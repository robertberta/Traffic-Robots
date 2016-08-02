package crawdmix.traffic

import MoveRequest.Move
import GeoLocation.Station

import scala.io.Source

/**
 * Created by robert on 12/03/16.
 */
object GeoLocation {

  case class Station(name: String, lat: Double, long: Double)

}

class GeoLocation(stationPath: String) {
  final val STATION_FILE_NAME = "tube.csv"
  val stations = loadFrom(stationPath)

  private def loadFrom(stationPath: String) = {
    val tubeFile = Source.fromFile(stationPath.stripSuffix("/") + "/" + STATION_FILE_NAME)
    try
      tubeFile.getLines().map({ line: String =>
        val Array(name, long, lat) = line.split(",")
        Station(name.stripPrefix("\"").stripSuffix("\""), long.toDouble, lat.toDouble)
      }).toList
    finally tubeFile.close()
  }

  def inRange(move: Move) = {
    val RADIUS_IN_METERS = 350
    stations.exists(station => distance(station.lat, station.long, move.lat, move.long) < RADIUS_IN_METERS)
  }

  def getSpeed(prevMove: Move, move: Move) = {
    if (prevMove != null && prevMove.time != move.time) {
      val dist = distance(prevMove.lat, prevMove.long, move.lat, move.long)
      //
      // convert m/ms to km/h multiply with 3600*1000/1000 = 3600
      Some(Math.round(dist / (move.time - prevMove.time) * 3600))
    }
    else None
  }

  def distance(lat1: Double, long1: Double, lat2: Double, long2: Double) = {
    val METERS_PER_LAT = 111200
    val METERS_PER_LONG = 69260

    Math.hypot((lat1 - lat2) * METERS_PER_LAT, (long1 - long2) * METERS_PER_LONG)
  }
}
