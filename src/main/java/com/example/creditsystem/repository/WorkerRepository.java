package com.example.creditsystem.repository;

import com.example.creditsystem.entity.Client;
import com.example.creditsystem.entity.Worker;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkerRepository extends JpaRepository<Worker,Long> {
    @NotNull Optional<Worker> findByUsersId(@NotNull Long id);

    @NotNull Optional<Worker> findById(@NotNull Long id);


}
