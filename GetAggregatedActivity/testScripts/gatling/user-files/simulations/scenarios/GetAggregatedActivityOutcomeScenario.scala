package scenarios

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.check.HttpCheck
import scala.util.Random

object GetAggregatedActivityScenario {

  val headers = Map(
    "Accept-Encoding"                        -> "gzip,deflate",
    "Content-Type"                           -> "text/xml;charset=UTF-8",
    "SOAPAction"                             -> "urn:riv:clinicalprocess:activity:actions:GetActivityResponder:3:GetActivity",
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
          .check(xpath("//ns3:activity", List("ns3" -> "urn:riv:clinicalprocess:activity:actions:GetActivityResponder:3")).count.is(session => session("count").as[String].toInt))
      )
  }

/*
<soapenv:Envelope
      xmlns:soapenv= "http://schemas.xmlsoap.org/soap/envelope/"
      xmlns:urn    = "urn:riv:itintegration:registry:1"
      xmlns:urn1   = "urn:riv:clinicalprocess:activity:actions:GetActivityResponder:3"
      xmlns:urn2   = "urn:riv:clinicalprocess:activity:actions:3">

   <soapenv:Header>
      <urn:LogicalAddress>5565594230</urn:LogicalAddress>
   </soapenv:Header>
   <soapenv:Body>
      <urn1:GetActivity>
         <urn1:patientId>
            <urn2:id>121212121212</urn2:id>
            <urn2:type>1.2.752.129.2.1.3.1</urn2:type>
         </urn1:patientId>
      </urn1:GetActivity>
   </soapenv:Body>
</soapenv:Envelope>

<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:urn="urn:riv:interoperability:headers:1" xmlns:urn1="urn:riv:itintegration:registry:1">
   <soapenv:Header>
      <ProcessingStatus xmlns="urn:riv:interoperability:headers:1">
         <ProcessingStatusList>
            <logicalAddress>HSA-ID-4</logicalAddress>
            <statusCode>DataFromSource</statusCode>
            <isResponseFromCache>false</isResponseFromCache>
            <isResponseInSynch>true</isResponseInSynch>
            <lastSuccessfulSynch>20150226150225</lastSuccessfulSynch>
         </ProcessingStatusList>
      </ProcessingStatus>
   </soapenv:Header>

   <soapenv:Body>

      <ns3:GetReferralOutcomeResponse xmlns:ns3="urn:riv:clinicalprocess:healthcond:actoutcome:GetReferralOutcomeResponder:3" xmlns="urn:riv:clinicalprocess:healthcond:actoutcome:3" xmlns:ns2="urn:riv:clinicalprocess:healthcond:actoutcome:3.1" xmlns:ns4="urn:riv:interoperability:headers:1">

          <ns3:referralOutcome>
            <referralOutcomeHeader>
               <documentId>463b90d6-a4e1-4c7f-bf3a-349e9c10f723</documentId>
               <sourceSystemHSAId>HSA-ID-4</sourceSystemHSAId>
               <documentTime>20150226030101</documentTime>
               <patientId>
                  <id>121212121212</id>
                  <type>1.2.752.129.2.1.3.1</type>
               </patientId>
               <accountableHealthcareProfessional>
                  <authorTime>20150226030101</authorTime>
                  <healthcareProfessionalOrgUnit>
                     <orgUnitHSAId>HSA-ID-4</orgUnitHSAId>
                     <orgUnitName>Sjukhuset</orgUnitName>
                     <orgUnitTelecom>00-00000000</orgUnitTelecom>
                     <orgUnitEmail>email@email.com</orgUnitEmail>
                     <orgUnitAddress>Address</orgUnitAddress>
                     <orgUnitLocation>Location</orgUnitLocation>
                  </healthcareProfessionalOrgUnit>
                  <healthcareProfessionalCareGiverHSAId>HSA-ID-4</healthcareProfessionalCareGiverHSAId>
               </accountableHealthcareProfessional>
               <approvedForPatient>false</approvedForPatient>
            </referralOutcomeHeader>
            <referralOutcomeBody>
               <referralOutcomeTypeCode>SR</referralOutcomeTypeCode>
               <referralOutcomeTitle>ReferalOutcomeTitle</referralOutcomeTitle>
               <referralOutcomeText>OutcomeText</referralOutcomeText>
               <referral>
                  <referralId>ReferralId</referralId>
                  <referralReason>ReferralReason</referralReason>
                  <referralTime>20150226030101</referralTime>
                  <referralAuthor>
                     <authorTime>20150226030101</authorTime>
                     <healthcareProfessionalOrgUnit>
                        <orgUnitHSAId>HSA-ID-4</orgUnitHSAId>
                        <orgUnitName>Sjukhuset</orgUnitName>
                        <orgUnitTelecom>00-00000000</orgUnitTelecom>
                        <orgUnitEmail>email@email.com</orgUnitEmail>
                        <orgUnitAddress>Address</orgUnitAddress>
                        <orgUnitLocation>Location</orgUnitLocation>
                     </healthcareProfessionalOrgUnit>
                     <healthcareProfessionalCareGiverHSAId>HSA-ID-4</healthcareProfessionalCareGiverHSAId>
                  </referralAuthor>
               </referral>
            </referralOutcomeBody>
         </ns3:referralOutcome>

       </ns3:GetReferralOutcomeResponse>

   </soapenv:Body>
</soapenv:Envelope>
*/
