package art.of.programming

import org.scalatest.Matchers
import org.codetask.koanlib.CodeTaskSuite

class Synchronization extends CodeTaskSuite("Multithreading", 13) {

  codetask(""" Exercise: (Single Thread)
    Try to write an object Multithreading that does start a single thread in its main.
    Each thread should print sequentially the numbers from 1..15.
    The thread should sleep after each print statement for 200 ms.
    Outside of the thread definition write a loop that iterates over the range of 'A'..'Z' which prints
    again each element. Add a sleep interval of 100 ms to this loop.""") {
    // solve
    object Multithreading {
      def main(args: Array[String]) = {
        new Thread(new Runnable() {
          def run {
            for (i <- 1 to 15) {
              println(i)
              Thread.sleep(200)
            }
          }
        }).start

        for (i <- 'A' to 'Z') {
          println(i)
          Thread.sleep(100)
        }
      }
    }
    // endsolve
  }

  codetask(""" Exercise: (Join Threads in Multithreaded Environment)
    Try to write an object Multithreading that does start 10 threads in its main.
    Each thread should print sequentially the numbers from 1..15, with the addition of the thread number e.g.:
    
    Thread #1: 10
    
    Try to print "done" when all threads are finished.    
    Note: Use the feature of joining the threads.
    """) {
    // solve
    object Multithreading {
      def main(args: Array[String]) = {
        val threads = for (i <- 1 to 10) yield {
          new Thread(new Runnable() {
            def run {
              for (j <- 1 to 15) println("Thread #" + i + ": " + j)
            }
          })
        }
        threads.foreach(_.start())
        threads.foreach(_.join())
        println("done")
      }
    }
    // endsolve
  }

  codetask(""" Exercise: (synchronized blocks) 
    The Synchronisation tool of the java/scala language do support the programmer to 
    synchronize certain blocks with the keyword synchronized. When using synchronization the programmer
    can lock onto an object, in our case this could be the object itself. It seals concurrent 
    access to a single thread. As you may think this way could slow the whole application when done wrong.
    
    Try to increment the local variable 1000 times in each thread.""") {
    object Multithreading {
      def main(args: Array[String]) = {
        var cnt = 0
        val threads = for (i <- 1 to 10) yield {
          new Thread(new Runnable() {
            def run {
              // solve
              for (j <- 1 to 1000) Multithreading.synchronized {
                cnt += 1
              }
              // endsolve
            }
          })
        }
        threads.foreach(_.start())
        threads.foreach(_.join())
        // test
        println(cnt) should be(10000)
        // endtest
      }
    }
  }

  codetask(""" Exercise: (Atomic variables)
    As an alternative or addition to synchronized java has several Atomic types. Atomic types
    prevents things like check then act issues. When adding or incrementing atomic types
    this operation will be synchronized.
    
    Try to add the atomic variable (aInt) with the intial value of 0. Increment this variable 10000 times 
    in each thread.
    
    Try to figure out an even faster implementation with the keyword synchronized.
    Tip: Do you need to synchronize each incrementation? Can i somehow get this locally done?""") {
    import java.util.concurrent.atomic.AtomicInteger

    object Multithreading {
      def main(args: Array[String]) = {
        // solve
        val aInt = new AtomicInteger(0)
        
        val threads = for (i <- 1 to 10) yield {
          new Thread(new Runnable() {
            def run {
              // solve
              for (j <- 1 to 10000) aInt.addAndGet(1)
            
            }
          })
        // endsolve
        }
        threads.foreach(_.start())
        threads.foreach(_.join())
        // test
        println(aInt.get) should be(100000)
        // endtest
      }
    }

    object FasterMultithreading {
      def main(args: Array[String]) = {
        
        var cnt = 0
        
        val threads = for (i <- 1 to 10) yield {
          new Thread(new Runnable() {
            def run {
              // solve
              var c = 0
              
              for (j <- 1 to 10000) c += 1
              
              FasterMultithreading.synchronized { cnt += c }
              // endsolve
            }
          })
        }
        threads.foreach(_.start())
        threads.foreach(_.join())
      }
    }
  }

  codetask(""" Exercise: (Executor with Futures)
    Similar to running threads manually Java has an built-in service for executing threads.
    Executors require a Interface Callable with the method call. The method call does the actual 
    calculation or instructions in each thread. Each call invoke sends back an asynchron Future which
    could be validated to a later time. 
          
    In fact that this is a service with an initialization process after using the service we have to terminate
    it by calling a method called shutdown() or shutdownNow(). The second option is brute force.
    
    Try to add a Executor service with 4 threads which calculate the fibonacci numbers from 30 down to 15.
    Print each single future from the list of futures.
    """) {
    import java.util.concurrent.Executors
    import java.util.concurrent.Callable

    object Multithreading {
      def main(args: Array[String]) = {
        // solve
        val es = Executors.newFixedThreadPool(4)
        val futures = for (i <- 30 to 15 by -1) yield {
          es.submit(new Callable[Int] {
            def call: Int = fib(i)
          })
        }
        futures.foreach(f => println(f.get))
        es.shutdown()
        // endsolve
      }

      def fib(n: Int): Int = if (n < 2) 1 else fib(n - 1) + fib(n - 2)
    }
  }

  codetask(""" Exercise: (Parallel Execution) 
    Every previous examples/codetask had two thing in common. All of them were based on java and they ran concurrent.
    Nowadays every PC has several CPU cores. Writing programs that work concurrently are good, but how about real parallelism.
    Let programs run on single cores without having the cpu sheduling between processes (ok in the end the cpu will shedule but 
    not as much as we would run it concurrent). Parallelism is the most efficient way to use all ressources provided by the pc. 
    You can archive this in by easily calling the method par which returns you a special subtype of the initial type. Each operation
    invoked on this new Collection/Sequence is then ran in parallel.
    
    Try to calculate the fib vom 30 down to 15 like in the previous task with what you learned. 
    """) {
    object Multithreading {
      def main(args: Array[String]) = {
        // solve
        val values = for (i <- (30 to 15 by -1).par) yield {
          fib(i)
        }
        // endsolve
        println("unordered")
        values.foreach(println)
        println("ordered")
        values.seq.foreach(println)
      }

      def fib(n: Int): Int = if (n < 2) 1 else fib(n - 1) + fib(n - 2)
    }
  }
}