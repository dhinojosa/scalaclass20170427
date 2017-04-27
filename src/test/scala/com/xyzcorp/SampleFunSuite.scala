package com.xyzcorp

import org.scalatest.{FunSuite, Matchers}

class SampleFunSuite extends FunSuite with Matchers {
    test("that one is equal to one") {
      1 should be (1)
    }
}
