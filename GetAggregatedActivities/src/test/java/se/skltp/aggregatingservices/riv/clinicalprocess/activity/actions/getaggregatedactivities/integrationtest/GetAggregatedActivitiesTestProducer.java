package se.skltp.aggregatingservices.riv.clinicalprocess.activity.actions.getaggregatedactivities.integrationtest;

import javax.jws.WebService;

import riv.clinicalprocess.activity.actions.getactivities.v1.rivtabp21.GetActivitiesResponderInterface;
import riv.clinicalprocess.activity.actions.getactivitiesresponder.v1.GetActivitiesResponseType;
import riv.clinicalprocess.activity.actions.getactivitiesresponder.v1.GetActivitiesType;
import se.skltp.agp.test.producer.TestProducerDb;

@WebService(serviceName = "GetActivitiesResponderService", 
               portName = "GetActivitiesResponderPort", 
        targetNamespace = "urn:riv:clinicalprocess:activity:actions:GetActivities:1:rivtabp21", 
                   name = "GetActivitiesInteraction")
public class GetAggregatedActivitiesTestProducer implements GetActivitiesResponderInterface {

    private TestProducerDb testDb;
    public void setTestDb(TestProducerDb testDb) {
        this.testDb = testDb;
    }

    public GetActivitiesResponseType getActivities(String logicalAddress, GetActivitiesType request) {
        Object response = testDb.processRequest(logicalAddress, request.getPatientId().getExtension());

        if (response == null) {
            return new GetActivitiesResponseType();
        }
        return (GetActivitiesResponseType) response;
    }
}