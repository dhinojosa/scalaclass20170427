package com.xyzcorp

import org.scalatest.{FunSuite, Matchers}

class TupleFunSuite extends FunSuite with Matchers {
   test("Using Tuple") {
     val a = ("One", 1.0, 3, 43.0d)
     val b = new Tuple2[Int, String](3, "Whoa")
     val c:(String, Int) = ("One", 1)
     val d:Tuple2[String, Int] = ("One", 1)
     val e = "One" -> 1
   }

   test("Using a tuple in a method") {
     def foo(t:(Int, String), x:Float):(String, Int) = {
       (t._2 + x, t._1 + 10)
     }

     def bar(t:Tuple2[Int, String], x:Float):String = ???

     foo(3 -> "Tarator", 4.0f) should be ("Tarator4.0", 13)
     foo((3, "Tarator"), 4.0f) should be ("Tarator4.0", 13)
   }
}
