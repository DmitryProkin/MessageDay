package messageDay.repository;

import messageDay.entities.MessagesEntity;
import org.springframework.data.repository.CrudRepository;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public interface MessagesRepository extends CrudRepository<MessagesEntity,Integer> {
    MessagesEntity findAllByDateCreateEquals(Date date);
    List<MessagesEntity> findAllByDateCreateIsLessThanEqual(Date date);
    List<MessagesEntity> findAll();


}
