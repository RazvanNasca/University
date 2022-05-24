package socialnetwork.service;

import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.ValidationException;
import socialnetwork.repository.RepoException;
import socialnetwork.repository.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class PrietenieService {

    private Repository<Long, Utilizator> repoUtilizatori;
    private Repository<Tuple<Long, Long>, Prietenie> repoPrietenie;


    public PrietenieService(Repository<Tuple<Long, Long>, Prietenie> repoPrietenie, Repository<Long, Utilizator> repoUtilizatori)
    {
        this.repoPrietenie = repoPrietenie;
        this.repoUtilizatori = repoUtilizatori;

        for(Prietenie it : this.repoPrietenie.findAll())
        {
            Utilizator u1 = this.repoUtilizatori.findOne(it.getId().getLeft());
            Utilizator u2 = this.repoUtilizatori.findOne(it.getId().getRight());
            u1.getFriends().add(u2);
            u2.getFriends().add(u1);
        }
    }

    public Prietenie addPrietenie(Prietenie prietenie)
    {
        /// cautam primul utilizator
        Long id1 = prietenie.getId().getLeft();
        Utilizator utilizator1 = repoUtilizatori.findOne(id1);
        if(utilizator1 == null)
            throw new ValidationException("Id inexistent!\n");

        /// cautam utilizatorul 2
        Long id2 = prietenie.getId().getRight();
        Utilizator utilizator2 = repoUtilizatori.findOne(id2);
        if(utilizator2 == null)
            throw new ValidationException("Id inexistent!\n");

        Prietenie eroare2 = repoPrietenie.findOne(new Tuple(id1, id2));
        if(eroare2 != null)
            throw new ValidationException("Id existent!\n");

        repoPrietenie.save(prietenie);

        ///actualizam lista de prieteni a utilizatorilor
        utilizator1.getFriends().add(utilizator2);
        utilizator2.getFriends().add(utilizator1);

        return null;
    }

    public Prietenie deletePrietenie(Tuple<Long, Long> id)
    {
        /// cautam primul utilizator
        Long id1 = id.getLeft();
        Utilizator utilizator1 = repoUtilizatori.findOne(id1);
        if(utilizator1 == null)
            throw new ValidationException("Id inexistent!\n");

        /// cautam utilizatorul 2
        Long id2 = id.getRight();
        Utilizator utilizator2 = repoUtilizatori.findOne(id2);
        if(utilizator2 == null)
            throw new ValidationException("Id inexistent!\n");

        /// cautam prietenia
        Prietenie eroare2 = repoPrietenie.findOne(id);
        if(eroare2 != null)
        {
            repoPrietenie.delete(id);
            utilizator1.getFriends().remove(utilizator2);
            utilizator2.getFriends().remove(utilizator1);
            return null;
        }

        return null;
    }

    private Map<Long, Integer> vizitat = new HashMap<Long, Integer>();

    private void DFS(Utilizator nod, int nr)
    {
        vizitat.put(nod.getId(), nr);
        for(Utilizator it : nod.getFriends())
            if(vizitat.get(it.getId()) == 0)
                DFS(it, nr);
    }

    public int NumarComponnteConexe()
    {
        Iterable<Utilizator> all = repoUtilizatori.findAll();
        List<Utilizator> rez = new ArrayList<Utilizator>();

        for(Utilizator it : all)
        {
            ///Map<Long, LocalDateTime> prt = ListaPrieteni(it.getId());
            Map <Long, LocalDateTime> prt = new HashMap<>();
            Iterable<Prietenie> toti = repoPrietenie.findAll();

            for(Prietenie it1: toti)
                if(it1.getId().getRight() == it.getId())
                    prt.put(it1.getId().getLeft(), it1.getDate());
                else
                if(it1.getId().getLeft() == it.getId())
                    prt.put(it1.getId().getRight(), it1.getDate());

            for(Map.Entry<Long, LocalDateTime> fr: prt.entrySet())
                it.getFriends().add(repoUtilizatori.findOne(fr.getKey()));

            rez.add(it);
        }

        int nr = 0;

        /// marcam toate id-urile ca nevizitate
        for(Utilizator it : all)
            vizitat.put(it.getId(), 0);

        /// parcurgeam toti utilizatorii care nu au fost vizitati anterior
        for(Utilizator it : all)
            if(vizitat.get(it.getId()) == 0)
            {
                nr++;
                DFS(it, nr);
            }

        return nr;
    }

    public StringBuilder Comunitaate()
    {
        NumarComponnteConexe();
        int maxim = -1, nr = 0;
        Iterable<Utilizator> all = repoUtilizatori.findAll();
        StringBuilder rezultat = new StringBuilder("{ ");

        /// aflam comunitatea maxima
        for(Utilizator it : all)
        {
            int cnt = 0;
            Iterable<Utilizator> all2 = repoUtilizatori.findAll();
            for(Utilizator it2 : all2)
                ///fac parte din aceeasi comunitate
                if(vizitat.get(it.getId()) == vizitat.get(it2.getId()))
                    cnt++;

            if(cnt > maxim)
            {
                maxim = cnt;
                nr = vizitat.get(it.getId());
            }
        }

        for(Utilizator it : all)
            if(vizitat.get(it.getId()) == nr)
                rezultat.append(it.getFirstName()).append(" ");

        rezultat.append("}");
        return rezultat; /// lista
    }

    public Iterable<Prietenie> getAll()
    {
        return repoPrietenie.findAll();
    }


}
