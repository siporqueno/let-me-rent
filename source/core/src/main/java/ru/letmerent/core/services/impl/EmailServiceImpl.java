package ru.letmerent.core.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.letmerent.core.entity.Order;
import ru.letmerent.core.entity.OrderItem;
import ru.letmerent.core.services.EmailService;

import javax.annotation.PostConstruct;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {
    @Value("${spring.mail.host}")
    private String mailHost;
    @Value("${spring.mail.user}")
    private String user;
    @Value("${spring.mail.password}")
    private String password;
    @Value("${spring.mail.port}")
    private int port;
    
    private Transport transport;
    private Session session;
    
    @PostConstruct
    private void initProps() throws MessagingException {
        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", mailHost);
        props.put("mail.smtp.user", user);
        props.put("mail.smtp.password", password);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", true);
        
        session = Session.getInstance(props, null);
        transport = session.getTransport("smtp");
        transport.connect(mailHost, user, password);
        
    }
    @Override
    public void sendNotification(Order order){
        sendRecipientMessage(order);
       sendRenterMessage(order);
    }
    
    private void sendRecipientMessage(Order order) {
        String body = createRecipientMessage(order);
        sendMessage(order.getUser().getEmail(), "Заказ инструмента", body);
    }
    
    private void sendRenterMessage(Order order) {
        order.getOrderItems().forEach(oi -> {
            String to = oi.getInstrument().getUser().getEmail();
            String body = String.format("У вас арендовали инструмент %s c %s по %s на сумму: %s р.", oi.getInstrument().getTitle(),
                oi.getStartDate(), oi.getEndDate(), oi.getRentPrice().toString());
            sendMessage(to, "Аренда инструмента", body);
        });
    }
    
    private void sendMessage(String to, String subject, String body) {
        try {
            MimeMessage message = new MimeMessage(session);
            InternetAddress from = new InternetAddress(user);
            message.setSubject(subject, String.valueOf(StandardCharsets.UTF_8));
            message.setFrom(from);
            message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            
            Multipart multipart = new MimeMultipart("alternative");
            BodyPart messageBodyPart = new MimeBodyPart();
            
            messageBodyPart.setText(body);
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            
            transport.sendMessage(message, message.getAllRecipients());
        } catch (MessagingException e) {
            log.error("Can't send email {}", e.getMessage());
        }
    }
    
    private String createRecipientMessage(Order order) {
        return String.format("Вы только что оформили заказ №:%s, аренда от %s, до %s.%sСписок инструментов:%s%s",
            order.getId(), order.getStartDate(), order.getEndDate(), System.lineSeparator(), System.lineSeparator(), getOrderItemList(order));
    }
    
    private String getOrderItemList(Order order) {
        List<OrderItem> orderItems = new ArrayList<>(order.getOrderItems());
        StringBuilder items = new StringBuilder();
        for (OrderItem orderItem : orderItems) {
            items.append(String.format("%s c %s  до %s ", orderItem.getInstrument().getTitle(), orderItem.getStartDate(), orderItem.getEndDate()));
            items.append(System.lineSeparator());
        }
        
        return items.toString();
    }
}
