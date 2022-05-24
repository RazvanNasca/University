package app.persistence.interfaces;

import app.model.User;

public interface UserRepository extends Repository<String, User> {
    User getUser(String username, String password);
}
