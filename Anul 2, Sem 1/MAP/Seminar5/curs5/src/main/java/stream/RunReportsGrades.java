package stream;

import stream.domain.Nota;
import stream.domain.NotaDto;
import stream.domain.Student;
import stream.domain.Tema;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RunReportsGrades {
    public static void main(String[] args)
    {
        List<Nota> note = getNote(getStudents(), getTeme());
        report1(note);
        report2(note);
    }

    /**
     * creati si afisati o lista de obiecte de nota dto apoi filtrati dupa un anumit profesor
     * toate notele acordate de un anumit profesor
     * toate notele acordate de un anumit profesor la o anumita grupa
     * fiiltru compus adica generalizam
     * @param note
     */
    private static void report1(List<Nota> note)
    {
        Predicate<Nota> byGrupa = x -> x.getStudent().getGroup() == 225;
        Predicate<Nota> byProf = x -> x.getProfesor().equals("Bota");
        Predicate<Nota> filter = byGrupa.and(byProf);

        note.stream()
                .filter(byGrupa)
                .map(x -> new NotaDto(x.getStudent().getName(), x.getTema().getId(), x.getValue(), x.getProfesor()))
                .forEach(x -> System.out.println(x));

    }

    /**
     * media notelor de lab pentru un student
     * @param note
     */

    private static void report2(List<Nota> note)
    {
        Map<Student, List<Nota>> noteStud = note.stream()
                .collect(Collectors.groupingBy(x -> x.getStudent()));
        noteStud.entrySet()
                .forEach(x ->
                        {
                            System.out.println(x.getKey().getName());
                            double suma = x.getValue()
                                    .stream()
                                    .map(y -> y.getValue())
                                    .reduce(0D, (a,b) -> a+b);
                            System.out.println(suma);
                        }
                );
    }

    private static List<Student> getStudents()
    {
        Student s1 = new Student("Alexandra", 225);
        s1.setId(1L);
        Student s2 = new Student("Ioana", 227);
        s1.setId(2L);
        Student s3 = new Student("ALexandru", 225);
        s1.setId(3L);
        Student s4 = new Student("Radu", 226);
        s1.setId(4L);
        Student s5 = new Student("Ana", 227);
        s1.setId(5L);

        return Arrays.asList(s1,s2,s3,s4,s5);
    }

    private static List<Tema> getTeme()
    {
        return Arrays.asList(
                new Tema("1", "Tema MAP"),
                new Tema("2", "Tema PLF"),
                new Tema("3", "Tema BD"),
                new Tema("4", "Tema PS")
        );
    }

    private static List<Nota> getNote(List<Student> stud, List<Tema> teme)
    {
        return Arrays.asList(
                new Nota(stud.get(0), teme.get(0), 9.3, LocalDate.of(2020, 11, 2), "Bota"),
                new Nota(stud.get(1), teme.get(1), 8.7, LocalDate.of(2020, 12, 18), "Antonia"),
                new Nota(stud.get(2), teme.get(2), 10, LocalDate.of(2020, 11, 25), "Bota"),
                new Nota(stud.get(3), teme.get(3), 7.25, LocalDate.of(2020, 10, 4), "Larisa"),
                new Nota(stud.get(4), teme.get(4), 8.9, LocalDate.of(2020, 12, 9), "Antonia")
        );
    }


}
