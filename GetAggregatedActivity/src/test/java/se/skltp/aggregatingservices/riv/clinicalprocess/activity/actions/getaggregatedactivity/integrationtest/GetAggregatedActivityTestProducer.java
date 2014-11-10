package se.skltp.aggregatingservices.riv.clinicalprocess.activity.actions.getaggregatedactivity.integrationtest;

import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import riv.clinicalprocess.activity.actions.getactivity.v1.rivtabp21.GetActivityResponderInterface;
import riv.clinicalprocess.activity.actions.getactivityresponder.v1.GetActivityResponseType;
import riv.clinicalprocess.activity.actions.getactivityresponder.v1.GetActivityType;
import se.skltp.agp.test.producer.TestProducerDb;

@WebService(serviceName = "GetActivityResponderService", portName = "GetActivityResponderPort", targetNamespace = "urn:riv:clinicalprocess:activity:actions:GetActivity:1:rivtabp21", name = "GetActivityInteraction")
public class GetAggregatedActivityTestProducer implements GetActivityResponderInterface {

	private static final Logger log = LoggerFactory.getLogger(GetAggregatedActivityTestProducer.class);

	private TestProducerDb testDb;
	public void setTestDb(TestProducerDb testDb) {
		this.testDb = testDb;
	}

	public GetActivityResponseType getActivity(String logicalAddress, GetActivityType request) {
		Object response = testDb.processRequest(logicalAddress, request.getPatientId().getExtension());

		if(response == null) {
			return new GetActivityResponseType();
		}
		return (GetActivityResponseType) response;
	}
}