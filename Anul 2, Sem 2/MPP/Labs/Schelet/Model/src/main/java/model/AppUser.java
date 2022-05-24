package model;

import java.io.Serializable;

public class AppUser implements Comparable<AppUser>, Serializable {

    private String name;
    private String password;

    public AppUser(){}

    public AppUser(String name, String password)
    {
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
    public int compareTo(AppUser o) {
        return name.compareTo(o.name);
    }
}
