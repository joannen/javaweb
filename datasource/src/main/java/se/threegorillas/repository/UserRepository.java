package se.threegorillas.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.threegorillas.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
