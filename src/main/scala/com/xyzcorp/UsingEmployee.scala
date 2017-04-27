package com.xyzcorp

import java.time.LocalDateTime

object UsingEmployee extends App {
  val e = new Employee("Foo", "Bar", Some(4))
  LocalDateTime.now
  e.fullName()
}
