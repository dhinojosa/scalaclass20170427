package com.xyzcorp

import java.time.{LocalDate, LocalDateTime}

import org.scalatest.{FunSuite, Matchers}

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.Future

class FunctionFunSuite extends FunSuite with Matchers {
  test("What is a function") {
    val f1: Function1[String, Int] = new Function1[String, Int]() {
      def apply(v1: String): Int = v1.size
    }
    f1.apply("Foo") should be(3)
    f1("Foo") should be(3)
  }

  test("What is a function (different signature)") {
    val f1: String => Int = new Function1[String, Int]() {
      def apply(v1: String): Int = v1.size
    }
    f1.apply("Foo") should be(3)
    f1("Foo") should be(3)
  }

  test("What is a function (different signature more concise)") {
    val f1: String => Int = (v1: String) => v1.size
    f1.apply("Foo") should be(3)
    f1("Foo") should be(3)
  }

  test("What is a function (different signature more concise, verbose on the right)") {
    val f1 = (v1: String) => v1.size
    f1("Foo") should be(3)
  }

  test("What is a function (different signature more concise, verbose on the left)") {
    val f1: String => Int = v1 => v1.size
    f1("Foo") should be(3)
  }

  test("What is a function (ultimate concise)") {
    val f1: String => Int = _.size
    f1("Foo") should be(3)
  }

  test("Function3 how does that look") {
    val f3 =
      (v1: String, v2: Int, v3: Double) => {
        v2 + (v1 * 10) + (v3 * 1014.00)
      }
  }


  test("Function2 using tuple") {
    val f2: (Int, String) => (String, Int) = (x: Int, y: String) => (y, x)
  }

  test("Function 0") {
    val f2 = () => LocalDate.of(2017, 4, 26)
  }

  test("map") {
    val list = List("foo", "bar", "baz", "tarator")
    val f = (x: String) => x.size
    list.map(f) should be(List(3, 3, 3, 7))
  }

  test("filter") {
    val list = Set(1, 4, 10, 3, 9)
    val f = (x: Int) => x % 2 == 0
    list.filter(f) should be(Set(4, 10))
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

      def get: A

      def map[B](f: A => B): Force[B] = if (isEmpty) DarkSide else LightSide(f(this.get))

      def flatMap[B](f: A => Force[B]): Force[B] = if (isEmpty) DarkSide else f(this.get)
    }

    case object DarkSide extends Force[Nothing] {
      override def isEmpty = true

      def get: Nothing = throw new RuntimeException("Nope cant do that")
    }

    case class LightSide[A](a: A) extends Force[A] {
      override def isEmpty = false

      def get: A = a
    }

    val lightSide = new LightSide[String]("Luke")
    lightSide.map(x => x.size) should be(LightSide(4))

    val result = for (i <- lightSide) yield i.size
    result should be(LightSide(4))

    val result2 = LightSide("Luke").flatMap(x => LightSide("Leia").map(y => x + y))
    result2 should be(LightSide("LukeLeia"))

    val result3 = for (x <- LightSide("Luke");
                       y <- LightSide("Leia");
                       z <- LightSide("Han")) yield List(x, y, z).mkString(", ")

    result3 should be(LightSide("Luke, Leia, Han"))
  }


  test("Mix of Monads") {
    val list = List(1, 2, 3, 4)
    val option = Some(10)
    val result = list.flatMap(x => option.map(y => x * y))
    result should be(List(10, 20, 30, 40))


    val result2 = for (x <- list; y <- option) yield x * y
    result2 should be(List(10, 20, 30, 40))

    val cities = List("Sofia, Bulgaria", "Tirana, Albania", "Bucharest, Romania")
    val token = Some("492384823")

    val result3 = for (x <- cities; y <- token) yield {
      /*
          Url url = new URL(s"http://weather-service.com/$x&token=$y")
          InputStream is = url.openConnection()
          val temps = List[Int]parse(is)
          temps
       */
    }


    val result4 = for (x <- List[Int](); y <- Some(10)) yield x * y
    result4 should be(List())
  }

  test("foreach with list") {
    val list = List(1, 2, 3)
    list.foreach(x => println(x))

    //Arrays.asList(1,2,3).stream().foreach(System.out::println)

    list foreach println
  }

  test("foreach with a future") {
    import scala.concurrent.ExecutionContext.Implicits.global

    val future: Future[Int] = Future {
      println(s"Inside the future ${Thread.currentThread().getName}")
      Thread.sleep(5000)
      6 + 12
    }

    println(s"Before the map ${Thread.currentThread().getName}")

    val step2: Future[String] = future.map { x =>
      println(s"Inside the map ${Thread.currentThread().getName}"); "" + x + 50
    }

    step2.foreach(x => println(x))

    println(s"After the map ${Thread.currentThread().getName}")

    Thread.sleep(5050)
  }

  test("foreach with a future (concise)") {
    import scala.concurrent.ExecutionContext.Implicits.global

    val future: Future[Int] = Future {
      println(s"Inside the future ${Thread.currentThread().getName}")
      Thread.sleep(5000)
      6 + 12
    }

    future.map(1 +).foreach(println)
    Thread.sleep(5070)
  }

  test("future monad") {
    import scala.concurrent.ExecutionContext.Implicits.global

    val future: Future[Int] = Future {
      println(s"Inside the future ${Thread.currentThread().getName}")
      Thread.sleep(1500)
      6 + 12
    }

    val future2: Future[Int] = Future {
      println(s"Inside the future ${Thread.currentThread().getName}")
      Thread.sleep(4000)
      50 * 10
    }

    val finalResult: Future[Int] = for (x <- future;
                                        y <- future2) yield x + y

    finalResult.foreach(println)

    Thread.sleep(4050)
  }

  test("byName parameter") {


    def times[A](x: Int)(y: => A): List[A] = {
      def timesRec(x: Int, acc: List[A]): List[A] = {
        if (x == 0) acc
        else timesRec(x - 1, acc :+ y)
      }

      println("Entering recursion")
      timesRec(x, List())
    }

    val result = times(3) {
      println("Block invoked")
      val x = "Foo"
      val y = "Bar"
      x + y
    }

    result should be(List("FooBar", "FooBar", "FooBar"))
  }

  test("byName timing blocks") {

    def timeIt[A](x: => A): Tuple2[Long, A] = {
      val startTime = System.currentTimeMillis()
      val result = x
      val endTime = System.currentTimeMillis()
      (endTime - startTime, result)
    }

    val result = timeIt {
      Thread.sleep(3000)
      40 + 10
    }

    println(result)
    result._2 should be(50)
  }

  test("convert a method to function") {
    class Foo(val x: Int) {
      def bar(y: Int) = x + y
    }

    val foo = new Foo(10)

    val result = List(1, 2, 3, 4).map(foo.bar)

    result should be(List(21, 22, 23, 24))
  }


  test("zip") {
    val a = List(1, 2, 3, 4)
    val b = List("Foo", "Bar", "Baz", "Quux")

    a.zip(b) should be(List(1 -> "Foo", 2 -> "Bar", 3 -> "Baz", 4 -> "Quux"))
    a.zip(b) should be(List((1, "Foo"), (2, "Bar"), (3, "Baz"), (4, "Quux")))
  }


  test("foldLeft") {
    val result = (1 to 10).foldLeft(0) { (total, next) =>
      println(s"total:$total, next:$next"); total + next
    }
    result should be(((10 + 1) / 2.0) * 10)
    result should be(55)
  }

  test("foldRight") {
    val result = (1 to 10).foldRight(0) { (next, total) =>
      println(s"total:$total, next:$next"); total + next
    }
    result should be(((10 + 1) / 2.0) * 10)
    result should be(55)
  }

  test("foldLeft what is it?") {
     def factorial(x:Int) = (1 to x).foldLeft(1)(_ * _)
     factorial(5) should be (120)
  }

  test("reduce") {
    (1 to 10).reduceLeft {(total, next) =>
      println(s"total:$total, next:$next"); total + next
    }
  }

  test("groceryList") {
    val groceries = List("Eggs", "Milk", "Naan", "Bacon",
                         "Rakia", "Jamon Iberico", "Tarator")

    val result:String = groceries
                        .sorted
                        .zipWithIndex
                        .map(t => (t._2 + 1, t._1))
                        .map(t => s"${t._1}. ${t._2}")
                        .mkString(System.lineSeparator())

    result should be (
      """1. Bacon
        |2. Eggs
        |3. Jamon Iberico
        |4. Milk
        |5. Naan
        |6. Rakia
        |7. Tarator""".stripMargin)
  }


  test("A real curry") {
    def filter(xs: List[Int], p: Int => Boolean): List[Int] =
      if (xs.isEmpty) xs
      else if (p(xs.head)) xs.head :: filter(xs.tail, p)
      else filter(xs.tail, p)

    val modN = (n:Int, x:Int) => (x % n) == 0
    val curriedModN = modN.curried
    val nums = List(1, 2, 3, 4, 5, 6, 7, 8)
    println(filter(nums, curriedModN(2)))
    println(filter(nums, curriedModN(3)))
  }

  test("A Stream that returns itself") {
      def streamStartingWith(x:Int):Stream[Int] = Stream.cons(x, streamStartingWith(x+1))
      streamStartingWith(1).take(5).toList should be (List(1,2,3,4,5))
  }

  test("A finite stream") {
      val stream = 1 #:: 2 #:: 3 #:: Stream.empty[Int]
      stream.take(3).toList should be (List(1,2,3))
  }

  test("A stream for fibonacci") {

    import scala.math.BigInt
    lazy val fibs: Stream[BigInt] = BigInt(0) #::
      BigInt(1) #::
      fibs.zip(fibs.tail).map { n => n._1 + n._2 }

     fibs.take(5).toList should be (List(0, 1, 1, 2, 3))
  }
}
