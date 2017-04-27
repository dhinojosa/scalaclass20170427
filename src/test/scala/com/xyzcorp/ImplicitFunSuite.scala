package com.xyzcorp

import org.scalatest.{FunSuite, Matchers}

class ImplicitFunSuite extends FunSuite with Matchers {
  case class Rate(x:Int)
  case class Currency(s:String)

  implicit val s:Currency = Currency("Lev")

  test("Simple Implicit") {
    implicit val a:Rate = Rate(40)

    def calcRate(hrs:Int)(implicit rate:Rate,
                           currency:Currency) =
                             (hrs * rate.x) + " " + currency.s

    calcRate(40) should be ("1600 Lev")
    calcRate(40)(Rate(50), Currency("Dollars")) should be ("2000 Dollars")
  }


  test("Create a method that doesn't on a class") {
    class IntWrapper(x:Int) {
      def isOdd: Boolean = x % 2 != 0
      def isEven: Boolean = !isOdd
      def Добавете3():Int = x + 3
    }

    //[Int => IntWrapper, obj]

    //implicit def int2IntWrapper(x:Int) = new IntWrapper(x)
    implicit val myIntWrapper = (x:Int) => new IntWrapper(x)
    10.isOdd should be (false)
    12.isEven should be (true)
    40.Добавете3 should be (43)
  }

  test("converters") {
    object MyRecipes {
      implicit val convertTuple2List = (t:Tuple2[Int, String]) => List.fill(t._1)(t._2)
    }


    import MyRecipes._
    def foo(x:List[String]):Int = {println(s"Thanks for $x"); x.size}
    foo((2, "Foo")) should be (2)
    foo((10, "Foo")) should be (10)
  }

  test("bigInt") {
    val a = BigInt(4000)
    (a + 10) should be (BigInt(4010))
  }



}
