package se.skltp.aggregatingservices.riv.clinicalprocess.activity.actions.getaggregatedactivity.integrationtest;

import static se.skltp.agp.test.producer.TestProducerDb.TEST_RR_ID_ONE_HIT;

import javax.xml.ws.Holder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import riv.clinicalprocess.activity.actions.getactivity.v1.rivtabp21.GetActivityResponderInterface;
import riv.clinicalprocess.activity.actions.getactivityresponder.v1.GetActivityResponseType;
import riv.clinicalprocess.activity.actions.getactivityresponder.v1.GetActivityType;
import riv.clinicalprocess.activity.actions.v1.IIType;
import se.skltp.aggregatingservices.riv.clinicalprocess.activity.actions.getaggregatedactivity.GetAggregatedActivityMuleServer;
import se.skltp.agp.test.consumer.AbstractTestConsumer;
import se.skltp.agp.test.consumer.SoapHeaderCxfInterceptor;
import se.skltp.agp.riv.interoperability.headers.v1.ProcessingStatusType;

public class GetAggregatedActivityTestConsumer extends AbstractTestConsumer<GetActivityResponderInterface> {

	private static final Logger log = LoggerFactory.getLogger(GetAggregatedActivityTestConsumer.class);

	public static void main(String[] args) {
		String serviceAddress = GetAggregatedActivityMuleServer.getAddress("SERVICE_INBOUND_URL");
		String personnummer = TEST_RR_ID_ONE_HIT;

		GetAggregatedActivityTestConsumer consumer = new GetAggregatedActivityTestConsumer(serviceAddress, SAMPLE_SENDER_ID, SAMPLE_ORIGINAL_CONSUMER_HSAID);
		Holder<GetActivityResponseType> responseHolder = new Holder<GetActivityResponseType>();
		Holder<ProcessingStatusType> processingStatusHolder = new Holder<ProcessingStatusType>();

		consumer.callService("logical-adress", personnummer, processingStatusHolder, responseHolder);
	}

	public GetAggregatedActivityTestConsumer(String serviceAddress, String senderId, String originalConsumerHsaId) {
	    
		// Setup a web service proxy for communication using HTTPS with Mutual Authentication
		super(GetActivityResponderInterface.class, serviceAddress, senderId, originalConsumerHsaId);
	}

	public void callService(String logicalAddress, String registeredResidentId, Holder<ProcessingStatusType> processingStatusHolder, Holder<GetActivityResponseType> responseHolder) {

		log.debug("Calling GetActivity-soap-service with Registered Resident Id = {}", registeredResidentId);
		
		GetActivityType request = new GetActivityType();

		request.setPatientId(new IIType());
		request.getPatientId().setExtension(registeredResidentId);
		
		
		GetActivityResponseType response = _service.getActivity(logicalAddress, request);
		responseHolder.value = response;
		
		processingStatusHolder.value = SoapHeaderCxfInterceptor.getLastFoundProcessingStatus();
	}
}