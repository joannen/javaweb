package se.threegorillas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.threegorillas.model.Team;

/**
 * Created by TheYellowBelliedMarmot on 2016-03-22.
 */

public interface TeamRepository extends JpaRepository<Team, Long> {
}
