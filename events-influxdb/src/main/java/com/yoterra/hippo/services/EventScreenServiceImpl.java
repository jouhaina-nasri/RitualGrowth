package com.yoterra.hippo.services;


import com.yoterra.hippo.exception.InvalidEventArguments;
import com.yoterra.hippo.mapper.EventScreenMapper;
import com.yoterra.hippo.measurements.entities.DeviceMeasurement;
import com.yoterra.hippo.measurements.entities.EventScreenMeasurement;
import com.yoterra.hippo.measurements.entities.ScreenMeasurement;
import com.yoterra.hippo.measurements.load.EventScreenEntity;
import com.yoterra.hippo.measurements.repository.EventScreenRepositoryImpl;
import com.yoterra.hippo.measurements.requests.form.EventRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.yoterra.hippo.services.HashService.hash;

@Service
public class EventScreenServiceImpl implements EventScreenService {

    @Autowired
    EventScreenRepositoryImpl influxdbScreensRepository;

    @Autowired
    public EventScreenMapper mapper;

    @Override
    public void saveScreenEvent(EventScreenEntity entity) throws InvalidEventArguments {
            try {
                entity.setEventId(""+new Date().getTime());
                String projectId = entity.getProjectId();
                String userId = entity.getUserId();
                String appVersion = entity.getAppVersion();

                Long project_id = hash(projectId);
                Long user_id = hash(userId);
                Long cohort_id = hash(appVersion);

                EventScreenMeasurement screen = mapper.toMeasurement(entity);

                influxdbScreensRepository.save(screen, project_id,cohort_id,user_id);
            }catch (Exception e){
                e.printStackTrace();
                throw new InvalidEventArguments("Invalid event arguments. Please, check the given data.");
            }
    }

    @Override
    public List<ScreenMeasurement> findAllScreensEvents(EventRequest request) {
        return influxdbScreensRepository.findAll(request);
    }

    @Override
    public List<DeviceMeasurement> findAllDeviceEntity(String userId) {
        return influxdbScreensRepository.findAllDevices(userId);
    }

    @Override
    public void deleteAllEvents() {
        influxdbScreensRepository.deleteAllEvents();
    }
}
