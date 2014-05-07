package permnotifier.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import permnotifier.domain.PermRecord;

public interface DolItemRepository extends PagingAndSortingRepository<PermRecord, Long> {

	@Query("SELECT e FROM PermRecord as e WHERE e.caseId = :caseId")
	public PermRecord findByCaseId(@Param("caseId") int caseId);
	
	@Query("SELECT e FROM PermRecord as e WHERE e.caseNumber = :caseNumber")
	public PermRecord findByCaseNumber(@Param("caseNumber") String caseNumber);
}
