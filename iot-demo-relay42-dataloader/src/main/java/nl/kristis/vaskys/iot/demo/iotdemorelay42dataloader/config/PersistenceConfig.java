package nl.kristis.vaskys.iot.demo.iotdemorelay42dataloader.config;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
public class PersistenceConfig {

    @Bean
    public InfluxDbProperties influxDbProperties() {
        return new InfluxDbProperties();
    }

    @Bean
    public InfluxDB influxDB(InfluxDbProperties properties) {
        InfluxDB influxDb = InfluxDBFactory.connect(properties.getHost(), properties.getUsername(), properties.getPassword());
        influxDb.enableGzip();
        influxDb.setDatabase(properties.getDefaultDatabase());
        return influxDb;
    }
}
