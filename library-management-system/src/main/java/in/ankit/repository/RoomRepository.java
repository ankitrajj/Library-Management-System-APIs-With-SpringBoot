package in.ankit.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import in.ankit.entity.RoomEntity;

import java.util.List;

@Repository
public interface RoomRepository extends CrudRepository<RoomEntity, Long> {
    List<RoomEntity> findByName(String name);
}
