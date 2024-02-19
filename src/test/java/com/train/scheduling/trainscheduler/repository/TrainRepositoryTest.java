package com.train.scheduling.trainscheduler.repository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.train.scheduling.trainscheduler.entity.Station;
import com.train.scheduling.trainscheduler.entity.Train;

@SpringBootTest
public class TrainRepositoryTest {

	private TrainRepository trainRepository;

	@BeforeEach
	public void setup() {
		trainRepository = mock(TrainRepository.class);
	}

	@Test
	public void testFindTrainsBetweenStations() {
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

		Train train2 = new Train();
		train2.setId(2L);
		train2.setTrainName("Train 2");
		train2.setTrainNumber("456");

		Station sourceStation2 = new Station();
		sourceStation2.setId(3L);
		sourceStation2.setStationName("Chennai");

		Station destinationStation2 = new Station();
		destinationStation2.setId(4L);
		destinationStation2.setStationName("Kolkata");

		train2.setStations(Arrays.asList(sourceStation2, destinationStation2));

		List<Train> expectedTrains = Arrays.asList(train1, train2);

		String source = "Mumbai";
		String destination = "Delhi";

		when(trainRepository.findTrainsBetweenStations(source, destination)).thenReturn(expectedTrains);

		List<Train> trains = trainRepository.findTrainsBetweenStations(source, destination);

		assertEquals(expectedTrains, trains);
	}

	@Test
	public void testFindByTrainNumber() {
		String trainNumber = "123";
		Train train = new Train();
		train.setTrainNumber(trainNumber);

		when(trainRepository.findByTrainNumber(trainNumber)).thenReturn(train);

		Train foundTrain = trainRepository.findByTrainNumber(trainNumber);

		assertEquals(train, foundTrain);
	}

	@Test
	public void testDeleteByTrainNumber() {
		String trainNumber = "123";

		assertDoesNotThrow(() -> trainRepository.deleteByTrainNumber(trainNumber));
	}
}
