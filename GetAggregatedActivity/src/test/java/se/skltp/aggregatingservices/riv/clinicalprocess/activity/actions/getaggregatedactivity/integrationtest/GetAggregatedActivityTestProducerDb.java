package se.skltp.aggregatingservices.riv.clinicalprocess.activity.actions.getaggregatedactivity.integrationtest;

import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soitoolkit.commons.mule.util.ThreadSafeSimpleDateFormat;

import riv.clinicalprocess.activity.actions.enums.v1.AddressPartTypeEnum;
import riv.clinicalprocess.activity.actions.enums.v1.TelTypeEnum;
import riv.clinicalprocess.activity.actions.getactivityresponder.v1.GetActivityResponseType;
import riv.clinicalprocess.activity.actions.v1.ActivityType;
import riv.clinicalprocess.activity.actions.v1.AddressPartType;
import riv.clinicalprocess.activity.actions.v1.AddressType;
import riv.clinicalprocess.activity.actions.v1.CVType;
import riv.clinicalprocess.activity.actions.v1.CareGiverType;
import riv.clinicalprocess.activity.actions.v1.CareUnitType;
import riv.clinicalprocess.activity.actions.v1.DeviceType;
import riv.clinicalprocess.activity.actions.v1.IIType;
import riv.clinicalprocess.activity.actions.v1.LocationType;
import riv.clinicalprocess.activity.actions.v1.PatientType;
import riv.clinicalprocess.activity.actions.v1.PerformerRoleType;
import riv.clinicalprocess.activity.actions.v1.SourceSystemType;
import riv.clinicalprocess.activity.actions.v1.TelType;
import riv.clinicalprocess.activity.actions.v1.TimePeriodType;
import se.skltp.agp.test.producer.TestProducerDb;

public class GetAggregatedActivityTestProducerDb extends TestProducerDb {

	private static final Logger log = LoggerFactory.getLogger(GetAggregatedActivityTestProducerDb.class);
	private static final ThreadSafeSimpleDateFormat df = new ThreadSafeSimpleDateFormat("yyyyMMddhhmmss");

	@Override
	public Object createResponse(Object... responseItems) {
		log.debug("Creates a response with {} items", responseItems);
		GetActivityResponseType response = new GetActivityResponseType();
		for (int i = 0; i < responseItems.length; i++) {
			response.getActivity().add((ActivityType)responseItems[i]);
		}
		return response;
	}
	
	@Override
	public Object createResponseItem(String logicalAddress, String registeredResidentId, String businessObjectId, String time) {
		if (log.isDebugEnabled()) {
			log.debug("Created one response item for logical-address {}, registeredResidentId {} and businessObjectId {}",
				new Object[] {logicalAddress, registeredResidentId, businessObjectId});
		}

		final ActivityType type = new ActivityType();
		
		type.setApprovedForPatient(true);
		type.setDescription("Kompletterande fritextbeskrivning av aktiviteten där aktiviteten inte fullt ut kan beskrivas med kodbeteckningen.");
		type.setDevice(deviceType());
		type.setId(iiType("1.2.752.129.2.1.2.1", logicalAddress+":"+ new Date().getTime()));
		type.setLocation(location(logicalAddress));
		type.setPatient(patient(registeredResidentId));
		type.setPerformerRole(performer());
		type.setSourceSystem(sourceSystemType(logicalAddress));
		type.setTargetSite(cvType("Fysisk", "1.2.752.116.2.1"));
		type.setTime(timePeriod());
		type.setValue(cvType("Sjukgymnastik", "1.2.3.4.5.6.7"));

		//type.getAdditionalParticipant()
		//type.getRelation()
		
		return type;
	}
	
	protected TimePeriodType timePeriod() {
		final TimePeriodType type = new TimePeriodType();
		type.setEnd(df.format(new Date()));
		type.setStart(df.format(new Date()));
		return type;
	}
	
	protected SourceSystemType sourceSystemType(final String logicalAddresss) {
		final SourceSystemType type = new SourceSystemType();
		type.setId(iiType("1.2.757.129.2.1.4.1", logicalAddresss));
		return type;
	}
	
	protected PerformerRoleType performer() {
		final PerformerRoleType perf = new PerformerRoleType();
		perf.setCareUnit(careUnit());
		perf.setCode(cvType("HL7 RoleCode", "2.16.840.1.113883.5.111"));
		perf.setId(iiType("1.2.752.129.2.1.4.1", "hsaid"));
		return perf;
	}
	
	protected CareUnitType careUnit() {
		final CareUnitType careUnitType = new CareUnitType();
		careUnitType.setId(iiType("1.2.752.129.2.1.4.1", "hsaId"));
		careUnitType.setName("Svedala sjukhus");
		careUnitType.setCareGiver(careGiver());
		return careUnitType;
	}
	
	protected CareGiverType careGiver() {
		final CareGiverType type = new CareGiverType();
		type.setId(iiType("1.2.752.129.2.1.4.1", "hsaid"));
		type.setName("Test Testsson");
		return type;
	}
	
	protected PatientType patient(final String ssn) {
		final PatientType type = new PatientType();
		if(ssn != null) {
			type.setDateOfBirth("19" + ssn.substring(0, 6));
		}
		type.setGender(cvType("9", "1.2.752.129.2.2.1.1"));
		type.setId(iiType("1.2.752.129.2.1.3.1", ssn));
		return type;
	}
	
	protected LocationType location(final String hsaId) {
		final LocationType loc = new LocationType();
		loc.setId(iiType("1.2.752.129.2.1.4.1", hsaId));
		loc.setName("TestName");
		loc.getAddress().add(address());
		loc.getTelecom().add(tele());
		return loc;
	}

	protected TelType tele() {
		final TelType type = new TelType();
		type.setUse(TelTypeEnum.FAX);
		type.setValue("02000000000");
		return type;
	}
	
	protected AddressType address() {
		final AddressType address = new AddressType();
		address.setPurpose(cvType("Kod", "2.16.840.1.113883.6.259"));
		address.getValue().add(partType());
		return address;
	}
	
	protected AddressPartType partType() {
		final AddressPartType type = new AddressPartType();
		type.setRole(AddressPartTypeEnum.AL);
		type.setValue("Svedala");
		return type;
	}
	
	protected DeviceType deviceType() {
		final DeviceType device = new DeviceType();
		device.setId(iiType("1.2.160", "1234"));
		return device;
	}
	
	protected IIType iiType(final String root, final String extension) {
		final IIType ii = new IIType();
		ii.setExtension(extension);
		ii.setRoot(root);
		return ii;
	}
	
	protected CVType cvType(final String code, final String codeSystem) {
		final CVType cv = new CVType();
		cv.setCode(code);
		cv.setCodeSystem(codeSystem);
		return cv;
	}
	
	
}