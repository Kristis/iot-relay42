iot-relay42
=============

[![Maintainability](https://api.codeclimate.com/v1/badges/4e9d039b4358c2626ef0/maintainability)](https://codeclimate.com/github/Kristis/iot-relay42/maintainability)

This is one of possible implementations of IoT stack. Architecutre is based on [TICK](https://www.influxdata.com/time-series-platform/). In my example I am using data from [weather](https://openweathermap.org/) provider.

## Architecture

Basic architeture looks like this:

```
+--------------------+  +--------------------+    +----------+     +----------------+
| Telegraf Sentinel 1|  | Telegraf Sentinel 2|    |Sentinel 1|     | Java Sentinel 1|
+------------+-------+  +---+----------------+    +-----+----+     +--------+-------+
             |              |                           |                   |
             |              |                           |                   |
             |              |                           |                   |
             |              |                           |                   |
         +---v--------------v---------------------------v-------------------v---+
         |                           Kafka                                      |
         +---------------+-----------------------------------------+------------+
                         |                                         |
                         |                                         |
                         |                                         |
                         |                                         |
         +---------------v------+                           +------v-----------+
         | Telegraf Dataloader 1|   ...Any dataloader...    | Java Dataloader 1|
         +----------------------+                           ++-----------------+
                                |                            |
                                |                            |
                                |                            |
                                |                            |
                                |                            |
                                |                            |
                              +-v---------------------+      |
                              |      INFLUX DB        +^-----+
                              +---------^-------------+
                                        |
                                        |
                                        |
                                        |
                                        |
                                        |
                          +-------------+----------------+   (GET)    +-------------+
                          |        Measurements API      <------------+ HTTP Client |
                          +----------^--------+----------+            +-+-----------+
                                     |        |                         |
                                     |        |                         |
                                   (Authorization)                      |
                                     |        |                         |
                              +------+--------v-----+ (Authentication)  |
                              |       Keycloak      <-------------------+
                              +---------+-----------+
                                        |
                                        |
                                        |
                                 +------v--------+
                                 |  PostgresSQL  |
                                 +---------------+

```

As you can see it's possible to add as many sentinels as we want, also, dataloaders could be added any time we want.
## STACK YOU NEED
 - InfluxDb
 - Kafka
 - Keycloak
 - Java 8

## COMPONENTS
### Java datalaoder
Simple Spring boot application, which reads measurments from Kafka, and puts measurment into InfluxDb. This application expects specific json format message. For example:
```json
{
  "fields": {
    "clouds_all": 0,
    "cod": 200,
    "coord_lat": 52.09,
    "coord_lon": 5.11,
    "dt": 1525722710,
    "id": 2745912,
    "main_grnd_level": 1029.01,
    "main_humidity": 47,
    "main_pressure": 1029.01,
    "main_sea_level": 1029.99,
    "main_temp": 19.31,
    "main_temp_max": 19.31,
    "main_temp_min": 19.31,
    "sys_message": 0.0044,
    "sys_sunrise": 1525665441,
    "sys_sunset": 1525720552,
    "weather_0_id": 800,
    "wind_deg": 98.0041,
    "wind_speed": 4.91
  },
  "name": "exec_weather_collector",
  "tags": {
    "host": "localhost",
    "name": "Utrecht"
  },
  "timestamp": 1525722960
}
```
### Iot Data API
Simple API for getting aggregated data. As mentioned before, in InfluxDb we are storing weather data, so, at the moment it provdes aggregations on **main_temp** for give time period and tags. All available endpoints could be found in swagger:
![swagger doc](https://i.imgur.com/ajoaCr9.png)

You can try API by requests:
```
curl -X GET "http://localhost:8082/iot/data/v1/series/" -H "accept: application/json;charset=UTF-8" -H "Authorization: Bearer keycloak-token"

curl -X GET "http://localhost:8082/iot/data/v1/average/?startDate=1525183199&endDate=1525787999&measurementName=exec_weather_collector" -H "accept: application/json;charset=UTF-8" -H "Authorization: Bearer keycloak-token"
```

keycloak-token - keycloak access token 


## RUN
### Local
- [Start Keycloak](https://www.keycloak.org/docs/latest/getting_started/index.html)
  * Add Realm with name ```relay42```
  * Add Client with name ```sentinel-data-api```
  * Add Role ```USER```
  * Add User relay42/relay42 and assign role ```USER```
- [Start Kafka](https://kafka.apache.org/quickstart)
- Start telegraf sentinels
  * ```telegraf --config /telegraf/telegraf-sentinel-1.conf```
  * ```telegraf --config /telegraf/telegraf-sentinel-2.conf```
- Start dataloader
- Start Iot Data API

### Docker
**__ NOT FINISHED__** 
There is issue with Kafka component, dataloader can connect to it, because kafka can not elect leader. Something something ```KAFKA_ADVERTISED_HOST_NAME```

