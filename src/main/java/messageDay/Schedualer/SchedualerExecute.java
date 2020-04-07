package messageDay.Schedualer;
import messageDay.controllers.MessageController;
import messageDay.entities.MessagesEntity;
import messageDay.repository.MessagesRepository;
import messageDay.service.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

@Component
public class SchedualerExecute {

    private final MessagesRepository messagesRepository;
    private final MessageController messageController;
    private final UserService userService;

    public SchedualerExecute(UserService userService, MessagesRepository messagesRepository, MessageController messageController) {

        this.userService = userService;
        this.messagesRepository = messagesRepository;
        this.messageController = messageController;

    }
//    Выполнять метод каждый день в 12 часов вечера
    @Scheduled(cron = "0 0 0 * * ?")
    public ModelAndView reportCurrentData() {
        System.out.println("Scheduler working: " + new Date());
        ModelAndView model = new ModelAndView();
        Integer roleId = 3;
        Integer userId =74;

        MessagesEntity messagesEntity = new MessagesEntity();

        java.sql.Date nows = new java.sql.Date( new java.util.Date().getTime() );
        java.sql.Date tomorrow= new java.sql.Date( nows.getTime() + 24*60*60*1000);
        messagesEntity.setDateCreate(tomorrow);

        List<MessagesEntity> messageList = messagesRepository.findAll();
        int size = messageList.size();
        int rundomIndex = (int) (Math.random() * size);
        messagesEntity.setText(messageList.get(rundomIndex).getText());

        messagesEntity.setAutor_id(userId);
        MessagesEntity messagesEntityOriginal =null;
        messagesEntityOriginal = messagesRepository.findAllByDateCreateEquals(messagesEntity.getDateCreate());

        if(messagesEntityOriginal!=null)
        {
            messagesEntityOriginal.setText(messagesEntity.getText());
            messagesRepository.save(messagesEntityOriginal);

        }
        else{
            messagesRepository.save(messagesEntity);
        }
        model.setViewName("redirect:/message/");
        return model;

    }
}
