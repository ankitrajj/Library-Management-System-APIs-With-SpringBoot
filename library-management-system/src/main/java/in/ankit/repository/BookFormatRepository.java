package in.ankit.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import in.ankit.entity.BookFormatEntity;


@Repository
public interface BookFormatRepository extends CrudRepository<BookFormatEntity, Long> {

}
