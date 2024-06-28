package com.vinca.backboard.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vinca.backboard.entity.Reset;

public interface ResetRepository extends JpaRepository<Reset, Integer>{
    
    Optional<Reset> findByUuid(String uuid);    //  UUID로 찾기
}
