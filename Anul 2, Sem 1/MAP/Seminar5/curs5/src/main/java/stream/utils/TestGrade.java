package stream.utils;




import stream.domain.Nota;
import stream.domain.NotaDto;
import stream.domain.Student;
import stream.domain.Tema;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TestGrade {
    private static List<Student> getStudents() {
        Student s1 = new Student("andrei", 221);
        s1.setId(2l);
        Student s2 = new Student("dan", 222);
        s2.setId(2l);
        Student s3 = new Student("gigi", 221);
        s3.setId(2l);
        Student s4 = new Student("costel", 222);
        s4.setId(2l);
        return Arrays.asList(s1, s2, s3, s4);
    }

    private static List<Tema> getTeme() {
        return Arrays.asList(
                new Tema("t1", "desc1"),
                new Tema("t2", "desc2"),
                new Tema("t3", "desc3"),
                new Tema("t4", "desc4")
        );
    }

    private static List<Nota> getNote(List<Student> stud, List<Tema> teme) {
        return Arrays.asList(
                new Nota(stud.get(0), teme.get(0), 10d, LocalDate.of(2019, 11, 2), "profesor1"),
                new Nota(stud.get(1), teme.get(0), 9d, LocalDate.of(2019, 11, 2).minusWeeks(1), "profesor1"),
                new Nota(stud.get(1), teme.get(1), 10d, LocalDate.of(2019, 10, 20), "profesor2"),
                new Nota(stud.get(1), teme.get(2), 10d, LocalDate.of(2019, 10, 20), "profesor2"),
                new Nota(stud.get(2), teme.get(1), 7d, LocalDate.of(2019, 10, 28), "profesor1"),
                new Nota(stud.get(2), teme.get(3), 9d, LocalDate.of(2019, 10, 27), "profesor2"),
                new Nota(stud.get(1), teme.get(3), 10d, LocalDate.of(2019, 10, 29), "profesor2")
        );
    }

    public static void main(String[] args) {
//        NotaDto.forEach(x-> System.out.println(x));
//        System.out.println(LocalDate.of(2019,10,29).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
//
        List<Nota> note = getNote(getStudents(), getTeme());
        report1(note);
        report2(note);


    }

    /**
     * creati/afisati o lista de obiecte NotaDto apoi filtrati dupa un anumit profesor
     * (toate notele acordate de un anumit profesor)
     * (toate notele acordate de un anumit profesor, la o anumita grupa)
     * GENERALIZARE: FILTRU COMPUS
     */
    private static void report1(List<Nota> note) {
        Predicate<Nota> byProf=x->x.getProfesor().equals("profesor1");

    }

    /**
     * media notelor de lab pt fiecare student
     *
     * @param note
     */
    private static void report2(List<Nota> note) {
        Map<Student, List<Nota>> grouped=note.stream()
                .collect(Collectors.groupingBy(x->x.getStudent()));


    }



}
