package container;

import domain.Student;

import java.util.*;

public class MyMap {

    Map<Integer, List<Student>>  map;

    public MyMap(){

        this.map = new TreeMap<Integer, List<Student>>();
    }

    public void add(Student student){

        int medie = Math.round(student.getMedie());
        List<Student> studenti = map.get(medie);
        if(studenti != null)
            studenti.add(student);
        else
        {
            studenti = new ArrayList<Student>();
            studenti.add(student);
            map.put(medie, studenti);
        }
    }

    public void printAll()
    {
        for(Map.Entry<Integer, List<Student>> pereche : map.entrySet())
            System.out.println(pereche.getKey() + " " + pereche.getValue().toString());
    }

    static class ComparatorMedie implements Comparator<Student>
    {
        @Override
        public int compare(Student o1, Student o2) {
            return Math.round(o1.getMedie()) - Math.round(o2.getMedie());
        }
    }

}
