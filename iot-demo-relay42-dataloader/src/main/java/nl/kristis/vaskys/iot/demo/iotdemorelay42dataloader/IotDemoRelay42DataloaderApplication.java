package nl.kristis.vaskys.iot.demo.iotdemorelay42dataloader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class IotDemoRelay42DataloaderApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(IotDemoRelay42DataloaderApplication.class, args);
    }

    @Override
    public void run(String... args) {
        log.info("IoT-Dataloader started!");
    }
}
