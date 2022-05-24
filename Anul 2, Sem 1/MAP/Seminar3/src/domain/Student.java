package domain;

import java.util.Objects;

public class Student {
    private String nume;
    private float media;

    public Student(String nume, float media)
    {
        this.nume = nume;
        this.media = media;
    }

    @Override
    public String toString() {
        return "Student { Nume: " + nume + " Media: " + media + "}\n";
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null || obj.getClass() != this.getClass())
            return false;

        Student student  = (Student) obj;

        return Float.compare(student.media, media) == 0 && Objects.equals(nume, student.nume);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nume, media);
    }

    public String getNume() { return nume; }

    public float getMedie(){return media;}
}
