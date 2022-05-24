package app.persistence.interfaces;

import app.model.User;

public interface IUserRepository extends ICrudRepository<String, User>
{
    User getUser(String username, String password);
}
