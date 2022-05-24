package app.services.rest;

import app.model.Mana;
import app.model.User;
import app.persistence.interfaces.IManaRepository;
import app.persistence.interfaces.IPropunereRepository;
import app.persistence.interfaces.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/app")
public class AppController
{
    private static final String template = "Hello, %s!";

    @Autowired
    private IPropunereRepository propunereRepository;

    @Autowired
    private IManaRepository manaRepository;


    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name){ return String.format(template, name); }

    @RequestMapping(value = "/jocuri/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable String id)
    {
        Map<String, String> propuneri = propunereRepository.getPropuneriByJoc(Integer.parseInt(id));
        if(propuneri == null)
            return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity< Map<String, String>>(propuneri, HttpStatus.OK);
    }

    @RequestMapping(value = "/{user}/{joc}", method = RequestMethod.GET)
    public List<Mana> getHandByGameAndUser(@PathVariable String user, @PathVariable int joc)
    {
        return manaRepository.getHandByGameAndUser(joc, user);
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String showError(ServiceException e){
        return e.getMessage();
    }
}
