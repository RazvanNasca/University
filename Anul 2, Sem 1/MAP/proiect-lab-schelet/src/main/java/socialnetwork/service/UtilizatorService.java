package socialnetwork.service;

import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.Utilizator;
import socialnetwork.repository.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UtilizatorService  {

    private Repository<Long, Utilizator> repo;
    private Repository<Tuple<Long, Long>, Prietenie> repoPrietenie;

    public UtilizatorService(Repository<Long, Utilizator> repo, Repository<Tuple<Long, Long>, Prietenie> repoPrietenie)
    {
        this.repo = repo;
        this.repoPrietenie = repoPrietenie;
    }

    public Utilizator addUtilizator(Utilizator utilizator)
    {
        return repo.save(utilizator);
    }

    public Utilizator deleteUtilizator(Long id)
    {
        for(Utilizator it : repo.findAll())
            repoPrietenie.delete(new Tuple(it.getId(), id));

        return repo.delete(id);
    }

    public Map<Utilizator, LocalDateTime> ceva(Long id)
    {
        Map<Utilizator, LocalDateTime> DTO = new HashMap<>();
        Iterable<Prietenie> all = repoPrietenie.findAll();
        List<Prietenie> rez = new ArrayList<>();

        for(Prietenie it: all)
            rez.add(it);

        Predicate<Prietenie> byId = x -> (x.getId().getLeft() == id || x.getId().getRight() == id);

        rez.stream()
                .filter(byId)
                .forEach(x -> {
                    if(x.getId().getLeft() != id)
                        DTO.put(repo.findOne(x.getId().getLeft()), x.getDate());
                    else
                        DTO.put(repo.findOne(x.getId().getRight()), x.getDate());
                });
        return DTO;
    }

    public Map <Long, LocalDateTime> ListaPrieteni(Long id)
    {
        Map <Long, LocalDateTime> DTO = new HashMap<>();
        Iterable<Prietenie> all = repoPrietenie.findAll();

        for(Prietenie it: all)
            if(it.getId().getRight() == id)
                DTO.put(it.getId().getLeft(), it.getDate());
            else
                if(it.getId().getLeft() == id)
                    DTO.put(it.getId().getRight(), it.getDate());

        return DTO;
    }

    public Iterable<Utilizator> getAll()
    {
        Iterable<Utilizator> all = repo.findAll();
        List<Utilizator> rez = new ArrayList<Utilizator>();

        for(Utilizator it : all)
        {
            //Map<Long, LocalDateTime> prt = ListaPrieteni(it.getId());
            Map<Utilizator, LocalDateTime> prt = ceva(it.getId());
            for(Map.Entry<Utilizator, LocalDateTime> fr: prt.entrySet())
                it.getFriends().add(repo.findOne(fr.getKey().getId()));

            rez.add(it);
        }

        return rez;
    }

    ///TO DO: add other methods
}
