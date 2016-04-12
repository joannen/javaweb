package se.threegorillas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.threegorillas.model.WorkItem;

import java.util.Collection;

/**
 * Created by TheYellowBelliedMarmot on 2016-03-22.
 */

@Repository
public interface WorkItemRepository extends JpaRepository<WorkItem, Long> {

    Collection<WorkItem> findByStatus(String status);

    @Query("select w from WorkItem w where w.description like ?1")
    Collection<WorkItem> searchByDescription(String description);

}
