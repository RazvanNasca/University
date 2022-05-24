package repository;


import festival.model.User;

public interface RepoUser extends CrudRepo<String, User> {

    User findByName(String name);

}
