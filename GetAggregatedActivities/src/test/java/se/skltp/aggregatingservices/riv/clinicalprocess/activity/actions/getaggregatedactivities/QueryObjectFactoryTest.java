package se.skltp.aggregatingservices.riv.clinicalprocess.activity.actions.getaggregatedactivities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import riv.clinicalprocess.activity.actions.getactivitiesresponder.v1.GetActivitiesType;
import riv.clinicalprocess.activity.actions.getactivitiesresponder.v1.ObjectFactory;
import riv.clinicalprocess.activity.actions.v1.IIType;
import se.skltp.agp.riv.itintegration.engagementindex.findcontentresponder.v1.FindContentType;

public class QueryObjectFactoryTest {

    private static final QueryObjectFactoryImpl testObject = new QueryObjectFactoryImpl();
    private static final ObjectFactory objFactory = new ObjectFactory();

    private static final String CATEGORIZATION = UUID.randomUUID().toString();
    private static final String SERVICE_DOMAIN = UUID.randomUUID().toString();
    private static final String SUBJECTOFCARE = UUID.randomUUID().toString();
    private static final String SOURCESYSTEMHSAID = UUID.randomUUID().toString();

    @BeforeClass
    public static void init() {
        testObject.setEiCategorization(CATEGORIZATION);
        testObject.setEiServiceDomain(SERVICE_DOMAIN);
    }

    @Test
    public void testQueryObjectFactorySuccess() throws Exception {
        final GetActivitiesType type = new GetActivitiesType();
        type.setPatientId(iiType(SUBJECTOFCARE));
        type.setSourceSystemId(iiType(SOURCESYSTEMHSAID));

        final Node node = createNode(type);
        final FindContentType findContent = testObject.createQueryObject(node).getFindContent();

        assertEquals(CATEGORIZATION, findContent.getCategorization());
        assertEquals(SERVICE_DOMAIN, findContent.getServiceDomain());
        assertEquals(SUBJECTOFCARE, findContent.getRegisteredResidentIdentification());
        assertEquals(SOURCESYSTEMHSAID, findContent.getSourceSystem());
        assertNull(findContent.getBusinessObjectInstanceIdentifier());
        assertNull(findContent.getClinicalProcessInterestId());
        assertNull(findContent.getDataController());
        assertNull(findContent.getMostRecentContent());
        assertNull(findContent.getOwner());
    }

    @Test
    public void testQueryObjectFactoryEmptySourceSystem() throws Exception {
        final GetActivitiesType type = new GetActivitiesType();
        type.setPatientId(iiType(SUBJECTOFCARE));
        type.setSourceSystemId(iiType(""));

        final Node node = createNode(type);
        final FindContentType findContent = testObject.createQueryObject(node).getFindContent();

        assertEquals(CATEGORIZATION, findContent.getCategorization());
        assertEquals(SERVICE_DOMAIN, findContent.getServiceDomain());
        assertEquals(SUBJECTOFCARE, findContent.getRegisteredResidentIdentification());

        assertNull(findContent.getLogicalAddress());
        assertNull(findContent.getSourceSystem());
        assertNull(findContent.getBusinessObjectInstanceIdentifier());
        assertNull(findContent.getClinicalProcessInterestId());
        assertNull(findContent.getDataController());
        assertNull(findContent.getMostRecentContent());
        assertNull(findContent.getOwner());
    }

    private IIType iiType(final String extension) {
        final IIType type = new IIType();
        type.setRoot("1.1.1.1.1");
        type.setExtension(extension);
        return type;
    }

    private Node createNode(final GetActivitiesType req) throws Exception {
        JAXBElement<GetActivitiesType> jaxb = objFactory.createGetActivities(req);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document node = db.newDocument();

        JAXBContext jc = JAXBContext.newInstance(GetActivitiesType.class);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.marshal(jaxb, node);
        return node;
    }
}
