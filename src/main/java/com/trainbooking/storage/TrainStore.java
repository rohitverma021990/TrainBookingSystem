package com.trainbooking.storage;

import com.trainbooking.exception.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrainStore<key, value> implements store<key, value>{
    private Map<key, value> store;


    public TrainStore(){
        this.store = new HashMap<>();
    }

    @Override
    public value get(key key) throws NotFoundException {
        if (store.containsKey(key))
            return store.get(key);
        throw new NotFoundException();
    }

    @Override
    public void remove(key key) throws NotFoundException {
        if (store.containsKey(key))
            store.remove(key);
        throw new NotFoundException();
    }

    @Override
    public void insert(key key, value value) {
        this.store.put(key, value);
    }

    @Override
    public List<value> getAll() {
        return new ArrayList<>(this.store.values());
    }
}
