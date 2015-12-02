package se.skltp.aggregatingservices.riv.clinicalprocess.activity.actions.getaggregatedactivities.integrationtest;

import static se.skltp.agp.test.producer.TestProducerDb.TEST_RR_ID_ONE_HIT;

import javax.xml.ws.Holder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import riv.clinicalprocess.activity.actions.getactivities.v1.rivtabp21.GetActivitiesResponderInterface;
import riv.clinicalprocess.activity.actions.getactivitiesresponder.v1.GetActivitiesResponseType;
import riv.clinicalprocess.activity.actions.getactivitiesresponder.v1.GetActivitiesType;
import riv.clinicalprocess.activity.actions.v1.IIType;
import se.skltp.aggregatingservices.riv.clinicalprocess.activity.actions.getaggregatedactivities.GetAggregatedActivitiesMuleServer;
import se.skltp.agp.test.consumer.AbstractTestConsumer;
import se.skltp.agp.test.consumer.SoapHeaderCxfInterceptor;
import se.skltp.agp.riv.interoperability.headers.v1.ProcessingStatusType;

public class GetAggregatedActivitiesTestConsumer extends AbstractTestConsumer<GetActivitiesResponderInterface> {

	private static final Logger log = LoggerFactory.getLogger(GetAggregatedActivitiesTestConsumer.class);

	public static void main(String[] args) {
		String serviceAddress = GetAggregatedActivitiesMuleServer.getAddress("SERVICE_INBOUND_URL");
		String personnummer = TEST_RR_ID_ONE_HIT;

		GetAggregatedActivitiesTestConsumer consumer = new GetAggregatedActivitiesTestConsumer(serviceAddress, SAMPLE_SENDER_ID, SAMPLE_ORIGINAL_CONSUMER_HSAID, SAMPLE_CORRELATION_ID);
		Holder<GetActivitiesResponseType> responseHolder = new Holder<GetActivitiesResponseType>();
		Holder<ProcessingStatusType> processingStatusHolder = new Holder<ProcessingStatusType>();

		consumer.callService("logical-adress", personnummer, processingStatusHolder, responseHolder);
	}

	public GetAggregatedActivitiesTestConsumer(String serviceAddress, String senderId, String originalConsumerHsaId, String correlationId) {
		// Setup a web service proxy for communication using HTTPS with Mutual Authentication
		super(GetActivitiesResponderInterface.class, serviceAddress, senderId, originalConsumerHsaId, correlationId);
	}

	public void callService(String logicalAddress, String registeredResidentId, Holder<ProcessingStatusType> processingStatusHolder, Holder<GetActivitiesResponseType> responseHolder) {

		log.debug("Calling GetActivities-soap-service with Registered Resident Id = {}", registeredResidentId);
		
		GetActivitiesType request = new GetActivitiesType();

		request.setPatientId(new IIType());
		request.getPatientId().setExtension(registeredResidentId);
		
		GetActivitiesResponseType response = _service.getActivities(logicalAddress, request);
		responseHolder.value = response;
		
		processingStatusHolder.value = SoapHeaderCxfInterceptor.getLastFoundProcessingStatus();
	}
}