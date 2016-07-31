package art.of.programming

import org.scalatest.Matchers
import org.codetask.koanlib.CodeTaskSuite

class AbstractDataTypes extends CodeTaskSuite("Abstract Data Types", 14) {

  codetask(""" Exercise: (Stack Trait)
  Write down a parameterize trait for a Stack with the default methods to interact with it:
    1. writing an element to it
    2. getting an element from it
    3. checking if it is empty
    4. looking (peeking!) at the first element of it
  """) {
    // solve
    trait Stack[A] {
      def push(elem: A)
      def pop()
      def isEmpty: Boolean
      def peek: A
    }
    // endsolve
  }

  codetask(""" Exercise: (Queue Trait)
  Write down a parameterize trait for a Queue with the default methods to interact with it:
    1. writing an element to it
    2. getting an element from it
    3. checking if it is empty
    4. looking (peeking!) at the first element of it
    """) {
    // solve
    trait Queue[A] {
      def enqueue(elem: A): Unit
      def dequeue(): A
      def isEmpty: Boolean
      def peek: A
    }
    // endsolve
  }

  codetask(""" Exercise: (Implement Custom Stack)
    Try to implement a stack (MyStack) with use of the previously implemented trait.
    
    Note: Don't bother to checking if the stack is empty before some operations. The same for the fact that 
    this Stack could only store 10 elements.""") {
    class MyStack[A: Manifest] extends Stack[A] {
      private val data = new Array[A](10)
      private var top = 0
      // solve

      def push(elem: A): Unit = {
        data(top) = elem
        top += 1
      }
      def pop(): A = {
        top -= 1
        data(top)
      }
      def isEmpty: Boolean = top == 0
      def peek: A = data(top - 1)
      // endsolve
    }
   

    val s = new MyStack[Int]
    // test
    s.isEmpty should be(true)
    s.push(1)
    s.push(2)
    s.push(3)
    s.push(4)
    s.push(5)
    s.peek should be(5)
    s.pop() should be(5)
    s.pop() should be(4)
    s.pop() should be(3)
    s.isEmpty should be(false)
    // endtest

    trait Stack[A] {
      def push(elem: A): Unit
      def pop(): A
      def isEmpty: Boolean
      def peek: A
    }
  }

  codetask(""" Exercise: (Implement Custom Queue)
    Try to implement a custom queue (MyQueue) with use of the previously implemented trait.
    Note: Don't bother to checking if the queue is empty before some operations. The same for the fact that 
    this Queue could only store 10 elements.""") {
    class MyQueue[A: Manifest] extends Queue[A] {
      private val data = new Array[A](10)
      private var back = 0
      private var front = 0

      // solve
      def enqueue(elem: A): Unit = {
        data(back) = elem
        back = (back + 1) % data.length
      }
      def dequeue(): A = {
        val tmp = data(front)
        front = (front + 1) % data.length
        tmp
      }
      def isEmpty: Boolean = front == back
      def peek: A = data(front)
      // endsolve
    }
    

    trait Queue[A] {
      def enqueue(elem: A): Unit
      def dequeue(): A
      def isEmpty: Boolean
      def peek: A
    }

    val q = new MyQueue[Int]
    // test
    q.isEmpty should be(true)
    q.enqueue(1)
    q.enqueue(2)
    q.enqueue(3)
    q.enqueue(4)
    q.enqueue(5)
    q.peek should be(1)
    q.dequeue() should be(1)
    q.dequeue() should be(2)
    q.dequeue() should be(3)
    q.isEmpty should be(false)
    // endtest
  }
}