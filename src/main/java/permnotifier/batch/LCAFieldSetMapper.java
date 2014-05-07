package permnotifier.batch;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import permnotifier.domain.LCARecord;

public class LCAFieldSetMapper implements FieldSetMapper<LCARecord> {

	DateTimeFormatter formatter = DateTimeFormat.forPattern("M/dd/yyyy");
	
	@Override
	public LCARecord mapFieldSet(FieldSet fieldSet) throws BindException {
		
		LCARecord record = new LCARecord();
		record.setCaseNumber(fieldSet.readString("caseNumber"));
		record.setStatus(fieldSet.readString("status"));
		record.setCaseSubmision(formatter.parseDateTime(fieldSet.readString("caseSubmission")).toDate());
		record.setCaseDecision(formatter.parseDateTime(fieldSet.readString("caseDecision")).toDate());
		
		if(StringUtils.isNotBlank(fieldSet.readString("employmentStart"))) {
			record.setEmploymentStart(formatter.parseDateTime(fieldSet.readString("employmentStart")).toDate());
		}
		
		record.setEmployer(fieldSet.readString("employer"));
		record.setEmployerCity(fieldSet.readString("employerCity"));
		record.setEmployerState(fieldSet.readString("employerState"));
		record.setJobTitle(fieldSet.readString("jobTitle"));
		record.setOfferLow(fieldSet.readBigDecimal("offerLow"));
		record.setOfferHigh(fieldSet.readBigDecimal("offerHigh"));
		record.setOfferSalaryType(fieldSet.readString("offerSalaryType"));
		record.setWorkLocationCity(fieldSet.readString("workLocationCity"));
		record.setWorkLocationState(fieldSet.readString("workLocationState"));
		
		return record;
	}

}
