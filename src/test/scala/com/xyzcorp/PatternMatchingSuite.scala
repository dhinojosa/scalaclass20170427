package com.xyzcorp

import org.scalatest.{FunSuite, Matchers}

class PatternMatchingSuite extends FunSuite with Matchers {
  test("Simple Pattern Match") {
      val (a, b) = ("Foo", 3.0)
      a should be ("Foo")
      b should be (3.0)
  }

  test("Simple Pattern Match Option") {
      val Some(x) = Some(100)
      x should be (100)
  }

  test("List Match using :: method") {
      val e @ a :: _ :: Nil = List(1, 2)
      a should be (1)
      e should be (List(1,2))
  }

  test("List Matching using List method") {
      val e @ List(a, b, _) = List(1, 2, 3)
      a should be (1)
      b should be (2)
      e should be (List(1,2,3))
  }

  test("Matching a case class") {
    sealed trait BeverageTemp
    object Hot extends BeverageTemp
    object Cold extends BeverageTemp

    case class Beverage(name:String, bestWith:String, temp:BeverageTemp)

    val Beverage(n, bw, t) = Beverage("Boza", "Espresso", Hot)
    n should be ("Boza")
    t should be (Hot)
  }


  test("Regex pattern") {
    val regex = """(\d{2})-(\d{6})""".r
    val regex(pre, suf) = "12-401392"
    pre should be ("12")
    suf should be ("401392")
  }

  test("Pattern Match, with match case") {
    def mySecond[A](list:List[A]):Option[A] = {
      list match {
        case _ :: b :: _ => Some(b)
        case _ => None
      }
    }

    mySecond(List()) should be (None)
    mySecond(List(3)) should be (None)
    mySecond(List("Foo", "Bar")) should be (Some("Bar"))
    mySecond(List("Foo", "Bar", "Baz")) should be (Some("Bar"))
  }

  test("Pattern Match, with match case Using List") {
    def mySecond[A](list:List[A]):Option[A] = {
      list match {
        case List(_, b, _*) => Some(b)
        case _ => None
      }
    }

    mySecond(List()) should be (None)
    mySecond(List(3)) should be (None)
    mySecond(List("Foo", "Bar")) should be (Some("Bar"))
    mySecond(List("Foo", "Bar", "Baz")) should be (Some("Bar"))
  }

  test("Embedded Matching") {

    sealed trait BeverageTemp
    object Hot extends BeverageTemp
    object Cold extends BeverageTemp
    case class Beverage(name:String, bestWith:String, temp:BeverageTemp)

    val o:Option[Beverage] = Some(Beverage("Rakia", "Tarator", Cold))

    def process(a:Any):String = {
      a match {
        case Some(Beverage("Rakia", _, _)) => "Whoa"
        case a:Boolean => "Boolean"
        case _ => "Not Interested"
      }
    }

    process(10) should be ("Not Interested")
    process(o) should be ("Whoa")
    process(true) should be ("Boolean")
  }

  test("Creating your own pattern matching") {
    object IsOdd {
      def unapply(arg: Int): Option[Int] = if (arg % 2 != 0) Some(arg) else None
    }

    object IsEven {
      def unapply(arg: Int): Option[Int] = if (arg % 2 == 0) Some(arg) else None
    }

    val result = 40 match {
      case IsOdd(a1) => s"$a1 is odd!"
      case IsEven(b1) => s"$b1 is even!"
    }

    result should be ("40 is even!")
  }
}
