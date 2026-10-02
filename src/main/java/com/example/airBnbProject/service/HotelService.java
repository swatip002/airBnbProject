package com.example.airBnbProject.service;

import com.example.airBnbProject.dto.HotelDto;
import com.example.airBnbProject.entity.Hotel;

public interface HotelService {
    HotelDto createHotel(HotelDto hotelDto);

    HotelDto getHotelById(Long id);

    HotelDto updateHotelById(Long id, HotelDto hotelDto);

    void deleteHotelById(Long id);

    void activateHotel(Long hotelId);
}
