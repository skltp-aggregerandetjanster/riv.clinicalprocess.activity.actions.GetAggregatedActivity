package se.skltp.aggregatingservices.riv.clinicalprocess.activity.actions.getaggregatedactivities;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soitoolkit.commons.mule.jaxb.JaxbUtil;

import riv.clinicalprocess.activity.actions.getactivitiesresponder.v1.GetActivitiesResponseType;
import riv.clinicalprocess.activity.actions.getactivitiesresponder.v1.ObjectFactory;
import se.skltp.agp.riv.interoperability.headers.v1.ProcessingStatusType;
import se.skltp.agp.service.api.QueryObject;
import se.skltp.agp.service.api.ResponseListFactory;

public class ResponseListFactoryImpl implements ResponseListFactory {

    private static final Logger log = LoggerFactory.getLogger(ResponseListFactoryImpl.class);
    private static final JaxbUtil jaxbUtil = new JaxbUtil(GetActivitiesResponseType.class, ProcessingStatusType.class);
    private static final ObjectFactory OF = new ObjectFactory();

    @Override
    public String getXmlFromAggregatedResponse(QueryObject queryObject, List<Object> aggregatedResponseList) {
        final GetActivitiesResponseType aggregatedResponse = new GetActivitiesResponseType();

        for (Object obj : aggregatedResponseList) {
            final GetActivitiesResponseType response = (GetActivitiesResponseType) obj;
            aggregatedResponse.getActivityGroup().addAll(response.getActivityGroup());
        }

        log.info("Returning {} aggregated alert informations for subject of care id {}",
                  aggregatedResponse.getActivityGroup().size(),
                  queryObject.getFindContent().getRegisteredResidentIdentification());

        return jaxbUtil.marshal(OF.createGetActivitiesResponse(aggregatedResponse));
    }
}