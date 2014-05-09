package permnotifier.batch;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.common.SolrInputDocument;

import permnotifier.domain.WorkInformation;

import com.google.common.base.Function;

final class WorkInformationConverter<T extends WorkInformation> implements
		Function<T, SolrInputDocument> {
	
	public static <T extends WorkInformation> WorkInformationConverter<T> newInstance() {
		return new WorkInformationConverter<T>();
	}
	
	@Override
	public SolrInputDocument apply(T input) {
		SolrInputDocument document = new SolrInputDocument();
		document.addField("employer_s", StringUtils.upperCase(input.getEmployer()));
		document.addField("city_s", StringUtils.upperCase(input.getCity()));
		document.addField("state_s", StringUtils.upperCase(input.getState()));
		document.addField("jobPostingDate_dt", input.getJobPostDate());
		document.addField("jobPostMonth_s", input.getJobPostMonth());
		document.addField("jobPostYear_s", input.getJobPostYear());
		document.addField("jobTitle_s", StringUtils.upperCase(input.getJobTitle()));
		document.addField("yearlySalary_c", input.getYearlySalary());
		document.addField("monthlySalary_c", input.getMonthlySalary());
		document.addField("biweeklySalary_c", input.getBiWeeklySalary());
		document.addField("weeklySalary_c", input.getWeeklySalary());
		document.addField("hourlySalary_c", input.getHourlySalary());
		document.addField("jobLevel_s", StringUtils.upperCase(input.getJobLevel()));
		document.addField("type_s", StringUtils.upperCase(input.getType()));
		document.addField("typeIdentifier_s", StringUtils.upperCase(input.getTypeIdentifier()));
		document.addField("questionable_b", input.getHourlySalary().doubleValue() > 500);
		document.addField("searchField_s", StringUtils.upperCase(input.getTypeIdentifier()) + "," + StringUtils.upperCase(input.getJobTitle()) + "," + StringUtils.upperCase(input.getEmployer()));
		return document;
	}
	
}