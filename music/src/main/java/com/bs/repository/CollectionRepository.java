package com.bs.repository;

import com.bs.entity.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollectionRepository extends JpaRepository<Collection, Long> {
    List<Collection> findByUserIdOrderByLove(Long userId);

    List<Collection> findByUserIdOrderByCount(Long userId);

    List<Collection> findByMusicIdOrderByCount(Long musicId);

    List<Collection> findByUserId(Long userId);

    Collection deleteByMusicId(Long musicId);
}
