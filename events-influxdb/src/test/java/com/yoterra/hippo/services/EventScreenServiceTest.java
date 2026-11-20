package com.yoterra.hippo.services;

import com.yoterra.hippo.exception.InvalidEventArguments;
import com.yoterra.hippo.measurements.entities.EventScreenMeasurement;
import com.yoterra.hippo.measurements.entities.ScreenMeasurement;
import com.yoterra.hippo.measurements.load.DeviceInformation;
import com.yoterra.hippo.measurements.load.EventScreenEntity;
import com.yoterra.hippo.measurements.load.NavigationParams;
import com.yoterra.hippo.measurements.load.ScreenInformation;
import com.yoterra.hippo.measurements.repository.EventScreenRepository;
import com.yoterra.hippo.measurements.requests.form.EventRequest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Tag("integration")
@SpringBootTest
public class EventScreenServiceTest {

    @Autowired
    EventScreenRepository<EventScreenMeasurement> eventScreenRepository;

    @Autowired
    EventScreenService eventScreenService;
    EventScreenMeasurement screen = new EventScreenMeasurement();


    @BeforeEach
    void setUp() {
         this.screen = new EventScreenMeasurement("1","1","1.0.0","1.0.27",1665146095463L,"iPhone 13"
                ,"Apple","iOS","15.5","VISITED","Hippo shopping","HippoApp","Home-AuMzYCVKCVpBGJ0PKgAOz","CollectionsList",
                "LIST","COLLECTIONS","DEAL","8gmn5vIK6ADGlrE73idx6Q",1665146095463L,1665146099087L,1665146095463L,3624,
                "C87E697A-032E-4570-A239-8B3952848A951665055688669","C87E697A-032E-4570-A239-8B3952848A95");
         eventScreenRepository.save(screen,1L,1L,1L);
    }

    @Test
    void saveScreenEvent() throws InvalidEventArguments {
        DeviceInformation deviceInformation = new DeviceInformation(1665146095463L,
                "iPhone 13","Apple","iOS","15.5");
        ScreenInformation screenInformation = new ScreenInformation("CollectionsList","LIST",
                "COLLECTIONS");
        NavigationParams navigationParams = new NavigationParams("8gmn5vIK6ADGlrE73idx6Q","DEAL");
        EventScreenEntity eventScreen = new EventScreenEntity("2","3","1.0.0",
                "1.0.29",deviceInformation,"VISITED","Hippo shopping",
                "HippoApp","Home-AuMzYCVKCVpBGJ0PKgAOz",screenInformation,
                navigationParams,1665146095463L,1665146099087L,1665146095463L,
                3624, "C87E697A-032E-4570-A239-8B3952848A951665055688669",
                "C87E697A-032E-45");
        eventScreenService.saveScreenEvent(eventScreen);
        assertFalse(eventScreenService.findAllDeviceEntity(eventScreen.getUserId()).isEmpty());
    }

    @Test
    void findAllScreensEvents() {
        EventRequest eventRequest = new EventRequest();
        assertFalse(eventScreenService.findAllScreensEvents(eventRequest).isEmpty());
    }

    @Test
    void findAllScreensEventsNotDuplicates(){
        EventRequest eventRequest = new EventRequest();
        List<ScreenMeasurement> screenMeasurements = eventScreenService.findAllScreensEvents(eventRequest);
        assertThat(screenMeasurements).doesNotHaveDuplicates();
    }

    @Test
    void findAllDeviceEntity() {
        assertFalse(eventScreenService.findAllDeviceEntity(screen.getUserId()).isEmpty());
    }

    @Test
    void findAllDeviceWithNotExistingId() {
        assertTrue(eventScreenService.findAllDeviceEntity("C88B3952848A95").isEmpty());
    }

    /*@Test
    void deleteAllEvents() {
        eventScreenService.deleteAllEvents();
        assertTrue(eventScreenService.findAllDeviceEntity(screen.getUserId()).isEmpty());
        EventRequest eventRequest = new EventRequest();
        assertTrue(eventScreenService.findAllScreensEvents(eventRequest).isEmpty());
    }*/


}