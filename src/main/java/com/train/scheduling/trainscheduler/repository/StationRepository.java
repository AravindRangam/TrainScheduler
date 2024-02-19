package com.train.scheduling.trainscheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.train.scheduling.trainscheduler.entity.Station;

public interface StationRepository extends JpaRepository<Station, Long> {

	Station findByStationName(String stationName);
}
