package socialnetwork.repository.file;

import socialnetwork.domain.Entity;
import socialnetwork.domain.validators.Validator;
import socialnetwork.repository.memory.InMemoryRepository;
import socialnetwork.repository.memory.InMemoryRepository0;

import java.io.*;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


///Aceasta clasa implementeaza sablonul de proiectare Template Method; puteti inlucui solutia propusa cu un Factori (vezi mai jos)
public abstract class AbstractFileRepository<ID, E extends Entity<ID>> extends InMemoryRepository<ID,E> {
    String fileName;
    public AbstractFileRepository(String fileName, Validator<E> validator) {
        super(validator);
        this.fileName=fileName;
        loadData();

    }

    private void loadData() {

        Path path = Paths.get(fileName);
        try{
            List<String> lines = Files.readAllLines(path);


            lines.forEach(
                    linie -> {
                        E entity = extractEntity(Arrays.asList(linie.split(";")));
                        super.save(entity);
                    }
            );
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    /**
     *  extract entity  - template method design pattern
     *  creates an entity of type E having a specified list of @code attributes
     * @param attributes
     * @return an entity of type E
     */
    public abstract E extractEntity(List<String> attributes);
    ///Observatie-Sugestie: in locul metodei template extractEntity, puteti avea un factory pr crearea instantelor entity

    protected abstract String createEntityAsString(E entity);

    @Override
    public Optional<E> save(E entity)
    {
        Optional <E> obj = super.save(entity);
        if(obj.isPresent())
            return obj;
        writeToFile(entity);
        return Optional.empty();
    }

    protected void writeToFile(E entity)
    {
        try{
            BufferedWriter bf = new BufferedWriter(new FileWriter(fileName, true));
            bf.write(createEntityAsString(entity));
            bf.newLine();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


}

