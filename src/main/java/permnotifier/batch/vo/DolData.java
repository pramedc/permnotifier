package permnotifier.batch.vo;

import java.util.Arrays;
import java.util.List;

import permnotifier.domain.PermRecord;


public class DolData {

	private int page;
	private int records;
	private int total;
	private PermRecord[] rows;
	
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

	public PermRecord[] getRows() {
		return rows;
	}
	
	public List<PermRecord> getItems() {
		return Arrays.asList(rows);
	}

	public void setRows(PermRecord[] rows) {
		this.rows = rows;
	}
	
}
