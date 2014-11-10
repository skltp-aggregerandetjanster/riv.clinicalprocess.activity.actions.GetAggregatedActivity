package se.skltp.aggregatingservices.riv.clinicalprocess.activity.actions.getaggregatedactivity;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;
import org.mockito.Mockito;
import org.soitoolkit.commons.mule.jaxb.JaxbUtil;

import riv.clinicalprocess.activity.actions.getactivityresponder.v1.GetActivityResponseType;
import riv.clinicalprocess.activity.actions.v1.ActivityType;
import riv.clinicalprocess.activity.actions.v1.IIType;
import riv.clinicalprocess.activity.actions.v1.PatientType;
import se.skltp.agp.riv.itintegration.engagementindex.findcontentresponder.v1.FindContentType;
import se.skltp.agp.service.api.QueryObject;
import se.skltp.agp.service.api.ResponseListFactory;


public class ResponseListFactoryTest {

	private final static String SUBJECT_OF_CARE = UUID.randomUUID().toString();
	private final static int NUMBER_OF_RESPONSES = 5;
	
	private final ResponseListFactoryImpl testObject = new ResponseListFactoryImpl();
	private final List<Object> responseList = new ArrayList<Object>();
	
	private final QueryObject queryObject = Mockito.mock(QueryObject.class);
	private final FindContentType findContentType = Mockito.mock(FindContentType.class);
	
	@Before
	public void setup() {
		Mockito.when(findContentType.getRegisteredResidentIdentification()).thenReturn(SUBJECT_OF_CARE);
		Mockito.when(queryObject.getFindContent()).thenReturn(findContentType);
		
		for(int i = 0; i < NUMBER_OF_RESPONSES; i++) {
			final GetActivityResponseType resp = new GetActivityResponseType();
			final ActivityType activity = new ActivityType();
			final PatientType patient = new PatientType();
			patient.setId(new IIType());
			patient.getId().setExtension(SUBJECT_OF_CARE);
			activity.setPatient(patient);
			resp.getActivity().add(activity);
			responseList.add(resp);
		}
	}
	

	@Test
	public void testGetXmlFromAggregatedResponse() {
		final JaxbUtil jaxbUtil = new JaxbUtil(GetActivityResponseType.class);

		final String after = testObject.getXmlFromAggregatedResponse(queryObject, responseList);
		final GetActivityResponseType type = (GetActivityResponseType) jaxbUtil.unmarshal(after);
		
		assertEquals(NUMBER_OF_RESPONSES, type.getActivity().size());
		for(ActivityType activity : type.getActivity()) {
			assertEquals(SUBJECT_OF_CARE, activity.getPatient().getId().getExtension());
		}
	}
}
