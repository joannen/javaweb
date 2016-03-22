package se.threegorillas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.threegorillas.model.WorkItem;

/**
 * Created by TheYellowBelliedMarmot on 2016-03-22.
 */

@Repository
public interface WorkItemRepository extends JpaRepository<WorkItem, Long> {
}
