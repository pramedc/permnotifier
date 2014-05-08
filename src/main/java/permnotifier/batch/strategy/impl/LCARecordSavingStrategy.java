package permnotifier.batch.strategy.impl;

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
}
