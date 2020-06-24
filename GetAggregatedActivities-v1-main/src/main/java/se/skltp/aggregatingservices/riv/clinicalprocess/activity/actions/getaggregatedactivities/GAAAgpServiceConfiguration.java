package se.skltp.aggregatingservices.riv.clinicalprocess.activity.actions.getaggregatedactivities;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import riv.clinicalprocess.activity.actions.getactivities.v1.rivtabp21.GetActivitiesResponderInterface;
import riv.clinicalprocess.activity.actions.getactivities.v1.rivtabp21.GetActivitiesResponderService;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "getaggregatedactivities.v1")
public class GAAAgpServiceConfiguration extends se.skltp.aggregatingservices.configuration.AgpServiceConfiguration {

  public static final String SCHEMA_PATH = "classpath:/schemas/td_clinicalprocess_activity_actions_1.0_RC6/interactions/GetActivitiesInteraction/GetActivitiesInteraction_1.0_RIVTABP21.wsdl";

  public GAAAgpServiceConfiguration() {

    setServiceName("GetAggregatedActivities-v1");
    setTargetNamespace("urn:riv:clinicalprocess:activity:actions:GetActivities:1:rivtabp21");

    // Set inbound defaults
    setInboundServiceURL("http://localhost:9010/GetAggregatedActivities/service/v1");
    setInboundServiceWsdl(SCHEMA_PATH);
    setInboundServiceClass(GetActivitiesResponderInterface.class.getName());
    setInboundPortName(GetActivitiesResponderService.GetActivitiesResponderPort.toString());

    // Set outbound defaults
    setOutboundServiceWsdl(SCHEMA_PATH);
    setOutboundServiceClass(GetActivitiesResponderInterface.class.getName());
    setOutboundPortName(GetActivitiesResponderService.GetActivitiesResponderPort.toString());

    // FindContent
    setEiServiceDomain("riv:clinicalprocess:activity:actions");
    setEiCategorization("caa-ga");

    // TAK
    setTakContract("urn:riv:clinicalprocess:activity:actions:GetActivitiesResponder:1");

    // Set service factory
    setServiceFactoryClass(GAAAgpServiceFactoryImpl.class.getName());
    }


}
