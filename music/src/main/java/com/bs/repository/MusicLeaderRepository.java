package com.bs.repository;

import com.bs.entity.MusicLeaderBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MusicLeaderRepository extends JpaRepository<MusicLeaderBoard,Long>{
    List<MusicLeaderBoard> findByTypeOrderByHotDesc(String type);
    List<MusicLeaderBoard> findBySingerIdOrderByHotDesc(Long signerId);
}
