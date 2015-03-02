package activity

import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import scenarios.GetAggregatedActivityScenario

/**
 * Smoke test VP:GetAggregatedActivity.
 */
class TP02Smoke extends Simulation {

  val baseURL           = "https://qa.esb.ntjp.se/vp/clinicalprocess/activity/actions/GetActivity/1/rivtabp21"

  val testDuration        =      2 minutes
  val numberOfConcurrentUsers = 10
  val rampDuration        =     10 seconds
  val minWaitDuration     =      2 seconds
  val maxWaitDuration     =      5 seconds

  val httpProtocol = http.baseURL(baseURL).disableResponseChunksDiscarding

  val load = scenario("smoke")
                 .during(testDuration) {
                   exec(session => {
                     session.set("status","200").set("patientid","121212121212").set("name","Tolvan Tolvansson").set("count","3")
                   })
                   .exec(GetAggregatedActivityScenario.request)
                   .pause(minWaitDuration, maxWaitDuration)
                  }

  setUp (load.inject(rampUsers(numberOfConcurrentUsers) over (rampDuration)).protocols(httpProtocol))
}
