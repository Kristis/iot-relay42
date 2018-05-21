package nl.kristis.vaskys.iot.demo.iotdemorelay42dataloader.converter;

import org.influxdb.dto.Point;
import org.json.JSONObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Convert InfluxDb JSON to InfluxDB Point object. {@link Point}
 */
@Component
public class PointConverter implements Converter<JSONObject, Optional<Point>> {

    @Override
    public Optional<Point> convert(JSONObject measurement) {
        if (measurement == null || measurement.length() == 0) {
            return Optional.empty();
        }
        Map<String, String> tags = measurement.getJSONObject("tags").toMap().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> (String) e.getValue()));
        Map<String, Object> fields = measurement.getJSONObject("fields").toMap();
        return Optional.of(Point.measurement(measurement.getString("name")).tag(tags)
                .time(measurement.getLong("timestamp"), TimeUnit.SECONDS).fields(fields).build());
    }
}
