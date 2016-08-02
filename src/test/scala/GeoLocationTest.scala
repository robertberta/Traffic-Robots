package crawdmix.traffic
import org.scalatest.{Matchers, WordSpec}
import GeoLocation.Station
import MoveRequest.Move
/**
 * Created by robert on 12/03/16.
 */
class GeoLocationTest extends WordSpec with Matchers{
  "crawdmix.traffic.GeoLocation" should {
    val geoLocation = new GeoLocation("src/test/resources/tube")
    "load all stations" in {
      geoLocation.stations should contain (Station("Barking",51.539521,0.080832))
      geoLocation.stations.size shouldBe 309
    }
    "compute speed between 2 moves" in {
      val move1=Move("5937,\"51.476051\",\"-0.100078\",\"2011-03-22 07:55:46\"")
      val move2=Move("5937,\"51.476009\",\"-0.099922\",\"2011-03-22 07:55:52\"")
      geoLocation.getSpeed(move1,move2) shouldBe Some(7L)
    }
    "compute distance between 2 moves" in {
      val move1=Move("5937,\"51.476051\",\"-0.100078\",\"2011-03-22 07:55:46\"")
      val move2=Move("5937,\"51.476009\",\"-0.099922\",\"2011-03-22 07:55:52\"")
      geoLocation.distance(move1.lat,move1.long,move2.lat,move2.long).toLong shouldBe 11
    }
    "show inRange tube stations 2 moves" in {
      val move1=Move("5937,\"51.476051\",\"-0.100078\",\"2011-03-22 07:55:46\"")
      val move2=Move("5937,\"51.476009\",\"-0.099922\",\"2011-03-22 07:55:52\"")
      val move3=Move("5937,\"51.609409\",\"-0.127489\",\"2011-03-22 07:55:58\"")
      geoLocation.inRange(move1) shouldBe false
      geoLocation.inRange(move2) shouldBe false
      geoLocation.inRange(move3) shouldBe true
    }
  }
}
