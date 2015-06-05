package se.skltp.aggregatingservices.riv.clinicalprocess.activity.actions.getaggregatedactivities;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.soitoolkit.commons.mule.jaxb.JaxbUtil;

import riv.clinicalprocess.activity.actions.getactivitiesresponder.v1.GetActivitiesResponseType;
import riv.clinicalprocess.activity.actions.v1.ActivityGroupType;
import riv.clinicalprocess.activity.actions.v1.IIType;
import riv.clinicalprocess.activity.actions.v1.PatientType;
import se.skltp.agp.riv.itintegration.engagementindex.findcontentresponder.v1.FindContentType;
import se.skltp.agp.service.api.QueryObject;

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

        for (int i = 0; i < NUMBER_OF_RESPONSES; i++) {
            final GetActivitiesResponseType resp = new GetActivitiesResponseType();
            final ActivityGroupType activities = new ActivityGroupType();
            final PatientType patient = new PatientType();
            patient.setId(new IIType());
            patient.getId().setExtension(SUBJECT_OF_CARE);
            activities.setPatient(patient);
            resp.getActivityGroup().add(activities);
            responseList.add(resp);
        }
    }

    @Test
    public void testGetXmlFromAggregatedResponse() {
        final JaxbUtil jaxbUtil = new JaxbUtil(GetActivitiesResponseType.class);

        final String after = testObject.getXmlFromAggregatedResponse(queryObject, responseList);
        final GetActivitiesResponseType type = (GetActivitiesResponseType) jaxbUtil.unmarshal(after);

        assertEquals(NUMBER_OF_RESPONSES, type.getActivityGroup().size());
        for (ActivityGroupType activities : type.getActivityGroup()) {
            assertEquals(SUBJECT_OF_CARE, activities.getPatient().getId().getExtension());
        }
    }
}
