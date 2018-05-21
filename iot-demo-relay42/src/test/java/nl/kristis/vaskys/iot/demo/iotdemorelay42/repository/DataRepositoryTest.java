package nl.kristis.vaskys.iot.demo.iotdemorelay42.repository;

import nl.kristis.vaskys.iot.demo.iotdemorelay42.config.InfluxDbProperties;
import nl.kristis.vaskys.iot.demo.iotdemorelay42.dto.AggregationFunction;
import nl.kristis.vaskys.iot.demo.iotdemorelay42.dto.FilterDTO;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBException;
import org.influxdb.dto.QueryResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class DataRepositoryTest {

    @Mock
    private InfluxDB influxDB;

    @Mock
    private InfluxDbProperties properties;

    private DataRepository dataRepository;

    private FilterDTO filter;

    @Before
    public void setUp() {
        filter = FilterDTO.builder().startDate(1l).endDate(2l).tagName("name").tagValue("test").measurementName("test_weather").build();
        when(properties.getDefaultDatabase()).thenReturn("relay42");
        dataRepository = new DataRepository(properties, influxDB);
    }

    @Test
    public void shouldReturnEmptyMeasurementsResults() {
        QueryResult queryResult = new QueryResult();
        queryResult.setResults(Collections.emptyList());
        when(influxDB.query(any())).thenReturn(queryResult);
        List<QueryResult.Result> results = dataRepository.getDataByFilter(filter, AggregationFunction.MAX);
        assertNotNull(results);
        assertEquals(0, results.size());
    }

    @Test(expected = InfluxDBException.class)
    public void shouldThrowException() {
        Mockito.doThrow(new InfluxDBException("Database is down!")).when(influxDB).query(any());
        dataRepository.getDataByFilter(filter, AggregationFunction.MAX);
    }

    @Test
    public void shouldReturnEmptySeriesResults() {
        QueryResult queryResult = new QueryResult();
        queryResult.setResults(Collections.emptyList());
        when(influxDB.query(any())).thenReturn(queryResult);
        List<QueryResult.Result> series = dataRepository.getAllSeries();
        assertNotNull(series);
    }


}
