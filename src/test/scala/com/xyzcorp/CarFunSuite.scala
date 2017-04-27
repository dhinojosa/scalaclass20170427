package com.xyzcorp

import org.scalatest.{FunSuite, Matchers}

class CarFunSuite extends FunSuite with Matchers {
   test("Our own factory") {
     val beemer = Car("BMW", "Series 3", 2003)
     val seat = Car("Seat", "Ibiza", 2017)
     val moskvich = Car("Moskvich", "2142", 2006)
     Car.numCarCreated should be (3)
   }
}
