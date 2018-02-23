package media.redStone.authRestService.testTask.service;

import media.redStone.authRestService.testTask.model.User;
import media.redStone.authRestService.testTask.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCrudService implements CrudUserService {
    @Autowired
    private UserRepository dao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return dao.getUserByName(s);
    }

    @Override
    public List<User> getListOfUsers() {
        return dao.getAllUsers();
    }

    @Override
    public User getById(Long id) {
        return dao.getUserById(id);
    }

    @Override
    public boolean checkIfExists(User user) {
        return dao.checkIfExists(user);
    }

    @Override
    public void saveUser(User user) {
        dao.createUser(user);
    }

    @Override
    public void updateUser(User user) {
        dao.updateUser(user);
    }

    @Override
    public void deleteUserById(Long id) {
        dao.deleteUser(id);
    }

    @Override
    public void deleteAllUsers() {
        dao.deleteAllUsers();
    }


}
