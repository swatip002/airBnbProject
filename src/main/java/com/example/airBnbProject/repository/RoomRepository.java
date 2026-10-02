package com.example.airBnbProject.repository;

import com.example.airBnbProject.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room,Long> {
}
