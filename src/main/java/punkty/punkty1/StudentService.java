package punkty.punkty1;
import io.vavr.collection.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import punkty.punkty1.db.IScoreRepository;
import punkty.punkty1.db.IStudentRepository;
import punkty.punkty1.db.ScoreRow;
import punkty.punkty1.db.StudentRow;

import java.util.Optional;

@Service
public class StudentService {
    private final IStudentRepository studentRepository;
    private final IScoreRepository scoreRepository;

    public StudentService(IStudentRepository studentRepository, IScoreRepository scoreRepository) {
        this.studentRepository = studentRepository;
        this.scoreRepository = scoreRepository;
    }
    List<Student> getStudents() {
        return List.ofAll(this.studentRepository.findAll())
                .map(StudentRow::toStudent);
    }

    Student addStudent(final NewStudent newStudent) {
        return this.studentRepository.save(new StudentRow(
                newStudent.name,
                newStudent.number,
                newStudent.grupa
        )).toStudent();
    }

    @Transactional
    public Optional<Student> changeNumber (long studentId, String newNumber) {
        final Optional<StudentRow> student = this.studentRepository.findById(studentId);
        return student.map(c -> {
            c.setNumber(newNumber);
            return c.toStudent();
        });
    }

    @Transactional
    public Optional<Integer> addScore(final long studentId, final Score score) {
        final Optional<StudentRow> student = this.studentRepository.findById(studentId);
        return student.map(c-> {
            int existingScore = List.ofAll(c.getScores())
                    .foldLeft(0, (p, s) -> p + s.getScore());
            final ScoreRow newScore = new ScoreRow(score.score, score.comment, c);
            this.scoreRepository.save(newScore);
            return existingScore + score.score;
        });
    }
}
