name := "scala-rabbitmq-example"

version := "1.0"

val akkaVersion = "2.4.0"
val opRabbitVersion = "1.1.2"

resolvers ++= Seq(
  // repo for op-rabbit client
  "SpinGo OSS" at "http://spingo-oss.s3.amazonaws.com/repositories/releases"
)

libraryDependencies ++= Seq(
  // akka actors
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,

  // configuration file
  "com.typesafe" % "config" % "1.3.0",

  // including this otherwise problems might occur
  "org.slf4j" % "slf4j-simple" % "1.7.12",

  // RabbitMQ client
  // https://github.com/SpinGo/op-rabbit
  "com.spingo" %% "op-rabbit-core" % opRabbitVersion,
  "com.spingo" %% "op-rabbit-play-json" % opRabbitVersion
)

scalaVersion := "2.11.7"
    