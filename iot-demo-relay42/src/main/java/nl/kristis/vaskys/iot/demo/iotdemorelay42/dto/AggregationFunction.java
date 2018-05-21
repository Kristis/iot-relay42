package nl.kristis.vaskys.iot.demo.iotdemorelay42.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum AggregationFunction {
    MEAN("mean"),
    MAX("max"),
    MIN("min");

    private final String functionName;
}
