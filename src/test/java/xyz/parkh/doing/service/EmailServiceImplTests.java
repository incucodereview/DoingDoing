package xyz.parkh.doing.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import xyz.parkh.doing.service.email.EmailService;

@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml",
        "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class EmailServiceImplTests {

    @Autowired
    EmailService emailService;

    @Test
    public void startTest() {
        System.out.println("emailService = " + emailService);
        Assertions.assertTrue(true);
    }

    @Test
    public void emailSend() {
        System.out.println("emailService = " + emailService);

        String email = "hyeonj1120@naver.com";
        emailService.sendSimpleMessage(email, "subject", "text");

    }
}
