package socialnetwork.service;

import socialnetwork.domain.Utilizator;
import socialnetwork.repository.Repository;



import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UtilizatorService {
    private Repository<Long, Utilizator> repo;

    public UtilizatorService(Repository<Long, Utilizator> repo) {
        this.repo = repo;
    }

    public Optional<Utilizator> addUtilizator(Utilizator utilizator) {
        return repo.save(utilizator);
    }

    public List<Utilizator> getAllUsers()
    {
        Iterable <Utilizator> utilizatori = repo.findAll();
        return StreamSupport.stream(utilizatori.spliterator(),false).collect(Collectors.toList());
    }


    public List<Utilizator> filterUsersName(String s) {
        return null;
    }
}
