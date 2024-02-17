package com.trainbooking.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@ToString
public class Train {
    private final String source;
    private final String destination;
    private final Date journeyDate;
    private final String id;
    private List<Seat> sectionA;
    private List<Seat> sectionB;

    public Train(@NonNull final String source, @NonNull final String destination, @NonNull String id){
        this.source = source;
        this.destination = destination;
        this.id = id;
        this.sectionA = new ArrayList<>();
        this.sectionB = new ArrayList<>();
        this.journeyDate = new Date();
    }

    public void addSeat(Seat seat, String section){
        if(section.equals("A"))
            sectionA.add(seat);
        else
            sectionB.add(seat);
    }
}
