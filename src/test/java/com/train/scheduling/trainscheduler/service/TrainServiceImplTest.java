package com.train.scheduling.trainscheduler.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.train.scheduling.trainscheduler.entity.Station;
import com.train.scheduling.trainscheduler.entity.Train;
import com.train.scheduling.trainscheduler.repository.StationRepository;
import com.train.scheduling.trainscheduler.repository.TrainRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class TrainServiceImplTest {

	private TrainServiceImpl trainService;
	private TrainRepository trainRepository;
	private StationRepository stationRepository;

	@BeforeEach
	public void setup() {
		trainRepository = mock(TrainRepository.class);
		stationRepository = mock(StationRepository.class);
		trainService = new TrainServiceImpl(trainRepository, stationRepository);
	}

	@Test
	public void testGetTrainsBetweenStations() {
		Train train1 = new Train();
		train1.setId(1L);
		train1.setTrainName("Train 1");
		train1.setTrainNumber("123");

		Station sourceStation1 = new Station();
		sourceStation1.setId(1L);
		sourceStation1.setStationName("Mumbai");

		Station destinationStation1 = new Station();
		destinationStation1.setId(2L);
		destinationStation1.setStationName("Delhi");

		train1.setStations(Arrays.asList(sourceStation1, destinationStation1));

		String source = "Mumbai";
		String destination = "Delhi";

		when(trainRepository.findTrainsBetweenStations(source, destination))
				.thenReturn(Collections.singletonList(train1));

		List<Train> trains = trainService.getTrainsBetweenStations(source, destination);

		assertEquals(Collections.singletonList(train1), trains);
	}

	@Test
	public void testAddTrain() {
		Train train = new Train();
		train.setTrainName("Test Train");
		train.setTrainNumber("123");
		List<Station> stations = new ArrayList<>();
		Station station1 = new Station();
		station1.setStationName("Station1");
		Station station2 = new Station();
		station2.setStationName("Station2");
		stations.add(station1);
		stations.add(station2);
		train.setStations(stations);

		when(stationRepository.findByStationName(anyString())).thenReturn(null);
		when(stationRepository.save(any())).thenReturn(station1);
		when(trainRepository.save(any())).thenReturn(train);

		Train savedTrain = trainService.addTrain(train);

		assertEquals(train, savedTrain);
	}

	@Test
	public void testUpdateTrain() {
		Long trainId = 1L;
		Train train = new Train();
		train.setTrainName("Updated Train");
		train.setTrainNumber("456");
		List<Station> stations = new ArrayList<>();
		Station station1 = new Station();
		station1.setId(1L);
		station1.setStationName("Updated Station 1");
		Station station2 = new Station();
		station2.setId(2L);
		station2.setStationName("Updated Station 2");
		stations.add(station1);
		stations.add(station2);
		train.setStations(stations);

		Train existingTrain = new Train();
		existingTrain.setId(trainId);
		existingTrain.setTrainName("Existing Train");
		existingTrain.setTrainNumber("123");
		List<Station> existingStations = new ArrayList<>();
		Station existingStation1 = new Station();
		existingStation1.setId(1L);
		existingStation1.setStationName("Station 1");
		Station existingStation2 = new Station();
		existingStation2.setId(2L);
		existingStation2.setStationName("Station 2");
		existingStations.add(existingStation1);
		existingStations.add(existingStation2);
		existingTrain.setStations(existingStations);

		when(trainRepository.findById(trainId)).thenReturn(Optional.of(existingTrain));
		when(stationRepository.findById(1L)).thenReturn(Optional.of(existingStation1));
		when(stationRepository.findById(2L)).thenReturn(Optional.of(existingStation2));
		when(trainRepository.save(any())).thenReturn(train);

		Train updatedTrain = trainService.updateTrain(trainId, train);

		assertEquals(train.getTrainName(), updatedTrain.getTrainName());
		assertEquals(train.getTrainNumber(), updatedTrain.getTrainNumber());
		assertEquals(train.getStations(), updatedTrain.getStations());
	}

	@Test
	public void testDeleteTrain() {
		String trainNumber = "123";
		Train train = new Train();
		train.setTrainName("Test Train");
		train.setTrainNumber(trainNumber);

		when(trainRepository.findByTrainNumber(trainNumber)).thenReturn(train);

		assertDoesNotThrow(() -> trainService.deleteTrain(trainNumber));

		verify(trainRepository, times(1)).delete(train);
	}

	@Test
	public void testAllTrains() {
		Train train1 = new Train();
		train1.setId(1L);
		train1.setTrainName("Train 1");
		train1.setTrainNumber("123");

		Train train2 = new Train();
		train2.setId(2L);
		train2.setTrainName("Train 2");
		train2.setTrainNumber("456");

		List<Train> expectedTrains = Arrays.asList(train1, train2);

		when(trainRepository.findAll()).thenReturn(expectedTrains);

		List<Train> trains = trainService.allTrains();

		assertEquals(expectedTrains, trains);
	}

}
