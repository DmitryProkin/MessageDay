package messageDay.controllers;

import messageDay.entities.MessagesEntity;
import messageDay.entities.UserEntity;
import messageDay.repository.MessagesRepository;
import messageDay.service.UserService ;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Calendar;
import java.util.List;


@Controller
public class IndexController {

    private final UserService userService;
    private final MessagesRepository messagesRepository;
    private final MessageController messageController;
    public static Integer counter ;
    public static Integer size ;
    public List<MessagesEntity> messagesList;

    public IndexController(UserService userService, MessagesRepository messagesRepository, MessageController messageController) {

        this.userService = userService;
        this.messagesRepository = messagesRepository;
        this.messageController = messageController;


        init();

    }
    public void init(){
        java.sql.Date currentDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        messagesList = messagesRepository.findAllByDateCreateIsLessThanEqual(currentDate);
        counter = messagesList.size();
//        counter--;
        size = messagesList.size();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView root() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!auth.isAuthenticated()) {
            return new ModelAndView("redirect:/login");
        }

        ModelAndView model = new ModelAndView();
        model.setViewName("redirect:/home/home");
        return model;
    }




    @RequestMapping(value = "/home/home", method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userService.findUserByLogin(auth.getName());
        Integer roleId = userService.getAuthenticationUser().getRoleId();

        init();

        if(counter ==size){
            counter--;
        }

        model.addObject("messageList",messagesList);
        if(counter>=0 && size>0){
            model.addObject("counter",counter);
            model.addObject("size",size);
        }
        model.addObject("userName", user.getFIO());
        model.addObject("role", user.getRole().getRole());
        model.addObject("roleId", roleId);
        model.setViewName("home/home");
        return model;
    }

    @RequestMapping(value = "/access_denied", method = RequestMethod.GET)
    public ModelAndView accessDenied() {
        ModelAndView model = new ModelAndView();
        model.setViewName("errors/access_denied");
        return model;
    }

    @RequestMapping(value = "/home/home/back", method = RequestMethod.GET)
    public ModelAndView decriment(){
        if(counter>0) {
            counter--;
        }


        ModelAndView model = new ModelAndView();
        model = constructModel();

        return model;

    }

    @RequestMapping(value = "/home/home/next", method = RequestMethod.GET)
    public ModelAndView increment(){
        if(counter<size-1) {
            counter++;
        }

        ModelAndView model = new ModelAndView();
        model = constructModel();


        return model;

    }
    public ModelAndView constructModel(){
        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userService.findUserByLogin(auth.getName());
        Integer roleId = userService.getAuthenticationUser().getRoleId();
        model.addObject("messageList",messagesList);
        if(counter>=0 && size>0){
            model.addObject("counter",counter);
            model.addObject("size",size);
        }
        model.addObject("userName", user.getFIO());
        model.addObject("role", user.getRole().getRole());
        model.addObject("roleId", roleId);
        model.setViewName("home/home");
        return model;
    }


    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public ModelAndView error() {
        ModelAndView model = new ModelAndView();
        model.setViewName("errors/404");
        return model;
    }
}