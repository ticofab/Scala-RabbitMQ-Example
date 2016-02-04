package io.ticofab.scalarabbitmqexample

import _root_.akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import io.ticofab.scalarabbitmqexample.actor.Supervisor
import io.ticofab.scalarabbitmqexample.actor.Supervisor.{End, Begin}

object ScalaRabbitMQExample extends App {

  // consts
  val ACTOR_SYSTEM_NAME = ConfigFactory.load().getString("akka.actor-system")

  // create actor system and use it to spin the supervisor actor
  implicit val actorSystem = ActorSystem(ACTOR_SYSTEM_NAME)
  val supervisor = actorSystem.actorOf(Supervisor.props)
  supervisor ! Begin

  // wait for user input to stop the system
  scala.io.StdIn.readLine(s"Press any key to stop the system")

  // close up things
  supervisor ! End
  actorSystem.terminate()

}