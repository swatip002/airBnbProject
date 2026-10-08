package com.example.airBnbProject.repository;

import com.example.airBnbProject.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {

}
