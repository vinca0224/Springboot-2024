package com.vinca.backboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vinca.backboard.entity.Reply;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long>{
    
}
