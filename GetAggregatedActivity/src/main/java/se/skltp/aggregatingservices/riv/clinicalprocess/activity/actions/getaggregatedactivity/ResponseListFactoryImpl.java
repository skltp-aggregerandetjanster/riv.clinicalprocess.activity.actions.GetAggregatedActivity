package se.skltp.aggregatingservices.riv.clinicalprocess.activity.actions.getaggregatedactivity;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soitoolkit.commons.mule.jaxb.JaxbUtil;

import riv.clinicalprocess.activity.actions.getactivityresponder.v1.GetActivityResponseType;
import riv.clinicalprocess.activity.actions.getactivityresponder.v1.ObjectFactory;
import se.skltp.agp.riv.interoperability.headers.v1.ProcessingStatusType;
import se.skltp.agp.service.api.QueryObject;
import se.skltp.agp.service.api.ResponseListFactory;

public class ResponseListFactoryImpl implements ResponseListFactory {

	private static final Logger log = LoggerFactory.getLogger(ResponseListFactoryImpl.class);
	private static final JaxbUtil jaxbUtil = new JaxbUtil(GetActivityResponseType.class, ProcessingStatusType.class);
	private static final ObjectFactory OF = new ObjectFactory();
	
	@Override
	public String getXmlFromAggregatedResponse(QueryObject queryObject, List<Object> aggregatedResponseList) {
		final GetActivityResponseType aggregatedResponse = new GetActivityResponseType();
		
		for(Object obj : aggregatedResponseList) {
			final GetActivityResponseType response = (GetActivityResponseType) obj;
			aggregatedResponse.getActivity().addAll(response.getActivity());
		}
		
		if (log.isInfoEnabled()) {
    		String subjectOfCareId = queryObject.getFindContent().getRegisteredResidentIdentification();
        	log.info("Returning {} aggregated alert informations for subject of care id {}", aggregatedResponse.getActivity().size() ,subjectOfCareId);
        }
		
		return jaxbUtil.marshal(OF.createGetActivityResponse(aggregatedResponse));
	}
}