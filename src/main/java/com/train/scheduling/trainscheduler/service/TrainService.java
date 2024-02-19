package com.train.scheduling.trainscheduler.service;

import java.util.List;

import com.train.scheduling.trainscheduler.entity.Train;

public interface TrainService {
    List<Train> getTrainsBetweenStations(String source, String destination);
    Train addTrain(Train train);
    Train updateTrain(Long id, Train train);
    void deleteTrain(String number);
    List<Train> allTrains();
}
