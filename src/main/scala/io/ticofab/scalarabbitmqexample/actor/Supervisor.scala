package io.ticofab.scalarabbitmqexample.actor

import akka.actor.SupervisorStrategy.Stop
import akka.actor.{Actor, OneForOneStrategy, Props}
import io.ticofab.scalarabbitmqexample.actor.QueueListener.{CloseYourEars, Listen}
import io.ticofab.scalarabbitmqexample.actor.Supervisor.{Begin, End}

object Supervisor {

  case object Begin

  case object End

  def props = Props[Supervisor]
}

class Supervisor extends Actor {
  val queueListener = context.actorOf(QueueListener.props)

  // very simple supervision strategy: if anything happens, stop the actor
  override val supervisorStrategy = OneForOneStrategy(loggingEnabled = false) {
    case _: Exception => Stop
  }

  override def receive: Receive = {
    case Begin => queueListener ! Listen
    case End => queueListener ! CloseYourEars
  }
}
