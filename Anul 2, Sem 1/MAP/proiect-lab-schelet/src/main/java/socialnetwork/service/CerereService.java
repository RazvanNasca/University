package socialnetwork.service;

import socialnetwork.domain.CererePrietenie;
import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.ValidationException;
import socialnetwork.domain.validators.Validator;
import socialnetwork.repository.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CerereService {

    private Repository<Tuple<Long, Long>, CererePrietenie> repoCerere;
    private Repository<Tuple<Long, Long>, Prietenie> repoPrietenie;
    private Repository<Long, Utilizator> repoUtilizatori;

    public CerereService(Repository<Tuple<Long, Long>, CererePrietenie> repoCerere,  Repository<Tuple<Long, Long>, Prietenie> repoPrietenie, Repository<Long, Utilizator> repoUtilizatori)
    {
        this.repoCerere = repoCerere;
        this.repoPrietenie = repoPrietenie;
        this.repoUtilizatori = repoUtilizatori;
    }

    public CererePrietenie addCerere(CererePrietenie cerere)
    {
        /// cautam utilizatorul 2
        Long idMain = cerere.getId().getRight();
        Long idPrieten = cerere.getId().getRight();
        Utilizator utilizator2 = repoUtilizatori.findOne(idPrieten);
        if(utilizator2 == null)
            throw new ValidationException("Id inexistent!\n");

        /// caut daca exista prietenia
        Prietenie prietenie = repoPrietenie.findOne(new Tuple(idMain, idPrieten));
        if(prietenie != null)
            throw new ValidationException("Prietenia exista deja!\n");

        /// caut daca cererea este trimisa
        CererePrietenie eroare2 = repoCerere.findOne(new Tuple(idMain, idPrieten));
        if(eroare2 != null)
            throw new ValidationException("Cererea deja exista!\n");

        repoCerere.save(cerere);

        return null;
    }

    public CererePrietenie raspundeCerere(Tuple<Long, Long> id, String raspuns)
    {
        CererePrietenie cerere = repoCerere.findOne(id);
        if(cerere == null)
            throw new ValidationException("Nu exista cererea de prietenie");

        if(raspuns.equals("A"))
        {
            Prietenie prieten = new Prietenie();
            prieten.setId(id);
            prieten.setDate(LocalDateTime.now());
            repoPrietenie.save(prieten);
            repoCerere.delete(id);
        }
        else
            repoCerere.delete(id);

        return null;
    }

    public List<Utilizator> CereriInAsteptare(Long id)
    {
        List<Utilizator> lista = new ArrayList<>();
        Iterable<CererePrietenie> all = repoCerere.findAll();

        for(CererePrietenie it: all)
            if(it.getStatus().equals("PENDING"))
                if(it.getId().getLeft() == id)
                    lista.add(repoUtilizatori.findOne(it.getId().getRight()));

        return lista;
    }

}
