package festival.model;

import java.io.Serializable;
import java.util.Objects;

public class User extends Entity<String> implements Comparable<User>, Serializable {

    private String name;
    private String password;

    public User(String name, String password)
    {
        this.setId(name);
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String newName) {
        name = newName;
    }

    public void setPassword(String newPassword) {
        password = newPassword;
    }

    @Override
    public int compareTo(User o) {
        return name.compareTo(o.name);
    }
}
