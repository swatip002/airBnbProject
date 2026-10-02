package com.example.airBnbProject.repository;

import com.example.airBnbProject.entity.Inventory;
import com.example.airBnbProject.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {

    void deleteByRoom(Room room );

}
