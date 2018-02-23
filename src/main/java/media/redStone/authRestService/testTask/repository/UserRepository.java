package media.redStone.authRestService.testTask.repository;

import media.redStone.authRestService.testTask.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
  public User findByName(String name);

}
