package nl.kristis.vaskys.iot.demo.iotdemorelay42.repository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.kristis.vaskys.iot.demo.iotdemorelay42.config.InfluxDbProperties;
import nl.kristis.vaskys.iot.demo.iotdemorelay42.dto.AggregationFunction;
import nl.kristis.vaskys.iot.demo.iotdemorelay42.dto.FilterDTO;
import org.influxdb.InfluxDB;
import org.influxdb.dto.BoundParameterQuery;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Repository class for communication with database. In this case database is InfluxDB, so, communication is done
 * via HTTP.
 * <p>
 * TODO: Implement error handling, custom POJO mappings for smaller objects and lower traffic.
 * TODO: Split actual query creation and query execution.
 * TODO: Map to custom POJO for more flexibility
 * TODO: implement measurement field selection, because not it is hardcoded
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class DataRepository {

    @NonNull
    private final InfluxDbProperties properties;

    @NonNull
    private final InfluxDB influxDB;

    /**
     * Filter measurements by given filter and aggregation.
     *
     * @param filter              contains period, measurement name
     * @param aggregationFunction e.g. max, min
     * @return list of results by aggregation, or empty list if no measurements found
     */
    public List<QueryResult.Result> getDataByFilter(FilterDTO filter, AggregationFunction aggregationFunction) {
        log.debug("getDataByFilter({}", filter);
        String queryStr = buildQuery(filter, aggregationFunction);
        QueryResult result = influxDB.query(new Query(queryStr, properties.getDefaultDatabase()));
        return result.getResults();
    }

    /**
     * Get all registered series for database. List could be quite long.
     *
     * @return List of series or empty list.
     */
    public List<QueryResult.Result> getAllSeries() {
        log.debug("getAllSeries()");
        BoundParameterQuery query = BoundParameterQuery.QueryBuilder
                .newQuery(String.format("SHOW SERIES ON %s", properties.getDefaultDatabase()))
                .forDatabase(properties.getDefaultDatabase()).create();
        return influxDB.query(query).getResults();
    }

    private String buildQuery(FilterDTO filter, AggregationFunction aggregation) {
        String tagClause = "";
        if (!StringUtils.isEmpty(filter.getTagName())) {
            tagClause = String.format("AND \"%s\"=\'%s\'", filter.getTagName(), filter.getTagValue());
        }
        return String.format("SELECT %s(main_temp) AS main_temp FROM %s WHERE time >= %s AND time <= %s %s", aggregation.getFunctionName(), filter.getMeasurementName(), filter.getStartDate(), filter.getEndDate(), tagClause);
    }


}
