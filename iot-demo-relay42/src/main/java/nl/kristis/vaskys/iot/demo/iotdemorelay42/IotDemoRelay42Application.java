package nl.kristis.vaskys.iot.demo.iotdemorelay42;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@ComponentScan
@SpringBootApplication
public class IotDemoRelay42Application {

	public static void main(String[] args) {
		SpringApplication.run(IotDemoRelay42Application.class, args);
	}
}
