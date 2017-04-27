package com.xyzcorp

import scala.collection.mutable.ArrayBuffer

trait Loggable {
   private val logItems:ArrayBuffer[String] = ArrayBuffer[String]()
   def log(x:String):Unit = logItems += x
   def allLogs: List[String] = logItems.toList
}
