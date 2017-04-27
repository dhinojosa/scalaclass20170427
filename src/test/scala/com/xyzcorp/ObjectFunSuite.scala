package com.xyzcorp

import org.scalatest.{FunSuite, Matchers}

class ObjectFunSuite extends FunSuite with Matchers {

  test(
    """What is an object?
      |Is a Singleton,
      |how we avoid static""".stripMargin) {
    object Singleton
    val a = Singleton
    val b = Singleton
    a should be (b)
  }

  test("How does it look like a static?") {
    object Singleton {
      def foo(x:Int):Int = x + 5
      def apply(s:String):Int = s.size
    }

    Singleton.foo(4) should be (9)
    Singleton.apply("Howdy") should be (5)
    Singleton("Howdy") should be (5)
  }




}
