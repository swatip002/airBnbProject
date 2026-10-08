package com.example.airBnbProject.service;

import com.example.airBnbProject.dto.HotelPriceDto;
import com.example.airBnbProject.dto.HotelSearchRequest;
import com.example.airBnbProject.entity.Room;
import org.springframework.data.domain.Page;

public interface InventoryService {

    void initializeRoomForAYear(Room room);

    void deleteAllInventories(Room room);

    Page<HotelPriceDto> searchHotels(HotelSearchRequest hotelSearchRequest);
}
