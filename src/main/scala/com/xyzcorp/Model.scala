package com.xyzcorp
//Optional only one if you need interact with Java Stuff
import scala.beans.BeanProperty

sealed abstract class Person extends Loggable {
  def firstName:String
  def lastName:String
}

class Employee (@BeanProperty val firstName:String,
                @BeanProperty val lastName:String, val age:Option[Int]) extends Person {

  //require(age.getOrElse(0) >= 16, "All employees should be 16 years or older")

  def this(firstName:String, lastName:String) = this(firstName, lastName, None)

  def fullName(): String = firstName + " " + lastName
}

class Supervisor(firstName:String, lastName:String,
                 age:Option[Int], val employees:List[Employee])
                         extends Employee(firstName, lastName, age){
  //require(age.getOrElse(0) > 25, "Supervisors must be older than 25. No kids!")
}

case class Department(name:String)

