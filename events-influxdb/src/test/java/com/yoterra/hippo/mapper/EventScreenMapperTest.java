package com.yoterra.hippo.mapper;

import com.yoterra.hippo.measurements.entities.EventScreenMeasurement;
import com.yoterra.hippo.measurements.load.DeviceInformation;
import com.yoterra.hippo.measurements.load.EventScreenEntity;
import com.yoterra.hippo.measurements.load.NavigationParams;
import com.yoterra.hippo.measurements.load.ScreenInformation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventScreenMapperTest {

    private final EventScreenMapper mapper = new EventScreenMapper();

    static EventScreenEntity sampleEvent() {
        DeviceInformation device = new DeviceInformation(1665146095463L, "iPhone 13", "Apple", "iOS", "15.5");
        ScreenInformation screen = new ScreenInformation("CollectionsList", "LIST", "COLLECTIONS");
        NavigationParams nav = new NavigationParams("deal-42", "DEAL");
        return new EventScreenEntity("evt-1", "test-1", "1.0.0", "1.0.29", device, "VISITED",
                "ritual-project", "RitualApp", "Home", screen, nav,
                1665146095463L, 1665146099087L, 1665146095463L, 3624, "session-1", "user-1");
    }

    @Test
    void mapsScreenTimeAndSession() {
        EventScreenMeasurement m = mapper.toMeasurement(sampleEvent());

        assertEquals(1665146095463L, m.getScreenStartTime());
        assertEquals(1665146099087L, m.getScreenEndTime());
        assertEquals(3624, m.getTotalScreenTime());
        assertEquals("session-1", m.getSessionId());
        assertEquals("user-1", m.getUserId());
    }

    @Test
    void mapsDeviceScreenAndNavigationInformation() {
        EventScreenMeasurement m = mapper.toMeasurement(sampleEvent());

        assertEquals("iPhone 13", m.getDeviceModel());
        assertEquals("Apple", m.getManufacturer());
        assertEquals("iOS", m.getSystemName());
        assertEquals("15.5", m.getSystemVersion());
        assertEquals("CollectionsList", m.getScreenName());
        assertEquals("LIST", m.getScreenSubtype());
        assertEquals("COLLECTIONS", m.getScreenType());
        assertEquals("Home", m.getPreviousScreenName());
        assertEquals("deal-42", m.getNavParamId());
        assertEquals("DEAL", m.getNavParamType());
        assertEquals("ritual-project", m.getProjectId());
        assertEquals("1.0.29", m.getAppVersion());
    }

    @Test
    void usesPlaceholderWhenScreenInformationIsMissing() {
        EventScreenEntity event = sampleEvent();
        event.setScreenInformation(null);

        EventScreenMeasurement m = mapper.toMeasurement(event);

        assertEquals("null", m.getScreenName());
        assertEquals("null", m.getScreenSubtype());
        assertEquals("null", m.getScreenType());
    }
}
