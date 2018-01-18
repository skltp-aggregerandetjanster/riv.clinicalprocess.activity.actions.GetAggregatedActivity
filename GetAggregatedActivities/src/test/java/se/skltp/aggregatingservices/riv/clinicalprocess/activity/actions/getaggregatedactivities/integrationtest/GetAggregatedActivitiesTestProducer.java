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