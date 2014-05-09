package permnotifier.batch.strategy.impl;

import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import permnotifier.batch.strategy.DOLRecordSavingStrategy;
import permnotifier.domain.LCARecord;
import permnotifier.repositories.LCARecordRepository;

@Service("lcaRecordSavingStrategy")
public class LCARecordSavingStrategy implements DOLRecordSavingStrategy<LCARecord> {

	@Autowired
	LCARecordRepository repository;
	
	@Override
	public boolean canSave(LCARecord obj) {
		return repository.findByCaseNumber(obj.getCaseNumber()) == null;
	}

	@Override
	public void save(LCARecord obj) {
		repository.save(obj);
	}

	@Override
	public void saveAll(Iterable<LCARecord> objs) {
		repository.save(objs);
	}
	
	@Override
	public Comparator<LCARecord> getEqualityComparator() {
		return new Comparator<LCARecord>() {
			@Override
			public int compare(LCARecord o1, LCARecord o2) {
				return o1.getCaseNumber().compareTo(o2.getCaseNumber());
			}
		};
	}
}
