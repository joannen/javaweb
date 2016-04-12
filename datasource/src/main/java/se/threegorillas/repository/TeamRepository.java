package se.threegorillas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.threegorillas.model.Team;

import java.util.Collection;

/**
 * Created by TheYellowBelliedMarmot on 2016-03-22.
 */

public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query("select t from Team t left join fetch t.users where t.id = ?1")
    Team findOne(Long id);

    @Query("select t from Team t left join fetch t.users where t.teamName = ?1")
    Team findByTeamName(String teamName);

//    Collection<Team> findByTeamStatus(String teamStatus);

}
