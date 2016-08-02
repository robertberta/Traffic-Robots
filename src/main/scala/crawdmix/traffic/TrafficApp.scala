package crawdmix.traffic

/**
 * Created by robert on 12/03/16.
 */

import akka.actor.{ActorSystem, Props, _}

object TrafficApp extends App {
  val system = ActorSystem("traffic")
  if (args.size != 2) {
    println("usage java -jar demo-1.0-jar-with-dependencies.jar tube_folder_path robot_folder_path")
    system.shutdown()
  }
  else {
    val dispatcher = system.actorOf(Props[DispatcherActor], "dispatcher")
    dispatcher ! DispatcherActor.init(args(0), args(1))
    system.awaitTermination()
  }

}






