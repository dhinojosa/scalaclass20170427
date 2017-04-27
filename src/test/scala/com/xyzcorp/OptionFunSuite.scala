package com.xyzcorp

import org.scalatest.{FunSuite, Matchers}

class OptionFunSuite extends FunSuite with Matchers{
   test("None with getOrElse") {
     val a:Option[String] = None
     a.getOrElse("Nothing to see here") should be ("Nothing to see here")
   }

  test("Some with getOrElse") {
    val a:Option[String] = Some("Hello")
    a.getOrElse("Nothing to see here") should be ("Hello")
  }
}
