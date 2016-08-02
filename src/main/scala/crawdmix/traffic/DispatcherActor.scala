package crawdmix.traffic

import java.io.File

import akka.actor._
import DispatcherActor._

/**
 * Created by robert on 12/03/16.
 */
object DispatcherActor {
  private val format = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
  final val FINISH_TIME = format.parse("2011-03-22 08:10:00").getTime

  case class init(stationPath: String, robotPath: String)
  case class next()
  case class trafficReport(report: String)
}

class DispatcherActor extends Actor {

  var robots: List[Robot] = null
  var robotsTerminated = 0

  override def receive: Receive = {

    case init(stationPath, robotPath) => {
      val robotDir = new File(robotPath)
      val files = robotDir.listFiles.toList
      robots = files.map({ file =>
        val robotId = file.getName.stripSuffix(".csv")
        new Robot(context.actorOf(Props(classOf[RobotActor], robotId, stationPath)), file)
      })

      robots.foreach(sendMoveRequests)
    }
    case next() => {
      val robot = robots.filter(_.actor == sender).head
      sendMoveRequests(robot)
    }
    case trafficReport(report) => {
      println(report)
    }

  }

  def sendMoveRequests(robot: Robot) = {
    val moves = robot.nextMoves(FINISH_TIME)

    if (moves.isEmpty) {
      robot.actor ! PoisonPill
      robots = robots.diff(List(robot))
      if (robots.isEmpty) TrafficApp .system.shutdown()
    }
    else robot.actor ! MoveRequest(moves)
  }
}
