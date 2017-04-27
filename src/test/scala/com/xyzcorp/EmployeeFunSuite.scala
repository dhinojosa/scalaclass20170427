package com.xyzcorp

import org.scalatest.{FunSuite, Matchers}

class EmployeeFunSuite extends FunSuite with Matchers {
  test("That an employee can be called with a secondary constructor") {
     val e = new Employee("Barbara", "Samson")
     e.age should be (None)
     e.age.getOrElse(5).should(be.apply(5))

    //e.age.getOrElse(5.asInstanceOf[AnyVal]).should(be.apply(5))
  }

  test("Supervisor works") {
    val `super` = new Supervisor("Darth", "Vader", Some(50),
      List(new Employee("Gary", "Stormtrooper")))

    `super`.employees.size should be (1)
  }


  test("Test Requirements Supervisor") {
    val `super` = new Supervisor("Darth", "Vader", Some(50),
      List(new Employee("Gary", "Stormtrooper", Some(40))))
  }
}
