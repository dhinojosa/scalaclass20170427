package com.xyzcorp

import java.time.LocalDateTime

object LazyVal extends App {
   lazy val a:Int = LocalDateTime.now.getSecond

   println(LocalDateTime.now.getSecond)

   Thread.sleep(4 * 1000)

   println(a)
   println(a)
}



