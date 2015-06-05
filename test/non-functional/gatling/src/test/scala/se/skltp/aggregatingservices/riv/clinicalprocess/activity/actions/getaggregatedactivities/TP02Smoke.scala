package se.skltp.aggregatingservices.riv.clinicalprocess.activity.actions.getaggregatedactivities

import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import se.skltp.agp.testnonfunctional.TP02SmokeAbstract

/**
 * smoke test VP:GetAggregatedActivities.
 */
class TP02Smoke extends TP02SmokeAbstract with CommonParameters {
   // override skltp-box with qa
  if (baseUrl.startsWith("http://33.33.33.33")) {
      baseUrl = "http://ine-sit-app03.sth.basefarm.net:9010/GetAggregatedActivities/service/v1"
  }
  setUp(setUpAbstract(serviceName, urn, responseElement, responseItem, baseUrl))
}
