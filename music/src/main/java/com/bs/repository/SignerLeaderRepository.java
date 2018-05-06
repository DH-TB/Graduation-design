package com.bs.repository;

import com.bs.entity.SingerLeaderBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SignerLeaderRepository extends JpaRepository<SingerLeaderBoard,Long>{
    List<SingerLeaderBoard> findByTagOrderByHot(String tag);


}
