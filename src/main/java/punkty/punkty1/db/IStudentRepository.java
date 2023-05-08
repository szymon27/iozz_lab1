package punkty.punkty1.db;

import org.springframework.data.repository.CrudRepository;

public interface IStudentRepository extends CrudRepository<StudentRow, Long> {
}
