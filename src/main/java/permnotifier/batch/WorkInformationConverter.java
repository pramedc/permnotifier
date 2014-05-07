package permnotifier.batch;

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
		document.addField("employer_s", input.getEmployer());
		document.addField("city_s", input.getCity());
		document.addField("state_s", input.getState());
		document.addField("jobPostingDate_dt", input.getJobPostDate());
		document.addField("jobTitle_s", input.getJobTitle());
		document.addField("yearlySalary_c", input.getYearlySalary());
		document.addField("monthlySalary_c", input.getMonthlySalary());
		document.addField("biweeklySalary_c", input.getBiWeeklySalary());
		document.addField("weeklySalary_c", input.getWeeklySalary());
		document.addField("hourlySalary_c", input.getHourlySalary());
		document.addField("jobLevel_s", input.getJobLevel());
		return document;
	}
	
}