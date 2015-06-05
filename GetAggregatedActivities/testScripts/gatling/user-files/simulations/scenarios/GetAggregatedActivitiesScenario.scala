package scenarios

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.check.HttpCheck
import scala.util.Random

object GetAggregatedActivitiesScenario {

  val headers = Map(
    "Accept-Encoding"                        -> "gzip,deflate",
    "Content-Type"                           -> "text/xml;charset=UTF-8",
    "SOAPAction"                             -> "urn:riv:clinicalprocess:activity:actions:GetActivitiesResponder:1:GetActivities",
    "x-vp-sender-id"                         -> "test",
    "x-rivta-original-serviceconsumer-hsaid" -> "test",
    "Keep-Alive"                             -> "115")


  val request = exec(
        http("GetAggregatedActivities ${patientid} - ${name}")
          .post("")
          .headers(headers)
          .body(ELFileBody("GetActivities.xml"))
          .check(status.is(session => session("status").as[String].toInt))
          .check(xpath("soap:Envelope", List("soap" -> "http://schemas.xmlsoap.org/soap/envelope/")).exists)
          .check(substring("GetActivitiesResponse"))
          .check(xpath("//ns3:activities", List("ns3" -> "urn:riv:clinicalprocess:activity:actions:GetActivitiesResponder:1")).count.is(session => session("count").as[String].toInt))
      )
}

