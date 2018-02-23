package media.redStone.authRestService.testTask.service;

import media.redStone.authRestService.testTask.model.User;
import media.redStone.authRestService.testTask.repository.UserDao;
import media.redStone.authRestService.testTask.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCrudService implements CrudUserService{
    @Autowired
    private UserDao dao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
       return dao.getUserByName(s);
    }

    @Override
    public List<User> getListOfUsers() {
       return dao.getAllUsers();
    }


}
