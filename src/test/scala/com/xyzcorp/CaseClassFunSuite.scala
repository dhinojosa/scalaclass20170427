package com.xyzcorp

import org.scalatest.{FunSuite, Matchers}

class CaseClassFunSuite extends FunSuite with Matchers {
  test("Convert Department to a case class") {
    val d = new Department("Toys")
    d.toString should be ("Department(Toys)")
  }

  test("Department with hash code") {
    val d1 = new Department("Toys")
    val d2 = new Department("Toys")
    d1.hashCode should be (d2.hashCode)
  }

  test("Department with equals") {
    val d1 = new Department("Toys")
    val d2 = new Department("Toys")
    d1 should be (d2)
  }

  test("Department with no new") {
    Department("Toys").name should be ("Toys")
    Department("Toys").name should not be ("Magazines")
  }

  test("Case classes provide a pattern match") {
    val Department(n) = Department("Appliances")
    n should be ("Appliances")
  }
}








