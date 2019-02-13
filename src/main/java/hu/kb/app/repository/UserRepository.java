package hu.kb.app.repository;

import hu.kb.app.modell.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Integer> {

}
