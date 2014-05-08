package permnotifier.controllers.api.result;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class FacetList {

	private String name;
	private List<FacetData> list = new ArrayList<FacetData>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if(name.contains("_")) {
			this.name = StringUtils.upperCase(name.substring(0, name.indexOf("_")));
		}
		else {
			this.name = StringUtils.upperCase(name);
		}
	}

	public List<FacetData> getList() {
		return list;
	}

	public void setList(List<FacetData> list) {
		this.list = list;
	}

	public void addFacet(String name, long count) {
		FacetData facetData = new FacetData();
		facetData.setName(name);
		facetData.setCount(count);
		this.list.add(facetData);
	}

}
