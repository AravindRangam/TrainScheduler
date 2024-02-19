package com.train.scheduling.trainscheduler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.train.scheduling.trainscheduler.entity.Station;
import com.train.scheduling.trainscheduler.entity.Train;
import com.train.scheduling.trainscheduler.repository.StationRepository;
import com.train.scheduling.trainscheduler.repository.TrainRepository;
import java.util.List;

@Service
public class TrainServiceImpl implements TrainService {

	private final TrainRepository trainRepository;
	private final StationRepository stationRepository;

	@Autowired
	public TrainServiceImpl(TrainRepository trainRepository, StationRepository stationRepository) {
		this.trainRepository = trainRepository;
		this.stationRepository = stationRepository;
	}

	@Override
	public List<Train> getTrainsBetweenStations(String source, String destination) {
		return trainRepository.findTrainsBetweenStations(source, destination);
	}

	@Override
	public Train addTrain(Train train) {
		// Saving stations first if they are new
		for (Station station : train.getStations()) {
			if (station.getId() == null) {
				// Checking if station with the same name already exists or not
				Station existingStation = stationRepository.findByStationName(station.getStationName());
				if (existingStation != null) {
					// If station with the same name exists, assign its ID to the new station
					station.setId(existingStation.getId());
				}
				stationRepository.save(station);
			}
		}
		return trainRepository.save(train);
	}

	@Override
	public Train updateTrain(Long id, Train train) {

		Train existingTrain = trainRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Train not found with id: " + id));

		// Updating train details
		existingTrain.setTrainName(train.getTrainName());
		existingTrain.setTrainNumber(train.getTrainNumber());

		// Managing the stations
		List<Station> updatedStations = train.getStations();

		for (Station updatedStation : updatedStations) {
			// Checking if the station is new or not

			if (updatedStation.getId() == null) {

				// Checking if a station with the same name already exists in the database or
				// not

				Station existingStation = stationRepository.findByStationName(updatedStation.getStationName());
				if (existingStation != null) {
					// If the station already exists, update its details
					// updatedStation.setId(existingStation.getId());
					continue;
				}
				// Saving new station to get its ID or update existing one
				Station savedStation = stationRepository.save(updatedStation);
				// Add saved station to the list of existing train stations
				existingTrain.getStations().add(savedStation);
			} else {
				// If station is already persisted, update its details
				Station existingStation = stationRepository.findById(updatedStation.getId()).orElseThrow(
						() -> new RuntimeException("Station not found with id: " + updatedStation.getId()));
				existingStation.setStationName(updatedStation.getStationName());
			}
		}
		return trainRepository.save(existingTrain);
	}

	@Override
	public void deleteTrain(String trainNumber) {
		Train train = trainRepository.findByTrainNumber(trainNumber);
		if (train != null) {
			trainRepository.delete(train);
		} else {
			throw new RuntimeException("Train with number " + trainNumber + " not found");
		}
	}

	@Override
	public List<Train> allTrains() {

		return trainRepository.findAll();
	}

}
