package punkty.punkty1;


import io.vavr.collection.List;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import punkty.punkty1.db.IStudentRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class StudentServiceTest {
    @Autowired
    private IStudentRepository repository;

    @AfterEach
    public void cleanAfterTest() {
        this.repository.deleteAll();
    }
    @Test
    public void getEmptyList(){
        final StudentService service = new StudentService(this.repository);
        List<Student> students = service.getStudents();
        assertTrue(students.isEmpty());
    }

    @Test
    public void addStudent() {
        final StudentService service=new StudentService(this.repository);
        final Student created=service.addStudent(new NewStudent("Student1","1-2-3","IP"));
        assertNotNull(created);
    }

    @Test
    public void addStudentIsReturned() {
        final StudentService service=new StudentService(this.repository);
        final Student created=service.addStudent(new NewStudent("Student1","1-2-3","IP"));
        final List<Student> all = service.getStudents();
        assertEquals(created.name, all.head().name);
    }

    @Test
    public void addStudentHasNewId() {
        final StudentService service=new StudentService(this.repository);
        final Student created1=service.addStudent(new NewStudent("Student1","1-2-3","IP"));
        final Student created2=service.addStudent(new NewStudent("Student2","1-2-3","IP"));
        assertNotEquals(created1.id, created2.id);
        assertEquals(2, service.getStudents().size());
    }
}