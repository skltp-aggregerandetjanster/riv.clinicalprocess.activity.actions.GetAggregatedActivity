package se.skltp.aggregatingservices.riv.clinicalprocess.activity.actions.getaggregatedactivities

import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import se.skltp.agp.testnonfunctional.TP01WarmUpAbstract

/**
 * Simple requests to warm up service.
 */
class TP01WarmUp extends TP01WarmUpAbstract with CommonParameters {
  setUp(setUpAbstract(serviceName, urn, responseElement, responseItem, baseUrl))
}
