package nl.kristis.vaskys.iot.demo.iotdemorelay42.web;

import nl.kristis.vaskys.iot.demo.iotdemorelay42.dto.FilterDTO;
import nl.kristis.vaskys.iot.demo.iotdemorelay42.repository.DataRepository;
import org.influxdb.dto.QueryResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class AggregatedDataControllerTest {

    @Mock
    private DataRepository dataRepository;

    private AggregatedDataController controller;

    private FilterDTO filter;

    @Before
    public void setUp() {
        filter = FilterDTO.builder().startDate(1l).endDate(2l).tagName("name").tagValue("test").measurementName("test_weather").build();
        controller = new AggregatedDataController(dataRepository);
        when(dataRepository.getDataByFilter(eq(filter), any())).thenReturn(Collections.emptyList());
        when(dataRepository.getAllSeries()).thenReturn(Collections.emptyList());
    }


    @Test
    public void shouldCallMaxEndpoint() {
        List<QueryResult.Result> result = controller.findMaxTemp(filter);
        assertNotNull(result);
    }

    @Test
    public void shouldCallMinEndpoint() {
        List<QueryResult.Result> result = controller.findMinTemp(filter);
        assertNotNull(result);
    }

    @Test
    public void shouldCallAvgEndpoint() {
        List<QueryResult.Result> result = controller.findAverageTemp(filter);
        assertNotNull(result);
    }

    @Test
    public void shouldCallSeriesEndpoint() {
        List<QueryResult.Result> result = controller.findAllSeries();
        assertNotNull(result);
    }


}
