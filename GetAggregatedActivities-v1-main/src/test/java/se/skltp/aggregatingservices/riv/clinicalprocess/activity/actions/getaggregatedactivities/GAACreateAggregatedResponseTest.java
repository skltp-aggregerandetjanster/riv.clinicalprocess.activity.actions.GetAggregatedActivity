package se.skltp.aggregatingservices.riv.clinicalprocess.activity.actions.getaggregatedactivities;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import riv.clinicalprocess.activity.actions.getactivitiesresponder.v1.GetActivitiesResponseType;
import se.skltp.aggregatingservices.api.AgpServiceFactory;
import se.skltp.aggregatingservices.tests.CreateAggregatedResponseTest;

@ExtendWith(SpringExtension.class)
public class GAACreateAggregatedResponseTest extends CreateAggregatedResponseTest {

  private static GAAAgpServiceConfiguration configuration = new GAAAgpServiceConfiguration();
  private static AgpServiceFactory<GetActivitiesResponseType> agpServiceFactory = new GAAAgpServiceFactoryImpl();
  private static ServiceTestDataGenerator testDataGenerator = new ServiceTestDataGenerator();

  public GAACreateAggregatedResponseTest() {
      super(testDataGenerator, agpServiceFactory, configuration);
  }

  @Override
  public int getResponseSize(Object response) {
        GetActivitiesResponseType responseType = (GetActivitiesResponseType)response;
    return responseType.getActivityGroup().size();
  }
}