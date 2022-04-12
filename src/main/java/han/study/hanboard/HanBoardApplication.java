package han.study.hanboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HanBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(HanBoardApplication.class, args);
    }

}
