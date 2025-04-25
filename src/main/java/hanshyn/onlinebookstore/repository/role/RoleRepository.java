package hanshyn.onlinebookstore.repository.role;

import hanshyn.onlinebookstore.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(Role.RoleName role);
}
