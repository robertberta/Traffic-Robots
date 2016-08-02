import java.io.File

import crawdmix.traffic.TrafficApp
import org.scalatest.{Matchers, WordSpec}

/**
 * Created by robert on 12/03/16.
 */
class TrafficAppTest extends WordSpec with Matchers{
    "TrafficApp" should {
      "run as expected for" in {
        TrafficApp.main(Array("src/test/resources/tube","src/test/resources/robot"))
      }
    }
}
