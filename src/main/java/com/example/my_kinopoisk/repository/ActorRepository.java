package com.example.my_kinopoisk.repository;

import com.example.my_kinopoisk.domain.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ActorRepository extends JpaRepository<Actor, Long>{
}
