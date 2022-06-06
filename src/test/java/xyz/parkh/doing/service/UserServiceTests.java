//package xyz.parkh.doing.service;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import xyz.parkh.doing.domain.entity.UserVo;
//
//
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml",
//        "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
//public class UserServiceTests {
//
//    @Autowired
//    UserService userService;
//
//    @Test
//    public void read() {
//        UserVo user = userService.read("park");
//        System.out.println("user = " + user);
//        Assertions.assertNotNull(user);
//    }
//
//}
