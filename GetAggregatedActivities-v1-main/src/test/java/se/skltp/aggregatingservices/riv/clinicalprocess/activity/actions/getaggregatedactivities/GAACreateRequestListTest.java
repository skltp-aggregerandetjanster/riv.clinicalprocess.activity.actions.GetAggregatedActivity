package se.skltp.aggregatingservices.riv.clinicalprocess.activity.actions.getaggregatedactivities;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import riv.clinicalprocess.activity.actions.getactivitiesresponder.v1.GetActivitiesResponseType;
import se.skltp.aggregatingservices.api.AgpServiceFactory;
import se.skltp.aggregatingservices.tests.CreateRequestListTest;


@ExtendWith(SpringExtension.class)
public class GAACreateRequestListTest extends CreateRequestListTest {

  private static GAAAgpServiceConfiguration configuration = new GAAAgpServiceConfiguration();
  private static AgpServiceFactory<GetActivitiesResponseType> agpServiceFactory = new GAAAgpServiceFactoryImpl();
  private static ServiceTestDataGenerator testDataGenerator = new ServiceTestDataGenerator();

  public GAACreateRequestListTest() {
    super(testDataGenerator, agpServiceFactory, configuration);
  }

}