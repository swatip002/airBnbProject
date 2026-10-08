package com.example.airBnbProject.service;

import com.example.airBnbProject.dto.BookingDto;
import com.example.airBnbProject.dto.BookingRequestDto;
import com.example.airBnbProject.dto.GuestDto;
import com.example.airBnbProject.entity.*;
import com.example.airBnbProject.entity.enums.BookingStatus;
import com.example.airBnbProject.exception.ResourceNotFoundException;
import com.example.airBnbProject.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService{

    private final BookingRepository bookingRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;
    private final GuestRepository guestRepository;

    @Override
    @Transactional
    public BookingDto initialiseBooking(BookingRequestDto bookingRequest) {
        log.info("Initialising booking for hotel : {}, room: {}, date {}-{}", bookingRequest.getHotelId(), bookingRequest.getRoomId(), bookingRequest.getCheckinDate(), bookingRequest.getCheckoutDate());
        Hotel hotel = hotelRepository
                .findById(bookingRequest.getHotelId())
                .orElseThrow(()-> new ResourceNotFoundException("Hotel not found with booking id: "+bookingRequest.getHotelId()));

        Room room = roomRepository
                .findById(bookingRequest.getRoomId())
                .orElseThrow(()-> new ResourceNotFoundException("Room not found with booking id: "+bookingRequest.getRoomId()));

        LocalDate endDate = bookingRequest.getCheckoutDate().minusDays(1);
        List<Inventory> inventoryList = inventoryRepository.findAndLockAvailableInventory(bookingRequest.getRoomId(),
                bookingRequest.getCheckinDate(),
                endDate,
                bookingRequest.getRoomsCount());
        log.info("the inventory list is: " + inventoryList);
        long daysCount = ChronoUnit.DAYS.between(bookingRequest.getCheckinDate(),bookingRequest.getCheckoutDate());

        if(inventoryList.size() != daysCount){
            throw new IllegalStateException("Room is not available anymore");
        }

        //Reserve the room/ update the booked count of inventories
        for(Inventory inventory: inventoryList){
            inventory.setReservedCount(inventory.getReservedCount() + bookingRequest.getRoomsCount());
        }

        inventoryRepository.saveAll(inventoryList);

        //create the booking

        //TODO: Calculate dynamic price

        Booking booking = Booking.builder()
                .bookingStatus(BookingStatus.RESERVED)
                .hotel(hotel)
                .room(room)
                .checkInDate(bookingRequest.getCheckinDate())
                .checkOutDate(bookingRequest.getCheckoutDate())
                .user(getCurrentUser())
                .roomsCount(bookingRequest.getRoomsCount())
                .amount(BigDecimal.TEN)
                .build();
        booking = bookingRepository.save(booking);
        return modelMapper.map(booking, BookingDto.class);
    }

    @Override
    public BookingDto addGuests(Long bookingId, List<GuestDto> guestDtoList) {

        log.info("Adding guests for booking with id : {}", bookingId);
        Booking booking = bookingRepository
                .findById(bookingId)
                .orElseThrow(()-> new ResourceNotFoundException("Booking not found with id "+bookingId));
        if(hasBookingExpired(booking)){
            throw new IllegalStateException("Booking has already been expired");
        }

        if(booking.getBookingStatus() != BookingStatus.RESERVED){
            throw new IllegalStateException("Booking is not under reserved state ,cannot add guests");
        }

        for (GuestDto guestDto : guestDtoList){
            Guest guest = modelMapper.map(guestDto, Guest.class);
            guest.setUser(getCurrentUser());
            guest = guestRepository.save(guest);
            booking.getGuests().add(guest);

            booking.setBookingStatus(BookingStatus.GUESTS_ADDED);
            booking = bookingRepository.save(booking);

            return modelMapper.map(booking, BookingDto.class);
        }
        return null;
    }

    public boolean hasBookingExpired(Booking booking){
        return booking.getCreatedAt().plusMinutes(10).isBefore(LocalDateTime.now());
    }

    public User getCurrentUser(){
        User user = new User();
        user.setId(1L);//TODO: REMOVE DUMMY USER
        return user;
    }
}
