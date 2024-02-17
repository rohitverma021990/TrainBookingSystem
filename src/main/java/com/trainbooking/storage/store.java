package com.trainbooking.storage;

import com.trainbooking.exception.NotFoundException;

import java.util.List;

public interface store<key,value> {
    value get(key key) throws NotFoundException;
    void remove(key key) throws NotFoundException;
    void insert(key key, value value);
    List<value> getAll();
}
