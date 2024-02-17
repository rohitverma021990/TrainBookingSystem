package com.trainbooking.service;

import com.trainbooking.enums.SeatType;
import com.trainbooking.exception.NotFoundException;
import com.trainbooking.model.Seat;
import com.trainbooking.model.Train;
import com.trainbooking.storage.TrainStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TrainService {
    private TrainStore store;
    public TrainService(){
        this.store = new TrainStore();
    }

    public String createTrain(String source, String destination){
        String id = UUID.randomUUID().toString();
        Train train = new Train(source, destination, id);
        this.store.insert(id, train);
        return id;
    }

    public Train getTrainInfo(String trainNo){
        try{
            return (Train) this.store.get(trainNo);
        }catch (Exception e){
            System.out.printf("No train available for the train no");
        }
        return null;
    }

    public Train getTrainInfo(String from, String to){
        List<Train> getAllTrains = this.store.getAll();
        Optional<Train> trainOptional = getAllTrains.stream().filter(a -> a.getSource().equals(from) && a.getDestination().equals(to)).findFirst();
        return trainOptional.get();
    }

    public void addSeat(int seatNo, SeatType seatType, String section, String trainNo) throws NotFoundException {
        Seat seat = new Seat(seatType.getPrice(), seatType.getType(), seatNo, section, false);
        Train train = getTrainInfo(trainNo);
        if(train!=null){
            train.addSeat(seat, section);
        }else{
            throw new NotFoundException();
        }
    }

    public boolean isSeatAvailable(SeatType seatType, String trainNum, int requiredSeats){
        Train train = getTrainInfo(trainNum);
        Long bookedSeats = train.getSectionA().stream().filter(a-> a.isBooked() && a.getType().equals(seatType.getType())).count();
        bookedSeats+=train.getSectionB().stream().filter(a->a.isBooked() && a.getType().equals(seatType.getType())).count();
        Long totalSeats = train.getSectionA().stream().filter(a-> a.getType().equals(seatType.getType())).count();
        totalSeats+= train.getSectionB().stream().filter(a-> a.getType().equals(seatType.getType())).count();
        return (totalSeats-bookedSeats)>=requiredSeats;
    }


    public List<Seat> getAvailableSeats(String trainNum, int numberOfSeat, SeatType seatType) {
        Train train = getTrainInfo(trainNum);
        List<Seat> seats = new ArrayList<>();
        for(Seat seat : train.getSectionA()){
            if(!seat.isBooked() && seat.getType().equals(seatType.getType()))
                seats.add(seat);
            if(seats.size()==numberOfSeat)
                break;
        }
        if(seats.size()<numberOfSeat){
            for(Seat seat : train.getSectionB()){
                if(!seat.isBooked() && seat.getType().equals(seatType.getType()))
                    seats.add(seat);
                if(seats.size()==numberOfSeat)
                    break;
            }
        }
        return seats;
    }
}
