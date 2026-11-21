package com.yoterra.hippo.measurements.repository;

import com.yoterra.hippo.measurements.entities.EventScreenMeasurement;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EventScreenRepositoryImplTest {

    @Mock
    InfluxDB influxDB;

    @InjectMocks
    EventScreenRepositoryImpl repository;

    private static EventScreenMeasurement measurement() {
        EventScreenMeasurement m = new EventScreenMeasurement();
        m.setEventId("evt-1");
        m.setTestId("test-1");
        m.setApiVersion("1.0.0");
        m.setAppVersion("1.0.29");
        m.setAppInstalledTime(1665146095463L);
        m.setDeviceModel("iPhone 13");
        m.setManufacturer("Apple");
        m.setSystemName("iOS");
        m.setSystemVersion("15.5");
        m.setEventType("VISITED");
        m.setProjectId("ritual-project");
        m.setProjectName("RitualApp");
        m.setPreviousScreenName("Home");
        m.setScreenName("CollectionsList");
        m.setScreenSubtype("LIST");
        m.setScreenType("COLLECTIONS");
        m.setNavParamId("deal-42");
        m.setNavParamType("DEAL");
        m.setScreenStartTime(1665146095463L);
        m.setScreenEndTime(1665146099087L);
        m.setTimestamp(1665146095463L);
        m.setTotalScreenTime(3624);
        m.setSessionId("session-1");
        m.setUserId("user-1");
        return m;
    }

    private String savedLine() {
        ArgumentCaptor<Point> point = ArgumentCaptor.forClass(Point.class);
        verify(influxDB).write(point.capture());
        return point.getValue().lineProtocol();
    }

    @Test
    void writesScreenTimeAsFields() {
        repository.save(measurement(), 11L, 22L, 33L);

        assertThat(savedLine())
                .startsWith("events_screens,")
                .contains("totalScreenTime=3624i")
                .contains("screenStartTime=1665146095463i")
                .contains("screenEndTime=1665146099087i");
    }

    @Test
    void writesScreenTypeAndSubtypeTagsSeparately() {
        repository.save(measurement(), 11L, 22L, 33L);

        assertThat(savedLine())
                .contains("screenSubtype=LIST")
                .contains("screenType=COLLECTIONS");
    }

    @Test
    void writesSessionUserAndHashedIds() {
        repository.save(measurement(), 11L, 22L, 33L);

        assertThat(savedLine())
                .contains("sessionId=session-1")
                .contains("userId=user-1")
                .contains("projectIdHash=\"11\"")
                .contains("cohortIdHash=\"22\"")
                .contains("userIdHash=\"33\"");
    }
}
