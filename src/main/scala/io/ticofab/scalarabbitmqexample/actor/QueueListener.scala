package io.ticofab.scalarabbitmqexample.actor

import akka.actor.{Actor, ActorLogging, Props}
import com.spingo.op_rabbit.Directives._
import com.spingo.op_rabbit.PlayJsonSupport._
import com.spingo.op_rabbit.{RabbitControl, Subscription, SubscriptionRef}
import com.typesafe.config.ConfigFactory
import io.ticofab.scalarabbitmqexample.actor.QueueListener.{CloseYourEars, Listen}
import io.ticofab.scalarabbitmqexample.model.MyObject

import scala.concurrent.ExecutionContext.Implicits.global

object QueueListener {

  case object Listen

  case object CloseYourEars

  def props = Props(new QueueListener)

}

class QueueListener extends Actor with ActorLogging {

  // read info from configuration
  val conf = ConfigFactory.load()
  val QUEUE = conf.getString("op-rabbit.my-queue")

  // instantiate a rabbit mq controller
  val RABBIT_CONTROL = context.actorOf(Props[RabbitControl])

  // references to the queue subscriptions
  var myQueueSubscription: Option[SubscriptionRef] = None

  override def receive: Receive = {

    case Listen =>

      // initialize a queue subscription
      myQueueSubscription = Some(
        Subscription.run(RABBIT_CONTROL) {
          channel(qos = 3) {
            consume(queue(QUEUE)) {
              body(as[MyObject]) {
                (obj) =>
                  log.debug(s"received my object $obj")
                  ack
              }
            }
          }
        }
      )

    case CloseYourEars =>

      // close the subscription
      myQueueSubscription.foreach(_.close())

  }
}
