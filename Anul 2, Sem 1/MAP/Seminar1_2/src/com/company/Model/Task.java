package com.company.Model;

import java.util.Objects;

public abstract class Task {

    private String taskID;
    private String descriere;

    public Task(){}

    public Task(String id, String d) {
        taskID = id;
        descriere = d;
    }

    public String getId() {
        return taskID;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setId(String id) {
        taskID = id;
    }

    public void setDescriere(String d) {
        descriere = d;
    }

    @Override
    public String toString() {
        return this.taskID + " " + this.descriere;
    }

    public abstract void execute();

    @Override
    /*public boolean equals(Object obj)
    {
        return obj.hashCode() == this.hashCode();
    }*/
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof Task))
            return false;

        Task t = (Task) obj;
        return (Objects.equals(t.getId(), getId()) && Objects.equals(t.getDescriere(), getDescriere()));
    }

    public int hashCode() {
        return Objects.hash(getId(), getDescriere());
    }

}
