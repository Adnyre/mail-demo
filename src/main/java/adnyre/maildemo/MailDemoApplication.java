package adnyre.maildemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@EnableWebSecurity
@EnableAsync
public class MailDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(MailDemoApplication.class, args);
    }
}
