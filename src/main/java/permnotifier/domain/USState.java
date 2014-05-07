package permnotifier.domain;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;


public enum USState {

	AL("ALABAMA"),
	AK("ALASKA"),
	AZ("ARIZONA"),
	AR("ARKANSAS"),
	CA("CALIFORNIA"),
	CO("COLORADO"),
	CT("CONNECTICUT"),
	DE("DELAWARE"),
	FL("FLORIDA"),
	GA("GEORGIA"),
	HI("HAWAII"),
	ID("IDAHO"),
	IL("ILLINOIS"),
	IN("INDIANA"),
	IA("IOWA"),
	KS("KANSAS"),
	KY("KENTUCKY"),
	LA("LOUISIANA"),
	ME("MAINE"),
	MD("MARYLAND"),
	MA("MASSACHUSETTS"),
	MI("MICHIGAN"),
	MN("MINNESOTA"),
	MS("MISSISSIPPI"),
	MO("MISSOURI"),
	MT("MONTANA"),
	NE("NEBRASKA"),
	NV("NEVADA"),
	NH("NEW HAMPSHIRE"),
	NJ("NEW JERSEY"),
	NM("NEW MEXICO"),
	NY("NEW YORK"),
	NC("NORTH CAROLINA"),
	ND("NORTH DAKOTA"),
	OH("OHIO"),
	OK("OKLAHOMA"),
	OR("OREGON"),
	PA("PENNSYLVANIA"),
	RI("RHODE ISLAND"),
	SC("SOUTH CAROLINA"),
	SD("SOUTH DAKOTA"),
	TN("TENNESSEE"),
	TX("TEXAS"),
	UT("UTAH"),
	VT("VERMONT"),
	VA("VIRGINIA"),
	WA("WASHINGTON"),
	WV("WEST VIRGINIA"),
	WI("WISCONSIN"),
	WY("WYOMING"),
	DC("WASHINGTON DC");
	
	private static final Map<String, USState> cachedValues = new HashMap<>();
	static {
		for (USState state : values()) {
			cachedValues.put(state.name, state);
			cachedValues.put(state.name(), state);
		}
	}
	
	private final String name;
	private USState(final String name) {
		this.name = name;
	}
	
	public String getStateName() {
		return name;
	}
	
	public static USState getState(String value) {
		String cleanupValue = StringUtils.upperCase(StringUtils.trimToEmpty(value));
		if(StringUtils.isBlank(cleanupValue)) {
			return null;
		}
		
		return cachedValues.get(cleanupValue);
	}
}
