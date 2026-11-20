package com.yoterra.hippo.testdata;

import com.yoterra.hippo.measurements.load.DeviceInformation;
import com.yoterra.hippo.measurements.load.EventScreenEntity;
import com.yoterra.hippo.measurements.load.NavigationParams;
import com.yoterra.hippo.measurements.load.ScreenInformation;

/** Test data shared by the screen-event tests. */
public final class ScreenEvents {

    private ScreenEvents() {}

    /** A screen visit of 3624 ms on an iPhone, for project "ritual-project", app version 1.0.29, user "user-1". */
    public static EventScreenEntity sampleEvent() {
        DeviceInformation device = new DeviceInformation(1665146095463L, "iPhone 13", "Apple", "iOS", "15.5");
        ScreenInformation screen = new ScreenInformation("CollectionsList", "LIST", "COLLECTIONS");
        NavigationParams nav = new NavigationParams("deal-42", "DEAL");
        return new EventScreenEntity("evt-1", "test-1", "1.0.0", "1.0.29", device, "VISITED",
                "ritual-project", "RitualApp", "Home", screen, nav,
                1665146095463L, 1665146099087L, 1665146095463L, 3624, "session-1", "user-1");
    }
}
