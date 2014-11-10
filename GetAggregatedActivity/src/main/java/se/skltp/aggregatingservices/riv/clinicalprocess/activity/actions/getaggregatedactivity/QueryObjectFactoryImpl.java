package se.skltp.aggregatingservices.riv.clinicalprocess.activity.actions.getaggregatedactivity;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soitoolkit.commons.mule.jaxb.JaxbUtil;
import org.w3c.dom.Node;

import riv.clinicalprocess.activity.actions.getactivityresponder.v1.GetActivityType;
import se.skltp.agp.riv.itintegration.engagementindex.findcontentresponder.v1.FindContentType;
import se.skltp.agp.service.api.QueryObject;
import se.skltp.agp.service.api.QueryObjectFactory;

public class QueryObjectFactoryImpl implements QueryObjectFactory {

	private static final Logger log = LoggerFactory.getLogger(QueryObjectFactoryImpl.class);
	private static final JaxbUtil ju = new JaxbUtil(GetActivityType.class);

	private String eiServiceDomain;
	public void setEiServiceDomain(String eiServiceDomain) {
		this.eiServiceDomain = eiServiceDomain;
	}

	private String eiCategorization;
	public void setEiCategorization(String eiCategorization) {
		this.eiCategorization = eiCategorization;
	}

	/**
	 * Transformerar GetActivity request till EI FindContent request enligt:
	 * 
	 * 1. subjectOfCareId --> registeredResidentIdentification
	 * 2. "riv:clinicalprocess:activity:actions" --> serviceDomain
	 * 3. "caa-ga" --> categorization
	 * 4. sourceSystemId.extension --> sourceSystem
	 * 5. sourceSystem --> logicalAddress
	 */
	public QueryObject createQueryObject(Node node) {
		final GetActivityType request = (GetActivityType)ju.unmarshal(node);
		if(log.isDebugEnabled() && request.getPatientId() != null) {
			log.debug("Transformed payload for pid: {}", request.getPatientId().getExtension());
		}
		final FindContentType fc = new FindContentType();
		
		if(request.getPatientId() != null) {
			fc.setRegisteredResidentIdentification(request.getPatientId().getExtension());
		}
		fc.setServiceDomain(eiServiceDomain);
		fc.setCategorization(eiCategorization);
		fc.setSourceSystem(getSourceSystem(request));
		fc.setLogicalAddress(fc.getSourceSystem());
		
		return new QueryObject(fc, request);	
	}
	
	protected String getSourceSystem(final GetActivityType request) {
		if(request.getSourceSystemId() == null || StringUtils.isBlank(request.getSourceSystemId().getExtension())) {
			return null;
		}
		return request.getSourceSystemId().getExtension();
	}
}
