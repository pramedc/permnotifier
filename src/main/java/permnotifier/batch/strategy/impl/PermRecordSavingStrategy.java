package permnotifier.batch.strategy.impl;

import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import permnotifier.batch.strategy.DOLRecordSavingStrategy;
import permnotifier.domain.PermRecord;
import permnotifier.repositories.PermRecordRepository;

@Service("permRecordSavingStrategy")
public class PermRecordSavingStrategy implements DOLRecordSavingStrategy<PermRecord> {

	@Autowired
	PermRecordRepository repository;
	
	@Override
	public boolean canSave(PermRecord obj) {
		return repository.findByCaseNumber(obj.getCaseNumber()) == null;
	}

	@Override
	public void save(PermRecord obj) {
		repository.save(obj);
	}
	
	@Override
	public void saveAll(Iterable<PermRecord> objs) {
		repository.save(objs);
	}

	@Override
	public Comparator<PermRecord> getEqualityComparator() {
		return new Comparator<PermRecord>() {
			@Override
			public int compare(PermRecord o1, PermRecord o2) {
				return o1.getCaseNumber().compareTo(o2.getCaseNumber());
			}
		};
	}
}
