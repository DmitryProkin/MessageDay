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
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

@Controller
public class IndexController {

    private final UserService userService;
    private final MessagesRepository messagesRepository;
    private final MessageController messageController;


    public IndexController(UserService userService, MessagesRepository messagesRepository, MessageController messageController) {

        this.userService = userService;
        this.messagesRepository = messagesRepository;
        this.messageController = messageController;

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
    private void everyDayTask(){
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 2);
        today.set(Calendar.MINUTE, 31);
        today.set(Calendar.SECOND, 0);

// every night at 2am you run your task
        Timer timer = new Timer();
        timer.schedule(everyNight(),TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
//        timer.schedule( , today.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)); // period: 1 day
    }

    public TimerTask everyNight(){
        messageController.createRundom();
        return null;
    }

    @RequestMapping(value = "/home/home", method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userService.findUserByLogin(auth.getName());
        Integer roleId = userService.getAuthenticationUser().getRoleId();
//        java.util.Date uDate = new java.util.Date();

        java.sql.Date currentDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        MessagesEntity messagesEntity = messagesRepository.findAllByDateCreateEquals(currentDate);
        model.addObject("message",messagesEntity);
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

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public ModelAndView error() {
        ModelAndView model = new ModelAndView();
        model.setViewName("errors/404");
        return model;
    }
}