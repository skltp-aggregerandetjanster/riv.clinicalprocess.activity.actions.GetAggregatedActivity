/**
 * Copyright (c) 2014 Inera AB, <http://inera.se/>
 *
 * This file is part of SKLTP.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
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
