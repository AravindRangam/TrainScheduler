package com.train.scheduling.trainscheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.train.scheduling.trainscheduler.entity.Train;

import jakarta.transaction.Transactional;

import java.util.List;

public interface TrainRepository extends JpaRepository<Train, Long> {
	@Query("SELECT t FROM Train t JOIN t.stations s1 JOIN t.stations s2 "
			+ "WHERE s1.stationName = :source AND s2.stationName = :destination")
	List<Train> findTrainsBetweenStations(@Param("source") String source, @Param("destination") String destination);

	Train findByTrainNumber(String trainNumber);

	@Transactional
	void deleteByTrainNumber(String trainNumber);

}
