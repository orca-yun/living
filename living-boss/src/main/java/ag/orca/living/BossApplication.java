package ag.orca.living;


import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableDubbo
@EnableAsync
@EnableTransactionManagement
@SpringBootApplication
public class BossApplication {

    public static void main(String[] args) {
        SpringApplication.run(BossApplication.class, args);
    }
}
