package com.example.airBnbProject.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingRequestDto {
    private Long hotelId;
    private Long roomId;
    private LocalDate checkinDate;
    private LocalDate checkoutDate;
    private Integer roomsCount;
}
