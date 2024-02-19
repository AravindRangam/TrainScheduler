package com.train.scheduling.trainscheduler.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.train.scheduling.trainscheduler.entity.Train;
import com.train.scheduling.trainscheduler.service.TrainService;

import java.util.Arrays;
import java.util.List;

public class TrainControllerTest {

    @Test
    public void testGetTrainsBetweenStations() {
        TrainService trainService = mock(TrainService.class);
        TrainController trainController = new TrainController(trainService);
        List<Train> expectedTrains = Arrays.asList(new Train(), new Train());
        when(trainService.getTrainsBetweenStations("source", "destination")).thenReturn(expectedTrains);
        ResponseEntity<List<Train>> responseEntity = trainController.getTrainsBetweenStations("source", "destination");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedTrains, responseEntity.getBody());
    }

    @Test
    public void testAddTrain() {
        TrainService trainService = mock(TrainService.class);
        TrainController trainController = new TrainController(trainService);
        Train trainToAdd = new Train();
        Train savedTrain = new Train();
        when(trainService.addTrain(trainToAdd)).thenReturn(savedTrain);
        ResponseEntity<Train> responseEntity = trainController.addTrain(trainToAdd);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(savedTrain, responseEntity.getBody());
    }

    @Test
    public void testUpdateTrain() {
        TrainService trainService = mock(TrainService.class);
        TrainController trainController = new TrainController(trainService);
        Long trainId = 1L;
        Train trainToUpdate = new Train();
        Train updatedTrain = new Train();
        when(trainService.updateTrain(trainId, trainToUpdate)).thenReturn(updatedTrain);
        ResponseEntity<Train> responseEntity = trainController.updateTrain(trainId, trainToUpdate);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedTrain, responseEntity.getBody());
    }

    @Test
    public void testDeleteTrain() {
        TrainService trainService = mock(TrainService.class);
        TrainController trainController = new TrainController(trainService);
        String trainNumber = "123";
        ResponseEntity<Void> responseEntity = trainController.deleteTrain(trainNumber);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(trainService).deleteTrain(trainNumber);
    }

    @Test
    public void testAllTrains() {
        TrainService trainService = mock(TrainService.class);
        TrainController trainController = new TrainController(trainService);
        List<Train> expectedTrains = Arrays.asList(new Train(), new Train());
        when(trainService.allTrains()).thenReturn(expectedTrains);
        ResponseEntity<List<Train>> responseEntity = trainController.allTrainss();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedTrains, responseEntity.getBody());
    }
}
