package permnotifier.batch.vo;

import java.util.Arrays;
import java.util.List;

import permnotifier.domain.DolItem;


public class DolData {

	private int page;
	private int records;
	private int total;
	private DolItem[] rows;
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRecords() {
		return records;
	}

	public void setRecords(int records) {
		this.records = records;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public DolItem[] getRows() {
		return rows;
	}
	
	public List<DolItem> getItems() {
		return Arrays.asList(rows);
	}

	public void setRows(DolItem[] rows) {
		this.rows = rows;
	}
	
}
