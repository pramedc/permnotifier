package permnotifier.batch;

import java.math.BigDecimal;
import java.net.URL;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import permnotifier.domain.PermRecord;
import permnotifier.repositories.PermRecordRepository;

public class DolItemProcessor implements ItemProcessor<PermRecord, PermRecord>{

	private static final String DETAILS_URL = "https://icert.doleta.gov/index.cfm?event=ehLCJRExternal.dspCert&doc_id=3&visa_class_id=6&id={0}";
	private static Pattern AMOUNT_PATTERN = Pattern.compile("(\\$[\\d,\\.]*)");
	
	@Autowired
	PermRecordRepository dolItemRepository;
	
	@Override
	public PermRecord process(PermRecord item) throws Exception {
		
		PermRecord existingItem = dolItemRepository.findByCaseId(item.getCaseId());
		if(existingItem != null) {
			// if item already exists, then don't process it
			return null;
		}
		
		if(!"certified".equalsIgnoreCase(item.getStatus())) {
			return item;
		}
		
		try {
			final NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);
//			Document document = Jsoup.connect(MessageFormat.format(DETAILS_URL, String.valueOf(item.getCaseId()))).timeout(15000).get();
			
			String url = MessageFormat.format(DETAILS_URL, String.valueOf(item.getCaseId()));
			Document document = Jsoup.parse(new URL(url).openStream(), "ISO-8859-1", url);
			
	//		private BigDecimal prevailingWage;
			String prevailingWageText = getText(document, "#detail > div:nth-child(6) > div:nth-child(6) > p").replaceAll("[a-zA-Z() ]", "");
			item.setPrevailingWage(new BigDecimal(currency.parse(prevailingWageText).doubleValue()));
			
	//		private String occupationTitle;
			item.setOccupationTitle(StringUtils.upperCase(getText(document, "#detail > div:nth-child(6) > div:nth-child(4) > p")));
			
	//		private String occupationLevel;
			item.setOccupationLevel(StringUtils.upperCase(getText(document, "#detail > div:nth-child(6) > div:nth-child(5) > p")));
			
	//		private String city;
			item.setCity(StringUtils.upperCase(getText(document, "#detail > div:nth-child(8) > div:nth-child(4) > p")));
			
	//		private String fieldOfStudy;
			item.setFieldOfStudy(StringUtils.upperCase(getText(document, "#detail > div:nth-child(8) > div:nth-child(10) > p")));
			
			item.setCountryOfOrigin(StringUtils.upperCase(getText(document, "#detail > div:nth-child(10) > div:nth-child(13) > p")));
			
			String offeredWageText = getText(document, "#detail > div:nth-child(7) > div:nth-child(2) > p").replaceAll("[a-zA-Z() ]", "");
			Matcher matcher = AMOUNT_PATTERN.matcher(offeredWageText);
			
			List<BigDecimal> amounts = new ArrayList<BigDecimal>();
			while(matcher.find()) {
				amounts.add(new BigDecimal(currency.parse(matcher.group()).doubleValue()));
			}
			
	//		private BigDecimal offerLow;
			item.setOfferLow(amounts.get(0));
			
	//		private BigDecimal offerHigh;
			if(amounts.size() > 1) {
				item.setOfferHigh(amounts.get(1));
			}
			
			item.setDetailsLoaded(true);
		}
		catch (Exception e) {
			// ignore
		}
		
		return item;
	}

	private String getText(Document document, String cssPath) {
		return StringUtils.replace(document.select(cssPath).get(0).text().trim(), "\u00a0", ""); //remove &nbsp; characters
	}

}
