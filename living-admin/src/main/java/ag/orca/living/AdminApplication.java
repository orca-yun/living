package ag.orca.living;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication
public class AdminApplication {
    public static void main(String[] args) {

        SpringApplication.run(AdminApplication.class, args);
    }
}
