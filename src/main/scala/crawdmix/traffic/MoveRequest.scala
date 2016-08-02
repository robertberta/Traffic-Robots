package crawdmix.traffic

import java.text.SimpleDateFormat

import MoveRequest.Move

/**
 * Created by robert on 12/03/16.
 */

object MoveRequest {

  case class Move(str: String) {
    private val format = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss")
    val Array(name, strLat, strLong, strTime) = str.split(",")

    val lat = stripQuotes(strLat).toDouble
    val long = stripQuotes(strLong).toDouble
    val time = format.parse(stripQuotes(strTime)).getTime

    def stripQuotes(str: String) = {
      str.stripPrefix("\"")
        .stripSuffix("\"")
    }
  }

}

case class MoveRequest(moves: List[Move])
