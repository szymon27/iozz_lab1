package punkty.punkty1;
import io.vavr.collection.List;
public class StudentService {
    private List<Student> students = List.empty();
    List<Student> getStudents() {
        return this.students;
    }

    Student addStudent(NewStudent newStudent) {
        Student created = new Student(this.students.size() + 1, newStudent.name, newStudent.number, newStudent.grupa);
        students=students.prepend(created);
        return created;
    }
}
