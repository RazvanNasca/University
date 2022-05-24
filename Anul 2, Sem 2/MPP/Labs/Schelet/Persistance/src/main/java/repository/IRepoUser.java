package repository;


import model.AppUser;

public interface IRepoUser extends CrudRepo<String, AppUser> {

    AppUser findByName(String name);

}
