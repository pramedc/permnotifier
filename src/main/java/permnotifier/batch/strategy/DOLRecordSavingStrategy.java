package permnotifier.batch.strategy;

import java.util.Comparator;

import permnotifier.domain.AbstractModel;
import permnotifier.domain.WorkInformation;

public interface DOLRecordSavingStrategy<T extends AbstractModel & WorkInformation> {

	boolean canSave(T obj);
	
	void save(T obj);
	
	void saveAll(Iterable<T> objs);

	Comparator<T> getEqualityComparator();
}
