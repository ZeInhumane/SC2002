package com.example.app.repository;


import com.example.app.models.Officer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfficerRepository extends JpaRepository<Officer, Long> {
}
