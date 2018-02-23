package media.redStone.authRestService.testTask.service;

import media.redStone.authRestService.testTask.repository.UserDao;
import media.redStone.authRestService.testTask.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserCrudService implements UserDetailsService{
    @Autowired
    private UserDao dao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
       return dao.getUserByName(s);
    }
}
