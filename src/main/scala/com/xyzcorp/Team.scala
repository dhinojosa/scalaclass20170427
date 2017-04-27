package com.xyzcorp

class Team  { boyanIsANiceGuy =>
  private var n:String = ""
  private var c:String = ""

  def coach:String = c
  def name:String = n

  def coach_=(c:String): Unit = {
    boyanIsANiceGuy.c = c
  }

  def name_=(n:String):Unit = {
    boyanIsANiceGuy.n = n
  }
}
