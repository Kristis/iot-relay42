package nl.kristis.vaskys.iot.demo.iotdemorelay42.web;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.kristis.vaskys.iot.demo.iotdemorelay42.dto.AggregationFunction;
import nl.kristis.vaskys.iot.demo.iotdemorelay42.dto.FilterDTO;
import nl.kristis.vaskys.iot.demo.iotdemorelay42.repository.DataRepository;
import org.influxdb.dto.QueryResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * One of Entry points of application.
 * TODO: Add Filter as model and convert it. Reason: to decouple what is coming from rest call and what should be executed in query.
 */
@RestController
@RequestMapping(value = "/data/v1/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
@Slf4j
public class AggregatedDataController {

    @NonNull
    private final DataRepository dataRepository;

    /**
     * Return all availabale series for that particular database.
     * <B>Disclaimer:</B> List of series could be big.
     *
     * @return
     */
    @GetMapping(path = "/series/")
    public List<QueryResult.Result> findAllSeries() {
        return dataRepository.getAllSeries();
    }

    /**
     * Return average temperature for given time period.
     *
     * @param filterDTO
     * @return null - if nothing found in period, otherwise list of {@link QueryResult.Result}
     */
    @GetMapping(path = "/average/")
    public List<QueryResult.Result> findAverageTemp(@Valid @ModelAttribute FilterDTO filterDTO) {
        return dataRepository.getDataByFilter(filterDTO, AggregationFunction.MEAN);
    }

    /**
     * Return max temperature for given time period.
     *
     * @param filterDTO
     * @return null - if nothing found in period, otherwise list of {@link QueryResult.Result}
     */
    @GetMapping(path = "/max/")
    public List<QueryResult.Result> findMaxTemp(@Valid @ModelAttribute FilterDTO filterDTO) {
        return dataRepository.getDataByFilter(filterDTO, AggregationFunction.MAX);
    }

    /**
     * Return min temperature for given time period.
     *
     * @param filterDTO
     * @return null - if nothing found in period, otherwise list of {@link QueryResult.Result}
     */
    @GetMapping(path = "/min/")
    public List<QueryResult.Result> findMinTemp(@Valid @ModelAttribute FilterDTO filterDTO) {
        return dataRepository.getDataByFilter(filterDTO, AggregationFunction.MIN);
    }

}
