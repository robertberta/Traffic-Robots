package crawdmix.traffic

import java.io.File

import akka.actor.ActorRef
import MoveRequest.Move

import scala.io.Source

/**
 * Created by robert on 12/03/16.
 */
class Robot(val actor: ActorRef, file: File) {
  val lines = Source.fromFile(file.getCanonicalPath).getLines()

  def nextMoves(finishTime: Long) = {
    lines.take(10)
      .map(new Move(_))
      .takeWhile(_.time < finishTime)
      .toList
  }
}
