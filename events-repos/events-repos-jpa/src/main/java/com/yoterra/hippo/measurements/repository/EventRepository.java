package com.yoterra.hippo.measurements.repository;

import com.yoterra.hippo.measurements.entities.EventScreenMeasurement;

import java.util.List;

public interface EventRepository<T> {

  public List<EventScreenMeasurement> findAll();

  public List<EventScreenMeasurement> findByVersion(String version);
  void save(T screen);

}