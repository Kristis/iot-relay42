package nl.kristis.vaskys.iot.demo.iotdemorelay42dataloader.config;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "influx.db")
public class InfluxDbProperties {

    @NonNull
    private String host;

    @NonNull
    private String username;

    @NonNull
    private String password;

    @NonNull
    private String defaultDatabase;

}
