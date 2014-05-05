package permnotifier.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import permnotifier.domain.DolItem;

public interface DolItemRepository extends PagingAndSortingRepository<DolItem, Long> {

	@Query("SELECT e FROM DolItem as e WHERE e.caseId = :caseId")
	public DolItem findByCaseId(@Param("caseId") int caseId);
}
