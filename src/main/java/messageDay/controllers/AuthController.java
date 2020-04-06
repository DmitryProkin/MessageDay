package messageDay.controllers;

import messageDay.entities.UserEntity;

import messageDay.repository.UserRepository ;
import messageDay.service.UserService ;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping
public class AuthController {

    private final UserService userService;
    private final UserRepository userRepository;

    public AuthController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView model = new ModelAndView();

        Optional<UserEntity> optUser = userRepository.findByActiveAndRoleId(1, 1);


        boolean isFirst = !optUser.isPresent();

        model.addObject("first", isFirst);

        model.setViewName("login");
        return model;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView signup() {
        ModelAndView model = new ModelAndView();
        UserEntity user = new UserEntity();
        model.addObject("user", user);
        model.setViewName("signup");

        return model;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ModelAndView createUser(@Valid UserEntity user, BindingResult bindingResult) {
        ModelAndView model = new ModelAndView();
        UserEntity userExists = userService.findUserByLogin(user.getLogin());

        if (userExists != null) {
            bindingResult.rejectValue("login", "error.user", "This login already exists!");
        }
        if (bindingResult.hasErrors()) {
            model.setViewName("signup");
        } else {
            user.setRoleId(1);
            userService.saveSingUpUser(user);
            model.addObject("msg", "User has been registered successfully!");
            model.setViewName("redirect:/login");
        }
        return model;
    }

    @RequestMapping(value = "/checkLogin", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> checkLogin(@RequestBody String login) {
        UserEntity userExists = userService.findUserByLogin(login);

        return new ResponseEntity<>(userExists != null, HttpStatus.OK);
    }

}