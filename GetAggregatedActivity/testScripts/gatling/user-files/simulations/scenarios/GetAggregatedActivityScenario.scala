package scenarios

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.check.HttpCheck
import scala.util.Random

object GetAggregatedActivityScenario {

  val headers = Map(
    "Accept-Encoding"                        -> "gzip,deflate",
    "Content-Type"                           -> "text/xml;charset=UTF-8",
    "SOAPAction"                             -> "urn:riv:clinicalprocess:activity:actions:GetActivityResponder:1:GetActivity",
    "x-vp-sender-id"                         -> "test",
    "x-rivta-original-serviceconsumer-hsaid" -> "test",
    "Keep-Alive"                             -> "115")


  val request = exec(
        http("GetAggregatedActivity ${patientid} - ${name}")
          .post("")
          .headers(headers)
          .body(ELFileBody("GetActivity.xml"))
          .check(status.is(session => session("status").as[String].toInt))
          .check(xpath("soap:Envelope", List("soap" -> "http://schemas.xmlsoap.org/soap/envelope/")).exists)
          .check(substring("GetActivityResponse"))
          .check(xpath("//ns3:activity", List("ns3" -> "urn:riv:clinicalprocess:activity:actions:GetActivityResponder:1")).count.is(session => session("count").as[String].toInt))
      )
}

