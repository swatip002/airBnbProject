package com.example.airBnbProject.service;

import com.example.airBnbProject.dto.BookingDto;
import com.example.airBnbProject.dto.BookingRequestDto;
import com.example.airBnbProject.dto.GuestDto;

import java.util.List;

public interface BookingService {

    BookingDto initialiseBooking(BookingRequestDto bookingRequest);

    BookingDto addGuests(Long bookingId, List<GuestDto> guestDtoList);
}
