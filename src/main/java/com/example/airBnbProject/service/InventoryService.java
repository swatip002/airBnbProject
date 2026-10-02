package com.example.airBnbProject.service;

import com.example.airBnbProject.entity.Room;

public interface InventoryService {

    void initializeRoomForAYear(Room room);

    void deleteAllInventories(Room room);
}
