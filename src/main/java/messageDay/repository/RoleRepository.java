package messageDay.repository;

import messageDay.entities.RoleEntity ;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, Integer> {
    RoleEntity findByRole(String role);
}
