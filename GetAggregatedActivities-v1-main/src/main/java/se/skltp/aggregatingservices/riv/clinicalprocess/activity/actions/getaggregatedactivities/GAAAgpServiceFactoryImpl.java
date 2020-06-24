package se.skltp.aggregatingservices.riv.clinicalprocess.activity.actions.getaggregatedactivities;

import java.util.List;
import lombok.extern.log4j.Log4j2;
import riv.clinicalprocess.activity.actions.getactivitiesresponder.v1.GetActivitiesResponseType;
import riv.clinicalprocess.activity.actions.getactivitiesresponder.v1.GetActivitiesType;
import se.skltp.aggregatingservices.AgServiceFactoryBase;

@Log4j2
public class GAAAgpServiceFactoryImpl extends
    AgServiceFactoryBase<GetActivitiesType, GetActivitiesResponseType>{

@Override
public String getPatientId(GetActivitiesType queryObject){
    return queryObject.getPatientId().getExtension();
    }

@Override
public String getSourceSystemHsaId(GetActivitiesType queryObject){
	if(queryObject.getSourceSystemId() != null)
		return queryObject.getSourceSystemId().getExtension();
	else
		return null;
    }

@Override
public GetActivitiesResponseType aggregateResponse(List<GetActivitiesResponseType> aggregatedResponseList ){

    GetActivitiesResponseType aggregatedResponse=new GetActivitiesResponseType();

    for (GetActivitiesResponseType obj : aggregatedResponseList) {
        final GetActivitiesResponseType response = obj;
        aggregatedResponse.getActivityGroup().addAll(response.getActivityGroup());
    }

    return aggregatedResponse;
    }
}

