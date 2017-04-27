package com.xyzcorp

class Car private (val manufacturer:String, val model: String,
                   val year:Int) {
    Car.count = Car.count + 1
}

object Car {
  private var count = 0

  def apply(manufacturer: String, model: String, year: Int): Car =
    new Car(manufacturer, model, year)

  def numCarCreated:Int = count
}
