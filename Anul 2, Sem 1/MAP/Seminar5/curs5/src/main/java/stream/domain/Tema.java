package stream.domain;

import java.util.Objects;

public class Tema {
    private String id;
    private String desc;

    public Tema(String id, String desc) {
        this.id = id;
        this.desc=desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "Tema{" +
                "id='" + id + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tema)) return false;
        Tema tema = (Tema) o;
        return getId().equals(tema.getId()) &&
                getDesc().equals(tema.getDesc());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDesc());
    }
}

