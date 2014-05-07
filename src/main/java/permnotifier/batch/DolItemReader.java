package permnotifier.batch;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.client.RestTemplate;

import permnotifier.batch.vo.DolData;
import permnotifier.domain.PermRecord;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class DolItemReader extends AbstractPagingItemReader<PermRecord> {

	@Value("#{jobParameters['startDate']}")
	private Date startDate;
	
	RestTemplate restTemplate = new RestTemplate();

	private static final String url = "https://icert.doleta.gov/index.cfm?event=ehLCJRExternal.dspQuickCertSearchGridData&&startSearch=1&case_number=&employer_business_name=&visa_class_id=6&state_id=all&location_range=10&location_zipcode=&job_title=&naic_code=&create_date={0}&post_end_date={1}&h1b_data_series=ALL&nd=1399068235183&page={2}&rows={3}&sidx=create_date&sord=desc&nd=1399068235184&_search=false";
	private DateTimeFormatter formatter = DateTimeFormat
			.forPattern("MM/dd/YYYY");


	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		messageConverters.add(new DolDataMessageConverter());
		restTemplate.setMessageConverters(messageConverters);
	}

	@Override
	protected void doJumpToPage(int itemIndex) {
		// not supported
	}

	@Override
	protected void doReadPage() {
		String formattedUrl = MessageFormat.format(url,
				formatter.print(startDate.getTime()), formatter.print(startDate.getTime()),
				getPage() + 1, getPageSize());
		
		System.out.println("Getting date for url: " + formattedUrl);
		DolData object = restTemplate.getForObject(formattedUrl, DolData.class);
		if (results == null) {
			results = new CopyOnWriteArrayList<PermRecord>();
		} else {
			results.clear();
		}

		if (object.getTotal() >= getPage() + 1) {
			results.addAll(object.getItems());
		}
		// System.out.println(object.getPage() + "=" + currentPage);
		// System.out.println(results);
		// for (DolItem item : results) {
		// System.out.println(item.getCaseId());
		// }

//		System.out.println(object.getPage() + "=" + results.size());
	}

	private static class DolDataMessageConverter implements
			HttpMessageConverter<DolData> {

		Gson gson = new GsonBuilder().registerTypeAdapter(PermRecord.class,
				new DolItemDeserializer()).create();

		@Override
		public boolean canRead(Class<?> arg0, MediaType arg1) {
			return true;
		}

		@Override
		public boolean canWrite(Class<?> arg0, MediaType arg1) {
			return false;
		}

		@Override
		public List<MediaType> getSupportedMediaTypes() {
			return Arrays.asList(MediaType.ALL);
		}

		@Override
		public DolData read(Class<? extends DolData> arg0,
				HttpInputMessage message) throws IOException,
				HttpMessageNotReadableException {
			try (InputStream body = message.getBody()) {
				String jsonData = IOUtils.toString(body);
				return gson.fromJson(jsonData, DolData.class);
			}
		}

		@Override
		public void write(DolData arg0, MediaType arg1, HttpOutputMessage arg2)
				throws IOException, HttpMessageNotWritableException {
			// not implemented reader only
		}

	}

	private static class DolItemDeserializer implements
			JsonDeserializer<PermRecord> {

		private DateTimeFormatter formatter = DateTimeFormat
				.forPattern("MM/dd/YYYY");

		@Override
		public PermRecord deserialize(JsonElement element, Type type,
				JsonDeserializationContext context) throws JsonParseException {
			if (element.isJsonArray()) {
				JsonArray arr = (JsonArray) element;
				final PermRecord permRecord = new PermRecord();
				permRecord.setCaseId(arr.get(0).getAsInt());
				permRecord.setCaseNumber(arr.get(1).getAsString());
				permRecord.setJobPostingDate(formatter.parseDateTime(
						arr.get(2).getAsString()).toDate());
				permRecord.setCaseType(arr.get(3).getAsString());
				permRecord.setStatus(arr.get(4).getAsString());
				permRecord.setEmployer(StringUtils.upperCase(arr.get(5)
						.getAsString()));
				permRecord.setWorkStartDate(formatter.parseDateTime(
						arr.get(6).getAsString()).toDate());
				permRecord.setWorkEndDate(formatter.parseDateTime(
						arr.get(7).getAsString()).toDate());
				permRecord.setJobTitle(StringUtils.upperCase(arr.get(8)
						.getAsString()));
				permRecord.setState(StringUtils
						.upperCase(arr.get(9).getAsString()));
				return permRecord;
			}
			return null;
		}

	}

}
