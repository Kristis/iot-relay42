package nl.kristis.vaskys.iot.demo.iotdemorelay42.dto;

import lombok.*;


/**
 * TODO: Extend this object with Period field for aggregation purpose. Also, implement proper zonedDatetime.
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class FilterDTO {

    @NonNull
    private Long startDate;

    @NonNull
    private Long endDate;

    @NonNull
    private String measurementName;

    private String tagName;

    private String tagValue;

}
