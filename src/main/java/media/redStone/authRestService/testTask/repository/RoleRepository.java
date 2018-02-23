package media.redStone.authRestService.testTask.repository;


import media.redStone.authRestService.testTask.model.Role;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class RoleRepository extends DatasourceFactory {
    public Role getRoleById(int id) {
        return (Role) getSession().get(Role.class, id);
    }

    public List<Role> getAllRoles() {
        return (List<Role>) getSession().createQuery("FROM " + Role.class.getName()).list();
    }
}