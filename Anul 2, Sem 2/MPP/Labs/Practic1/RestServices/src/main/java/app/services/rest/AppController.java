package app.services.rest;

import app.model.Cuvant;
import app.model.Joc;
import app.model.ManaDTO;
import app.persistence.interfaces.CuvantRepository;
import app.persistence.interfaces.JocRepository;
import app.persistence.interfaces.ManaRepository;
import app.persistence.interfaces.RundaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jocCuvinte")
public class AppController {
    private static final String template = "Hello, %s!";

    @Autowired
    private JocRepository jocRepository;
    @Autowired
    private ManaRepository manaRepository;
   /* @Autowired
    private CuvantRepository cuvantRepository;*/


    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name){
        return String.format(template, name);
    }

   /* @RequestMapping(value = "/jocuri/{id}", method = RequestMethod.GET)
    public List<Cuvant> getById(@PathVariable String id){
        return cuvantRepository.getCuvinteFromJoc(Integer.parseInt(id));
    }*/

    @RequestMapping(value = "/jocuri/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable String id){
        Joc joc = jocRepository.findOne(Integer.parseInt(id));
        if(joc == null)
            return new ResponseEntity<String>("Joc not found", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<Joc>(joc, HttpStatus.OK);
    }

    @RequestMapping(value = "/situatie/maini", method = RequestMethod.GET)
    public List<ManaDTO> getMainiByJocSiUser(@RequestParam(value = "username", defaultValue = "user1") String username, @RequestParam(value = "nrJoc", defaultValue = "1")int nrJoc){
        return manaRepository.getMainiByJucatorSiJoc(nrJoc, username);
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String showError(ServiceException e){
        return e.getMessage();
    }
}
