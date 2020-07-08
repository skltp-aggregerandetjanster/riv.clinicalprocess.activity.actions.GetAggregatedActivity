package se.skltp.aggregatingservices.riv.clinicalprocess.activity.actions.getaggregatedactivities;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import riv.clinicalprocess.activity.actions.getactivities.v1.rivtabp21.GetActivitiesResponderInterface;
import riv.clinicalprocess.activity.actions.getactivities.v1.rivtabp21.GetActivitiesResponderService;
import se.skltp.aggregatingservices.config.TestProducerConfiguration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix="getaggregatedactivities.v1.teststub")
public class ServiceConfiguration extends TestProducerConfiguration {

  public static final String SCHEMA_PATH = "classpath:/schemas/td_clinicalprocess_activity_actions_1.0_RC6/interactions/GetActivitiesInteraction/GetActivitiesInteraction_1.0_RIVTABP21.wsdl";

  public ServiceConfiguration() {
    setProducerAddress("http://localhost:8083/vp");
    setServiceClass(GetActivitiesResponderInterface.class.getName());
    setServiceNamespace("urn:riv:clinicalprocess:activity:actions:GetActivitiesResponder:1");
    setPortName(GetActivitiesResponderService.GetActivitiesResponderPort.toString());
    setWsdlPath(SCHEMA_PATH);
    setTestDataGeneratorClass(ServiceTestDataGenerator.class.getName());
    setServiceTimeout(27000);
  }

}
