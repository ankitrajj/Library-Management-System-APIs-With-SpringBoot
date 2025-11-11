package in.ankit.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import in.ankit.entity.TaskEntity;


@Repository
public interface TaskRepository extends CrudRepository<TaskEntity, Long> {

}
