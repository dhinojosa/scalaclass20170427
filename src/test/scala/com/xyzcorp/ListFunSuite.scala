package com.xyzcorp

import org.scalatest.{FunSuite, Matchers}

class ListFunSuite extends FunSuite with Matchers {
   test("Test mkString") {
      val result = List("Ivaylo", "Kunka", "Sergio", "Aleksander").mkString(", ")
      result should be ("Ivaylo, Kunka, Sergio, Aleksander")
   }

  test("Test mkString with outer characters") {
    val result = List("Ivaylo", "Kunka", "Sergio", "Aleksander").mkString("[", ", ", "]")
    result should be ("[Ivaylo, Kunka, Sergio, Aleksander]")
  }
}
