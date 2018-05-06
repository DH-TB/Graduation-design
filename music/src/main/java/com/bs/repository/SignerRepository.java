package com.bs.repository;

import com.bs.entity.Singer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignerRepository extends JpaRepository<Singer, Long> {
    Singer findBySinger(String singer);
}
