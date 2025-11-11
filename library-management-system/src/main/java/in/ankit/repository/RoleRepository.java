package in.ankit.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import in.ankit.entity.RoleEntity;


@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, Long> {
    RoleEntity findByRole(String role);
}

