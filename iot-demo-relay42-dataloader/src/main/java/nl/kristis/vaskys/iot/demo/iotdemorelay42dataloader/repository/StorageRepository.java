package nl.kristis.vaskys.iot.demo.iotdemorelay42dataloader.repository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.springframework.stereotype.Repository;

/**
 * TODO: Implement batch storing.
 *
 * @see <a href="https://github.com/influxdata/influxdb-java">InfluxDb Java Client</a>
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class StorageRepository {

    @NonNull
    private final InfluxDB influxDB;

    /**
     * Store point to InfluxDb
     *
     * @param point
     */
    public void save(Point point) {
        if (point == null) {
            log.warn("Can not store empty point!");
            return;
        }
        log.debug("save({})", point);
        influxDB.write(point);
        influxDB.close();
    }
}
