package nl.kristis.vaskys.iot.demo.iotdemorelay42dataloader.listener;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.kristis.vaskys.iot.demo.iotdemorelay42dataloader.converter.PointConverter;
import nl.kristis.vaskys.iot.demo.iotdemorelay42dataloader.repository.StorageRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.influxdb.dto.Point;
import org.json.JSONObject;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class MeasurementsListener {

    @NonNull
    private final PointConverter pointConverter;

    @NonNull
    private final StorageRepository storageRepository;

    @KafkaListener(topics = "measurements.stream.topic")
    public void listen(ConsumerRecord<String, String> cr) throws Exception {
        log.info("New measurement received!");
        Optional<Point> point = pointConverter.convert(new JSONObject(cr.value()));
        storageRepository.save(point.orElseThrow(() -> new Exception("Can not read measurement!")));
    }
}
