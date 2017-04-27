package com.xyzcorp

import java.time.{LocalDate, LocalDateTime}

import org.scalatest.{FunSuite, Matchers}

class FunctionFunSuite extends FunSuite with Matchers {
   test("What is a function") {
       val f1:Function1[String, Int] = new Function1[String, Int]() {
          def apply(v1: String): Int = v1.size
       }
       f1.apply("Foo") should be (3)
       f1("Foo") should be (3)
   }

   test("What is a function (different signature)") {
      val f1:String => Int = new Function1[String, Int]() {
         def apply(v1: String): Int = v1.size
      }
      f1.apply("Foo") should be (3)
      f1("Foo") should be (3)
   }

   test("What is a function (different signature more concise)") {
      val f1:String => Int = (v1: String) => v1.size
      f1.apply("Foo") should be (3)
      f1("Foo") should be (3)
   }

   test("What is a function (different signature more concise, verbose on the right)") {
      val f1 = (v1: String) => v1.size
      f1("Foo") should be (3)
   }

   test("What is a function (different signature more concise, verbose on the left)") {
      val f1:String => Int = v1 => v1.size
      f1("Foo") should be (3)
   }

   test("What is a function (ultimate concise)") {
      val f1:String => Int = _.size
      f1("Foo") should be (3)
   }

   test("Function3 how does that look") {
      val f3 =
         (v1: String, v2: Int, v3: Double) => {
               v2 + (v1 * 10) + (v3 * 1014.00)
      }
   }


   test("Function2 using tuple") {
      val f2:(Int, String) => (String, Int) = (x:Int, y:String) => (y, x)
   }

   test("Function 0") {
      val f2 = () => LocalDate.of(2017, 4, 26)
   }

   test("map") {
      val list = List("foo", "bar", "baz", "tarator")
      val f = (x:String) => x.size
      list.map(f) should be (List(3, 3, 3, 7))
   }

   test("filter") {
      val list = Set(1, 4, 10, 3, 9)
      val f = (x:Int) => x % 2 == 0
      list.filter(f) should be (Set(4, 10))
   }

   test("flatMap") {
      val list = List(1, 2, 3, 4)
      val list2 = List(11, 12, 13, 14)

      val result = list.flatMap(x => list2.map(y => x + y)) //Monad
      println(result)
   }


   test("flatMap as a for comprehension") {
      val list = List(1, 2, 3, 4)
      val list2 = List(11, 12, 13, 14)

      val result = for (x <- list;
                        y <- list2) yield x + y
      println(result)
   }

   test("Option flatMap") {
      val o1 = Some("A")
      val o2 = Some("B")

      o1.flatMap(x => o2.map(y => x + y))
   }

   test("Option flatMap as a for comprehension") {
      val o1 = Some("A")
      val o2 = Some("B")

      val result = for (x <- o1;
                        y <- o2) yield x + y
      println(result)
   }

   test("Option flatMap as a for comprehension with a failure") {
      val o1 = Some("A")
      val o2 = None

      val result = for (x <- o1;
                        y <- o2) yield x + y
      println(result)
   }

   test("Star Wars Monads") {
      abstract class Force[+A] {
         def isEmpty: Boolean
         def get:A
         def map[B](f: A => B): Force[B] = if (isEmpty) DarkSide else LightSide(f(this.get))
         def flatMap[B](f: A => Force[B]): Force[B] = if (isEmpty) DarkSide else f(this.get)
      }

      case object DarkSide extends Force[Nothing] {
         override def isEmpty = true
         def get:Nothing = throw new RuntimeException("Nope cant do that")
      }

      case class LightSide[A](a:A) extends Force[A] {
         override def isEmpty = false
         def get:A = a
      }

      val lightSide = new LightSide[String]("Luke")
      lightSide.map(x => x.size) should be (LightSide(4))

      val result = for (i <- lightSide) yield i.size
      result should be (LightSide(4))

      val result2 = LightSide("Luke").flatMap(x => LightSide("Leia").map(y => x + y))
      result2 should be (LightSide("LukeLeia"))

      val result3 = for (x <- LightSide("Luke");
           y <- LightSide("Leia");
           z <- LightSide("Han")) yield List(x,y,z).mkString(", ")

      result3 should be (LightSide("Luke, Leia, Han"))
   }

}
