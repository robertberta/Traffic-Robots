import sbt._
import sbt.Keys._

object AkkaBuild extends Build {

  val _name = "traffic"
  val _version = "1.0.0"
  val _scalaVersion = "2.10.5"


  lazy val adhocSparkJobsProject = Project(
    id = _name,
    base = file("."),

    settings =
        net.virtualvoid.sbt.graph.Plugin.graphSettings ++ Seq(
        name := _name,
        version := _version,
        scalaVersion := _scalaVersion,
        ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) },


        libraryDependencies ++= Seq(
          "com.typesafe.akka"        % "akka-actor_2.10"           % "2.3.14" ,
          "org.scala-lang"          % "scala-library"              % "2.10.5",
          "org.scalatest"           %% "scalatest"                 % "2.2.4" % "test"
        )


      )
  )

    .settings(Assembly.settings: _*)

}
