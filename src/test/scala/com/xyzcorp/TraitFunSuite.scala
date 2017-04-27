package com.xyzcorp

import org.scalatest.{FunSuite, Matchers}

class TraitFunSuite extends FunSuite with Matchers{
  test("twos traits, an abstract, and a concrete") {
     trait Foo
     trait Foo2
     abstract class Bar
     class Baz extends Bar with Foo with Foo2
  }

  test("twos traits, no abstract class, a concrete") {
    trait Foo
    trait Foo2
    class Baz extends Foo with Foo2
  }

  test("the Any, AnyRef polymorphism") {
    trait Foo
    trait Foo2
    class Baz extends Foo with Foo2

    val b = new Baz()
    val c:Any = b
    val d:AnyRef = b
  }


  test("the Any, AnyRef polymorphism verbose") {
    trait Foo
    trait Foo2
    class Baz extends AnyRef with Foo with Foo2

    val b = new Baz()
    val c:Any = b
    val d:AnyRef = b
  }

  test("an employee with our mixin") {
    val e = new Employee("Tom", "Paul", Some(40))
    e.log("I created an employee")
    e.log("I feel good about it")
    e.allLogs.size should be (2)
  }

  test("mixin after the fact") {
     val o = new Object() with Loggable
     o.log("What the?")
     o.allLogs(0) should be ("What the?")
  }
}

