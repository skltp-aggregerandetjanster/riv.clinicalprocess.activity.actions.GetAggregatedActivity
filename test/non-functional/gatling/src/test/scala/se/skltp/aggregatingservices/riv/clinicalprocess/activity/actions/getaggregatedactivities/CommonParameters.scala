package se.skltp.aggregatingservices.riv.clinicalprocess.activity.actions.getaggregatedactivities

trait CommonParameters {
  val serviceName:String     = "Activities"
  val urn:String             = "urn:riv:clinicalprocess:activity:actions:GetActivitiesResponder:1"
  val responseElement:String = "GetActivitiesResponse"
  val responseItem:String    = "activityGroup"
  var baseUrl:String         = if (System.getProperty("baseUrl") != null && !System.getProperty("baseUrl").isEmpty()) {
                                   System.getProperty("baseUrl")
                               } else {
                                   "http://33.33.33.33:8081/GetAggregatedActivities/service/v1"
                               }
}
