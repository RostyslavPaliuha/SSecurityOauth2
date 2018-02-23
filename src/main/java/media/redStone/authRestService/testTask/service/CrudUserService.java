package media.redStone.authRestService.testTask.service;

import media.redStone.authRestService.testTask.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface CrudUserService extends UserDetailsService {
    public List<User> getListOfUsers();
}
