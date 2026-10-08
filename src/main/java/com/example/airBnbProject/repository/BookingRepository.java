package com.example.airBnbProject.repository;

import com.example.airBnbProject.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,Long> {
}
