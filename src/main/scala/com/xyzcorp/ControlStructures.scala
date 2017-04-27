package com.xyzcorp

object ControlStructures extends App{
  //Functional
  val cond = 100
  
  val d = if (cond > 40) "Whoa" else 60
  println(d)



  //Functional
  val total = (1 to 5).toList.sum
  println(total)
  val total3 = (1 to 5).foldLeft(0)((next, total) => next + total)

  // For Comprehension
  val total2 = for (i <- List(1,2,3,4,5)) yield (i + 1)
  println(total2)

  List.range(1, 6)

  //We don't while all that much

  var count = 5
  while (count > 0) {
    println(count)
    count = count - 1
  }

  count = 5
  do {

  } while(count > 0)
}