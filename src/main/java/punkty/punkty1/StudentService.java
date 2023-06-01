package punkty.punkty1;
import io.vavr.collection.List;
import org.springframework.stereotype.Service;
import punkty.punkty1.db.IStudentRepository;
import punkty.punkty1.db.StudentRow;

import java.util.function.Function;

@Service
public class StudentService {
    private final IStudentRepository repository;

    public StudentService(IStudentRepository repository) {
        this.repository = repository;
    }
    List<Student> getStudents() {
        return List.ofAll(this.repository.findAll())
                .map(StudentRow::toStudent);
    }

    Student addStudent(final NewStudent newStudent) {
        return this.repository.save(new StudentRow(
                newStudent.name,
                newStudent.number,
                newStudent.grupa
        )).toStudent();
    }
}
