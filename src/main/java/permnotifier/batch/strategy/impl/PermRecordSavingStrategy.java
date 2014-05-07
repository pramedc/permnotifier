package permnotifier.batch.strategy.impl;

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

}
