package com.yoterra.hippo.services;

import com.yoterra.hippo.exception.InvalidEventArguments;
import com.yoterra.hippo.mapper.EventScreenMapper;
import com.yoterra.hippo.mapper.EventScreenMapperTest;
import com.yoterra.hippo.measurements.entities.EventScreenMeasurement;
import com.yoterra.hippo.measurements.load.EventScreenEntity;
import com.yoterra.hippo.measurements.repository.EventScreenRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.yoterra.hippo.services.HashService.hash;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EventScreenServiceImplTest {

    @Mock
    EventScreenRepositoryImpl repository;

    @Spy
    EventScreenMapper mapper = new EventScreenMapper();

    @InjectMocks
    EventScreenServiceImpl service;

    @Test
    void savesMeasurementWithHashedProjectCohortAndUserIds() throws Exception {
        EventScreenEntity event = EventScreenMapperTest.sampleEvent();

        service.saveScreenEvent(event);

        ArgumentCaptor<EventScreenMeasurement> saved = ArgumentCaptor.forClass(EventScreenMeasurement.class);
        verify(repository).save(saved.capture(),
                eq(hash("ritual-project")), eq(hash("1.0.29")), eq(hash("user-1")));
        assertEquals(3624, saved.getValue().getTotalScreenTime());
        assertEquals("CollectionsList", saved.getValue().getScreenName());
    }

    @Test
    void assignsANewEventId() throws Exception {
        EventScreenEntity event = EventScreenMapperTest.sampleEvent();
        event.setEventId(null);

        service.saveScreenEvent(event);

        assertNotNull(event.getEventId());
    }

    @Test
    void rejectsEventWithoutDeviceInformation() {
        EventScreenEntity event = EventScreenMapperTest.sampleEvent();
        event.setDeviceInformation(null);

        assertThrows(InvalidEventArguments.class, () -> service.saveScreenEvent(event));
        verify(repository, never()).save(any(), anyLong(), anyLong(), anyLong());
    }

    @Test
    void wrapsRepositoryFailures() {
        doThrow(new IllegalStateException("influx down"))
                .when(repository).save(any(), anyLong(), anyLong(), anyLong());

        assertThrows(InvalidEventArguments.class,
                () -> service.saveScreenEvent(EventScreenMapperTest.sampleEvent()));
    }
}
