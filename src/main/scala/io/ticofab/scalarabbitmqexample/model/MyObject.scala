package io.ticofab.scalarabbitmqexample.model

import play.api.libs.json.Json

/**
  * This is the kind of object that our listener expects in Json format from the queue. So like
  * {
  * "name" : "xxxx",
  * "version" : 1234
  * }
  *
  * @param name An arbitrary String
  * @param version An arbitrary Int
  */
case class MyObject(name: String, version: Int)

object MyObject {
  implicit def myObjectFormat = Json.format[MyObject]
}