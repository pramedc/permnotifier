package permnotifier.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import permnotifier.domain.LCARecord;

public interface LCARecordRepository  extends PagingAndSortingRepository<LCARecord, Long>{

	@Query("SELECT e FROM LCARecord as e WHERE e.caseNumber = :caseNumber")
	LCARecord findByCaseNumber(@Param("caseNumber") String caseNumber);
	
}
