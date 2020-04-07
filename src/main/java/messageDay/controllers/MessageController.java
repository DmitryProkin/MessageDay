package messageDay.controllers;

import messageDay.entities.MessagesEntity;
import messageDay.entities.UserEntity;
import messageDay.repository.MessagesRepository;
import messageDay.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/message")
public class MessageController {
    private final UserService userService;
    private final MessagesRepository messagesRepository;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
//    private final IndexController indexController;
    public static Integer counter ;
    public static Integer size ;
    public List<MessagesEntity> messagesList;

    public MessageController(UserService userService, MessagesRepository messagesRepository){
        this.userService = userService;
        this.messagesRepository = messagesRepository;
//        this.indexController = indexController;

    }

    public List<MessagesEntity> init(){
        java.sql.Date currentDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        messagesList = messagesRepository.findAllByDateCreateIsLessThanEqual(currentDate);
        counter = messagesList.size();
        counter--;
        size = messagesList.size();
        return messagesList;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        Integer roleId = userService.getAuthenticationUser().getRoleId();
//        indexController.init();
        ModelAndView model = new ModelAndView();
        if (roleId == 1) {
            Iterable<MessagesEntity> listMessages = messagesRepository.findAll();
            model.addObject("messageList", listMessages);
            model.addObject("roleId", roleId);
            model.setViewName("message/index");
        } else if (roleId == 2) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//            MessagesEntity messagesEntity = messagesRepository.findAllByDateCreateEquals(timestamp);
            List<MessagesEntity> listMessages = new ArrayList<>();
//            listMessages.add(messagesEntity);
            model.addObject("messageList", listMessages);
            model.addObject("roleId", roleId);
            model.setViewName("message/index");
        }
        return model;
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView create() {
        int flag =0;
        Integer roleId = userService.getAuthenticationUser().getRoleId();
        Integer userId = userService.getAuthenticationUser().getId();
        MessagesEntity messagesEntity = new MessagesEntity();

        ModelAndView model = new ModelAndView();
        model.addObject("message", messagesEntity);
        model.addObject("messageList", messagesRepository.findAll());
        model.addObject("roleId", roleId);
        model.addObject("userId",userId);
        model.addObject("flag",flag);
        model.setViewName("message/form");
        return model;
    }

    @RequestMapping(value = "/newRundom", method = RequestMethod.GET)
    public ModelAndView createRundom() {
        int flag =0;
        Integer roleId = userService.getAuthenticationUser().getRoleId();
        Integer userId = userService.getAuthenticationUser().getId();
        MessagesEntity messagesEntity = new MessagesEntity();

        ModelAndView model = new ModelAndView();
        List<MessagesEntity> messageList = messagesRepository.findAll();
        int size = messageList.size();
        int rundomIndex = (int) (Math.random() * size);
        messagesEntity.setText(messageList.get(rundomIndex).getText());

        java.sql.Date nows = new java.sql.Date( new java.util.Date().getTime() );
        java.sql.Date tomorrow= new java.sql.Date( nows.getTime() + 24*60*60*1000);

        messagesEntity.setDateCreate(tomorrow);
        messagesEntity.setAutor_id(userId);
        model.addObject("message", messagesEntity);
        model.addObject("roleId", roleId);
        model.addObject("flag",flag);
        model = save(messagesEntity);
//        model.setViewName("redirect:/message/");
        return model;
    }

    @RequestMapping(value = "/selectExist", method = RequestMethod.GET)
    public ModelAndView createSelect() {
        int flag =1;
        Integer roleId = userService.getAuthenticationUser().getRoleId();
        Integer userId = userService.getAuthenticationUser().getId();
        MessagesEntity messagesEntity = new MessagesEntity();

        ModelAndView model = new ModelAndView();
        List<MessagesEntity> messageList = messagesRepository.findAll();


        java.sql.Date nows = new java.sql.Date( new java.util.Date().getTime() );
        java.sql.Date tomorrow= new java.sql.Date( nows.getTime() + 24*60*60*1000);

        messagesEntity.setDateCreate(tomorrow);

        model.addObject("roleId", roleId);
        model.addObject("flag",flag);
        model.addObject("message", messagesEntity);
        model.addObject("messageList",messageList);
        model.setViewName("message/form");
        return model;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ModelAndView save(@Valid @ModelAttribute("MessageEmtity") MessagesEntity messagesEntity) {

        ModelAndView model = new ModelAndView();
        Integer roleId = userService.getAuthenticationUser().getRoleId();
        messagesEntity.setAutor_id(userService.getAuthenticationUser().getId());
//        messagesEntity.setId(null);
//        if (result.hasErrors()) {
//
//            model.addObject("message", messagesEntity);
//            model.addObject("messageList", messagesRepository.findAll());
//            model.addObject("roleId", roleId);
//            model.setViewName("message/form");
//            return model;
//        }

        MessagesEntity messagesEntityOriginal =null;
        messagesEntityOriginal = messagesRepository.findAllByDateCreateEquals(messagesEntity.getDateCreate());


        if(messagesEntityOriginal==null)
        {
            messagesRepository.save(messagesEntity);
        }
        else{
            messagesEntityOriginal.setText(messagesEntity.getText());
            messagesRepository.save(messagesEntityOriginal);
        }
        model.setViewName("redirect:/message/");
        return model;
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public ModelAndView update(@PathVariable int id) {
        int flag =0;
        ModelAndView model = new ModelAndView();
        Integer roleId = userService.getAuthenticationUser().getRoleId();

        Optional<MessagesEntity> optOrder = messagesRepository.findById(id);
        MessagesEntity messagesEntity = optOrder.get();
        if (optOrder.isPresent()) {

            model.addObject("message", messagesEntity);
            model.addObject("roleId", roleId);
            model.addObject("flag",flag);
            model.setViewName("message/form");

        } else {
            model.setViewName("redirect:/message/");
        }

        return model;
    }


    @RequestMapping(value = "/{id}/delete")
    public ModelAndView delete(@PathVariable int id) {
        ModelAndView model = new ModelAndView();
        Integer roleId = userService.getAuthenticationUser().getRoleId();
        MessagesEntity orderEntity = messagesRepository.findById(id).get();
        if (roleId == 1) {
            messagesRepository.deleteById(id);
            model.setViewName("redirect:/message/");
        } else {
            model.setViewName("redirect:/message/");
        }
        return model;
    }
}
