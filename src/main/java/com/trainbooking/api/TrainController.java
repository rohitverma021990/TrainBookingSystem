package com.trainbooking.api;

import com.trainbooking.enums.SeatType;
import com.trainbooking.exception.NotFoundException;
import com.trainbooking.model.Train;
import com.trainbooking.service.TrainService;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class TrainController {
    private TrainService trainService;

    public String createTrain(@NonNull final String source, @NonNull final String destination){
        return trainService.createTrain(source, destination);
    }

    public void addSeat(int seatNo, @NonNull final SeatType seatType, @NonNull final String section, @NonNull final String trainNum){
        try{
            trainService.addSeat(seatNo, seatType, section, trainNum);
        }catch (NotFoundException e){
            System.out.println("Train not found");
        }
    }

    public Train getTrainObj(@NonNull final String trainNum){
        return trainService.getTrainInfo(trainNum);
    }

    public boolean isSeatAvailable(@NonNull final SeatType seatType, @NonNull final String trainNum){
        return trainService.isSeatAvailable(seatType, trainNum, 1);
    }
}
