package com.train.scheduling.trainscheduler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.train.scheduling.trainscheduler.entity.Train;
import com.train.scheduling.trainscheduler.service.TrainService;
import java.util.List;

@RestController
@RequestMapping("/trains")
public class TrainController {

	private final TrainService trainService;

	@Autowired
	public TrainController(TrainService trainService) {
		this.trainService = trainService;
	}

	@GetMapping("/{source}/{destination}")
	public ResponseEntity<List<Train>> getTrainsBetweenStations(@PathVariable String source,
			@PathVariable String destination) {
		List<Train> trains = trainService.getTrainsBetweenStations(source, destination);
		return ResponseEntity.ok(trains);
	}

	@PostMapping
	public ResponseEntity<Train> addTrain(@RequestBody Train train) {
		Train savedTrain = trainService.addTrain(train);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedTrain);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Train> updateTrain(@PathVariable Long id, @RequestBody Train train) {
		Train updatedTrain = trainService.updateTrain(id, train);
		return ResponseEntity.ok(updatedTrain);
	}

	@DeleteMapping("/{number}")
	public ResponseEntity<Void> deleteTrain(@PathVariable String number) {
		trainService.deleteTrain(number);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<Train>> allTrainss() {
		List<Train> trains = trainService.allTrains();
		return ResponseEntity.status(HttpStatus.OK).body(trains);
	}
}
