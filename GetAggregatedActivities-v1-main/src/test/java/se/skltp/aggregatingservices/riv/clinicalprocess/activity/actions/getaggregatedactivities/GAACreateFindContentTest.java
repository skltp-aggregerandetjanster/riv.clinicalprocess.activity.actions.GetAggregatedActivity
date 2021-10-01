package se.skltp.aggregatingservices.riv.clinicalprocess.activity.actions.getaggregatedactivities;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import riv.clinicalprocess.activity.actions.getactivitiesresponder.v1.GetActivitiesResponseType;
import se.skltp.aggregatingservices.api.AgpServiceFactory;
import se.skltp.aggregatingservices.tests.CreateFindContentTest;


@ExtendWith(SpringExtension.class)
public class GAACreateFindContentTest extends CreateFindContentTest {

  private static GAAAgpServiceConfiguration configuration = new GAAAgpServiceConfiguration();
  private static AgpServiceFactory<GetActivitiesResponseType> agpServiceFactory = new GAAAgpServiceFactoryImpl();
  private static ServiceTestDataGenerator testDataGenerator = new ServiceTestDataGenerator();

  public GAACreateFindContentTest() {
    super(testDataGenerator, agpServiceFactory, configuration);
  }
}
