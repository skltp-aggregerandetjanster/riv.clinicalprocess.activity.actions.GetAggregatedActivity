package scenarios

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.check.HttpCheck

object GetAggregatedActivityPingForConfigurationScenario {

  val headers = Map(
    "Accept-Encoding" -> "gzip,deflate",
    "Content-Type"    -> "text/xml;charset=UTF-8",
    "SOAPAction"      -> "urn:riv:itintegration:monitoring:PingForConfigurationResponder:1:PingForConfiguration",
    "Keep-Alive"      -> "115")

  val request = exec(
        http("GetAggregatedActivityPingForConfiguration")
          .post("")
          .headers(headers)
          .body(RawFileBody("GetReferralOutcomePingForConfiguration.xml"))
          .check(status.is(200))
          .check(substring("Applikation"))
          .check(substring("GetAggregatedActivity"))
          .check(xpath("soap:Envelope", List("soap" -> "http://schemas.xmlsoap.org/soap/envelope/")).exists)
          .check(regex("GetAggregatedActivity").exists)
      )
  }



/*
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:urn="urn:riv:itintegration:registry:1" xmlns:urn1="urn:riv:itintegration:monitoring:PingForConfigurationResponder:1">
   <soapenv:Header>
      <urn:LogicalAddress>5565594230</urn:LogicalAddress>
   </soapenv:Header>
   <soapenv:Body>
      <urn1:PingForConfiguration>
         <urn1:serviceContractNamespace>something</urn1:serviceContractNamespace>
         <urn1:logicalAddress>5565594230</urn1:logicalAddress>
      </urn1:PingForConfiguration>
   </soapenv:Body>
</soapenv:Envelope>

<soap:Envelope
  xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
  <soap:Body>
    <PingForConfigurationResponse
      xmlns="urn:riv:itintegration:monitoring:PingForConfigurationResponder:1"
      xmlns:ns2="urn:riv:itintegration:registry:1">
      <pingDateTime>20150226110001</pingDateTime>
      <configuration>
        <name>Applikation</name>
        <value>GetAggregatedActivity</value>
      </configuration>
    </PingForConfigurationResponse>
  </soap:Body>
</soap:Envelope>
*/
