package socialnetwork.service;

import socialnetwork.domain.*;
import socialnetwork.domain.validators.ValidationException;
import socialnetwork.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class MesajeService {

    private Repository<Tuple<Long, Long>, Prietenie> repoPrietenie;
    private Repository<Long, Utilizator> repoUtilizatori;
    private Repository<Long, Mesaje> repoMesaje;

    public MesajeService(Repository<Long, Mesaje> repoMesaje, Repository<Tuple<Long, Long>, Prietenie> repoPrietenie, Repository<Long, Utilizator> repoUtilizatori)
    {
        this.repoMesaje = repoMesaje;
        this.repoPrietenie = repoPrietenie;
        this.repoUtilizatori = repoUtilizatori;
    }

    public Mesaje addMesaj(Mesaje mesaj)
    {
        Long idSursa = mesaj.getFrom();
        for(Long it : mesaj.getTo())
            if(repoPrietenie.findOne(new Tuple(idSursa, it)) == null)
                throw new ValidationException("Nu esti prieten cu " + it + " deci nu ii poti trimite mesaje!");

        repoMesaje.save(mesaj);

        return null;
    }

    public void addReply(Long id, Mesaje mesaj)
    {
        mesaj.getReply().add(id);
        repoMesaje.update(mesaj);
    }

    public List<Mesaje> conversatie(Long id1, Long id2)
    {
        Iterable<Mesaje> all = getAll();
        List<Mesaje> rez = new ArrayList<>();
        for(Mesaje it : all)
            for(Long ceva : it.getTo())
                if(ceva == id2 && it.getFrom() == id1)
                    rez.add(it);

                return rez;
    }

    public List<Long> Parseaza(String destinatie)
    {
        List<Long> all = new ArrayList<>();
        String[] ids = destinatie.split(" ");

        for (String it : ids)
            all.add(Long.parseLong(it));

        for(int i = 0; i < all.size() - 1; i++)
            for(int j = i + 1; j < all.size(); j++)
                if(all.get(i) == all.get(j))
                    throw new ValidationException("Trimiti de 2 ori la un utilizator!");

        return all;
    }

    public List<Mesaje> mesajeleUnuiUtilizator(Long id)
    {
        Iterable<Mesaje> mesaje = getAll();
        List<Mesaje> rez = new ArrayList<>();

        for(Mesaje it : mesaje)
            for(Long u : it.getTo())
                if(u.equals(id))
                    rez.add(it);

                return rez;
    }

    public Mesaje findOne(Long id)
    {
        return repoMesaje.findOne(id);
    }

    public Iterable<Mesaje> getAll()
    {
        return repoMesaje.findAll();
    }

}
