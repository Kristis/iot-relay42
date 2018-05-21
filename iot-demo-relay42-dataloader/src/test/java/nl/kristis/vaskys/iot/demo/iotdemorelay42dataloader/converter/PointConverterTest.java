package nl.kristis.vaskys.iot.demo.iotdemorelay42dataloader.converter;

import org.influxdb.dto.Point;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PointConverterTest {

    private static final String EXPECTED_RESULT = "exec_weather_collector,host=localhost,name=Utrecht clouds_all=0i,cod=200i,coord_lat=52.09,coord_lon=5.11,dt=1525722710i,id=2745912i,main_grnd_level=1029.01,main_humidity=47i,main_pressure=1029.01,main_sea_level=1029.99,main_temp=19.31,main_temp_max=19.31,main_temp_min=19.31,sys_message=0.0044,sys_sunrise=1525665441i,sys_sunset=1525720552i,weather_0_id=800i,wind_deg=98.0041,wind_speed=4.91 1525722960000000000";

    private PointConverter pointConverter = null;

    @Value("classpath:weather_example.json")
    private Resource stateFile;

    @Before
    public void setUp() {
        pointConverter = new PointConverter();
    }

    @Test
    public void shouldReturnPoint() throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(stateFile.getURI())));
        Optional<Point> pointOptional = pointConverter.convert(new JSONObject(content));
        assertEquals(true, pointOptional.isPresent());
        assertNotNull(pointOptional.get().lineProtocol());
        assertEquals(EXPECTED_RESULT, pointOptional.get().lineProtocol());
    }

    @Test
    public void shouldReturnEmptyOptional() {
        Optional<Point> point = pointConverter.convert(new JSONObject());
        assertFalse(point.isPresent());
    }


}
