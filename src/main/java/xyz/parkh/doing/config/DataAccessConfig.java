//// java 로 DB 연결 설정 했지만
//// Test 에서 application.properties 값이 안 가져와짐
//// 상황에 맞는 해결 방법 못 찾아 xml 사용
//// Spring Boot Test 이용하는 방법은 찾음
//// https://stackoverflow.com/questions/39368640/springboot-value-is-not-working-during-junit-test-for-custom-spring-config-lo
//
//package xyz.parkh.doing.config;
//
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class DataAccessConfig {
//
//    @Value("${db.url}")
//    private String jdbcUrl;
//
//    @Value("${db.username}")
//    private String username;
//
//    @Value("${db.password}")
//    private String password;
//
//    @Bean
//    public DataSource dataSource() {
//        HikariConfig hikariConfig = new HikariConfig();
//        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        hikariConfig.setJdbcUrl(jdbcUrl);
//        hikariConfig.setUsername(username);
//        hikariConfig.setPassword(password);
//
//        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
//        return dataSource;
//    }
//
//    @Bean
//    SqlSessionFactoryBean sqlSessionFactory() {
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(dataSource());
//
//        return sqlSessionFactoryBean;
//    }
//
//}
