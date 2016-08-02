package crawdmix.traffic

import akka.actor.Actor
import MoveRequest.Move

import scala.util.Random

/**
 * Created by robert on 12/03/16.
 */
class RobotActor(robotId: String, stationPath: String) extends Actor {
  val traffic = Array("HEAVY", "LIGHT", "MODERATE")

  var prevMove: Move = null

  override def receive: Receive = {
    case MoveRequest(moves) => {
      val geoLocation = new GeoLocation(stationPath)
      moves.foreach({ move =>
        if (geoLocation.inRange(move)) {
          val condition = traffic(Random.nextInt(traffic.size))
          val speed = s"speed = ${geoLocation.getSpeed(prevMove, move).getOrElse("None")} km/h "
          val report = robotId + "=> " + f"${move.time}%TD ${move.time}%TT " + speed + condition
          sender() ! DispatcherActor.trafficReport(report)
        }
        prevMove = move
      })
      sender() ! DispatcherActor.next()
    }
  }


}
