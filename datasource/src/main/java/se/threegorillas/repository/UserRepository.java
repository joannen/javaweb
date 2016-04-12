package se.threegorillas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import se.threegorillas.model.Team;
import se.threegorillas.model.User;

import java.util.Collection;


public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u left join fetch u.workItems where u.userNumber = ?1")
    User findByUserNumber(String userNumber);

//    Collection<User> findByFirstName(String firstName);

//    Collection<User> findByLastName(String lastName);

//    Collection<User> findByFirstNameAndLastName(String firstName, String lastName);

    @Query("select u from User u left join fetch u.workItems where u.userName = ?1")
    User findByUserName(String userName);

    @Query("select u from User u where u.userName like ?1 or u.lastName like ?1 or u.firstName like ?1")
    Collection<User> findByUserNameOrLastNameOrFirstNameLike(String search);

//    Collection<User> findByFirstNameLike(String firstname);

    @Query("select u from User u left join fetch u.workItems join fetch u.team t where t.id = ?1")
    Collection<User> findByTeamId(Long id);
}
