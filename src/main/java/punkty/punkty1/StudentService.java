package punkty.punkty1;
import io.vavr.collection.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import punkty.punkty1.db.IStudentRepository;
import punkty.punkty1.db.StudentRow;

import java.util.Optional;
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

    @Transactional
    public Optional<Student> changeNumber (long studentId, String newNumber) {
        final Optional<StudentRow> student = this.repository.findById(studentId);
        return student.map(c -> {
            c.setNumber(newNumber);
            return c.toStudent();
        });
    }
}
