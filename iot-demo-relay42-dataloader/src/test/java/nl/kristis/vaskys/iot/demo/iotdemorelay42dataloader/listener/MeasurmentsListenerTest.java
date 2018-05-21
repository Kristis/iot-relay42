package nl.kristis.vaskys.iot.demo.iotdemorelay42dataloader.listener;

import nl.kristis.vaskys.iot.demo.iotdemorelay42dataloader.converter.PointConverter;
import nl.kristis.vaskys.iot.demo.iotdemorelay42dataloader.repository.StorageRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.influxdb.dto.Point;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class MeasurmentsListenerTest {

    @Mock
    private StorageRepository storageRepository;

    @Mock
    private PointConverter pointConverter;

    @Mock
    private ConsumerRecord<String, String> message;

    @Mock
    private Point mockPoint;

    private MeasurementsListener listener;


    @Before
    public void setUp() {
        listener = new MeasurementsListener(pointConverter, storageRepository);
    }

    @Test
    public void shouldHandleMeasurement() throws Exception {
        when(message.value()).thenReturn("{data: data}");
        when(pointConverter.convert(any())).thenReturn(Optional.of(mockPoint));
        doNothing().when(storageRepository).save(mockPoint);
        listener.listen(message);
    }

    @Test(expected = Exception.class)
    public void shouldThrowException() throws Exception {
        when(message.value()).thenReturn("{data: data}");
        when(pointConverter.convert(any())).thenReturn(Optional.empty());
        listener.listen(message);
    }


}
