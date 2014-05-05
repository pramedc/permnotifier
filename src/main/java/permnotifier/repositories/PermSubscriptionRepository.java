package permnotifier.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import permnotifier.domain.PermSubscription;

public interface PermSubscriptionRepository extends PagingAndSortingRepository<PermSubscription, Long> {

}
