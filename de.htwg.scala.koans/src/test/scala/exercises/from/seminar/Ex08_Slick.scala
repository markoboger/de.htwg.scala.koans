package de.htwg.scala.seminar

import akka.actor.{ActorSystem, Props}
import akka.stream.actor.{ActorSubscriber, OneByOneRequestStrategy}
import org.codetask.koanlib.CodeTaskSuite
import slick.backend.DatabasePublisher
import slick.driver.H2Driver.api._
import slick.jdbc.meta.MTable

import scala.concurrent.Await
import scala.concurrent.duration.Duration

case class Department(id: Int, name: String)

class Departments(tag: Tag) extends Table[Department](tag, "DEPARTMENTS") {

  // column definition
  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  def name = column[String]("NAME")

  // default projection
  def * = (id, name) <>(Department.tupled, Department.unapply)
}

case class Employee(id: Int, firstname: String, lastname: String, age: Option[Int], departmentId: Int)

class Employees(tag: Tag) extends Table[Employee](tag, "EMPLOYEES") {

  // column definition
  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  def firstname = column[String]("FIRST_NAME")
  def lastname = column[String]("LAST_NAME")
  def age = column[Option[Int]]("AGE")
  def departmentId = column[Int]("DEPARTMENT_ID", O.Default(1))

  // default projection
  def * = (id, firstname, lastname, age, departmentId) <>(Employee.tupled, Employee.unapply)

  // foreign key
  def department = foreignKey("DEP_FK", departmentId, TableQuery[Departments])(_.id)
}

class Ex08_Slick extends CodeTaskSuite("Slick",8) {

  val db = Database.forURL("jdbc:h2:mem:test1;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver")

  val departments = TableQuery[Departments]
  val employees = TableQuery[Employees]

  koan("Creating the schema") {
    val action = (departments.schema ++ employees.schema).create
    val createFuture = db.run(action)
    Await.ready(createFuture, Duration.Inf)

    val future = db.run(MTable.getTables)
    val tables: Vector[MTable] = Await.result(future, Duration.Inf)

    tables.length should be(2)
    tables.count(_.name.name.equalsIgnoreCase("departments")) should be(1)
    tables.count(_.name.name.equalsIgnoreCase("employees")) should be(1)
    tables.count(_.name.name.equalsIgnoreCase("address")) should be(0)
  }

  koan("Inserting data to database") {
    val insert = DBIO.seq(
      departments += Department(1, "Hardware"),
      departments += Department(2, "Software"),
      departments += Department(3, "Personal"),

      employees ++= Seq(
        Employee(1, "Tim", "Cook", Some(26), 1),
        Employee(2, "Sergey", "Brin", Some(45), 2),
        Employee(3, "Foo", "Bar", None, 2)
      )
    )
    Await.ready(db.run(insert), Duration.Inf)

    val future = db.run(employees.result)
    val result: Seq[Employee] = Await.result(future, Duration.Inf)
    result.length should be(3)
  }

  koan("Inserting data with omitting some columns") {
    val empInsert = employees.map(e => (e.firstname, e.lastname))
    val action = empInsert +=("Erich", "Gamma")
    Await.ready(db.run(action), Duration.Inf)

    val query = employees.filter(_.lastname === "Gamma")
    val future = db.run(query.result)
    val result: Seq[Employee] = Await.result(future, Duration.Inf)
    val employee = result.head

    employee.firstname should be("Erich")
    employee.age should be(None)
    employee.departmentId should be(1)
  }

  koan("Query with the Scala collections like API") {
    val query = employees.filter(_.age > 30).map(_.lastname)
    val future = db.run(query.result)
    val result: Seq[String] = Await.result(future, Duration.Inf)

    result.length should be(1)
    result.head should be("Brin")

    // get select statement and remove " characters
    val sqlStatement = query.result.statements.head.replace("\"", "")
    sqlStatement.toLowerCase() should be("select last_name from employees where age > 30")
  }

  koan("Query with the Scala for-expression") {
    val query = for {
      e <- employees if e.age > 30
    } yield e.lastname
    val future = db.run(query.result)
    val result: Seq[String] = Await.result(future, Duration.Inf)

    result.length should be(1)
    result.head should be("Brin")

    // get select statement and remove " characters
    val sqlStatement = query.result.statements.head.replace("\"", "")
    sqlStatement.toLowerCase() should be("select last_name from employees where age > 30")
  }

  koan("Monadic inner join. This type of joins will be generated as explicit join in SQL.") {
    val query = for {
      e <- employees
      d <- e.department
    } yield (e.lastname, d.name)

    val future = db.run(query.result)
    val result: Seq[(String, String)] = Await.result(future, Duration.Inf)

    result.length should be(4)
  }

  koan("Applicative left outer join. This type of joins will be generated as implicit join in SQL.") {
    val query = for {
      (d, e) <- departments joinLeft employees on (_.id === _.departmentId)
    } yield (d.name, e.map(_.lastname))

    val future = db.run(query.result)
    val result: Seq[(String, Option[String])] = Await.result(future, Duration.Inf)

    result.length should be(5)
  }

  koan("Unions: Two queries can be concatenated with the '++' (or 'unionAll') and 'union' operators") {
    val q1 = employees.filter(_.age > 30)
    val q2 = employees.filter(_.age < 20)

    val unionQuery = q1 union q2

    val future = db.run(unionQuery.result)
    val result: Seq[Employee] = Await.result(future, Duration.Inf)

    result.length should be(1)
  }

  koan("Aggregation functions") {
    val query = employees
      .filter(_.age >= 18)
      .map(_.age)
      .min

    val future = db.run(query.result)
    val result: Option[Int] = Await.result(future, Duration.Inf)

    result should be(Some(26))
  }

  koan("Complex query with join, group by and order by") {
    val joinQuery = for {
      e <- employees
      d <- e.department
    } yield (d, e)

    val query = joinQuery.groupBy(_._1.name).map {
      case (name, j) => (name, j.map(_._2.age).max)
    }.sortBy(_._2.desc) // order by age

    val future = db.run(query.result)
    val result: Seq[(String, Option[Int])] = Await.result(future, Duration.Inf)

    result.length should be(2)
    result.head._1 should be("Software")
    result.head._2 should be(Some(45))
  }

  koan("Update data with the 'update' method") {
    val query = employees.filter(_.lastname === "Gamma").map(_.age)
    val update = query.update(Some(30))
    Await.ready(db.run(update), Duration.Inf)

    val future = db.run(query.result)
    val result: Seq[Option[Int]] = Await.result(future, Duration.Inf)

    result.head should be(Some(30))
  }

  koan("Delete data with the 'delete' method") {
    val query = employees.filter(_.lastname === "Gamma")
    val delete = query.delete
    Await.ready(db.run(delete), Duration.Inf)

    val future = db.run(query.result)
    val result: Seq[Employee] = Await.result(future, Duration.Inf)

    result.length should be(0)
  }

  koan("Collection-valued queries also support streaming results. " +
    "Data will be streamed through a Reactive Streams Publisher and can be processed and consumed by Akka Streams") {

    var msgCount = 0

    class ExampleActorSubscriber extends ActorSubscriber {
      val requestStrategy = OneByOneRequestStrategy

      override def receive: Receive = {
        case _ => msgCount += 1
      }
    }

    val system = ActorSystem("ExampleActorSystem")
    val subRef = system.actorOf(Props(new ExampleActorSubscriber()))
    val subscriber = ActorSubscriber[String](subRef)

    val query = employees.map(_.lastname)
    val action = query.result
    val publisher: DatabasePublisher[String] = db.stream(action)
    publisher.subscribe(subscriber)

    // wait that all message can be consumed
    Thread.sleep(300)

    msgCount should be(4)
  }

  koan("To force the use of a transaction, append the method transactionally to an action") {
    try {
      val actions = DBIO.seq(
        departments += Department(4, "New department"),
        departments += Department(5, null)
      )
      val f1 = db.run(actions.transactionally)
      Await.ready(f1, Duration.Inf)
    } catch {
      case e: Throwable =>
    }

    val future = db.run(departments.result)
    val result: Seq[Department] = Await.result(future, Duration.Inf)

    result.length should be(3)
  }

}
