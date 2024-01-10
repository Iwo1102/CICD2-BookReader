package ie.atu.bookreader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class BookReaderApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookReaderApplication.class, args);
    }

}
