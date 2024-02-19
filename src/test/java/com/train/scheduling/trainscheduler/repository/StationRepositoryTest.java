package com.train.scheduling.trainscheduler.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.train.scheduling.trainscheduler.entity.Station;

@SpringBootTest
public class StationRepositoryTest {

	private StationRepository stationRepository;

	@BeforeEach
	public void setup() {
		stationRepository = mock(StationRepository.class);
	}

	@Test
	public void testFindByStationName() {
		String stationName = "Mumbai";
		Station station = new Station();
		station.setId(1L);
		station.setStationName(stationName);

		when(stationRepository.findByStationName(stationName)).thenReturn(station);

		Station foundStation = stationRepository.findByStationName(stationName);

		assertEquals(station, foundStation);
	}
}
