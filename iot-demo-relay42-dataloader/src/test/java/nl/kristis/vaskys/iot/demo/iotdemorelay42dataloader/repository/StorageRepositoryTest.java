package nl.kristis.vaskys.iot.demo.iotdemorelay42dataloader.repository;


import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
public class StorageRepositoryTest {

    @Mock
    private InfluxDB influxDB;

    private StorageRepository storageRepository;

    @Before
    public void setUp() {
        storageRepository = new StorageRepository(influxDB);
    }

    @Test
    public void shouldStorePoint() {
        Point point = Point.measurement("test").addField("main_temp", 1).build();
        storageRepository.save(point);
        Mockito.verify(influxDB, times(1)).write(point);
    }

    @Test
    public void shouldSkipSave() {
        storageRepository.save(null);
        Mockito.verify(influxDB, times(0)).write("");
    }

}
