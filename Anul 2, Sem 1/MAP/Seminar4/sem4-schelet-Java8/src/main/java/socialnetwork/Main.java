package socialnetwork;

import socialnetwork.config.ApplicationContext;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.UtilizatorValidator;
import socialnetwork.repository.Repository;
import socialnetwork.repository.Repository0;
import socialnetwork.repository.file.UtilizatorFile;
import socialnetwork.repository.file.UtilizatorFile0;

public class Main {
    public static void main(String[] args) {
        String fileName=ApplicationContext.getPROPERTIES().getProperty("data.socialnetwork.users");
        //String fileName="data/users.csv";
        Repository0<Long,Utilizator> userFileRepository = new UtilizatorFile0(fileName
                , new UtilizatorValidator());
//
//        Repository<Long,Utilizator> userFileRepository2 = new UtilizatorFile(fileName
//                , new UtilizatorValidator());
//
        userFileRepository.findAll().forEach(System.out::println);
        System.out.println("ok");

    }
}


