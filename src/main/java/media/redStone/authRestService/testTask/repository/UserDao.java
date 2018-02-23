package media.redStone.authRestService.testTask.repository;


import media.redStone.authRestService.testTask.model.User;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class UserDao extends DatasourceFactory {

    public void createUser(User user) {
        getSession().save(user);
    }

    public void updateUser(User user) {
        getSession().merge(user);
    }

    public void deleteUser(User user) {
        getSession().delete(user);
    }

    public User getUserById(int id) {
        return getSession().get(User.class, id);
    }

    public User getUserByName(String username) {
        Query query = getSession().createQuery("From "+User.class.getSimpleName()+" where name = :username");
        query.setParameter("username", username);
        return (User) query.getSingleResult();
    }

    public List<User> getAllUsers() {
        return (List<User>) getSession().createQuery("FROM " + User.class.getName()).list();
    }
}