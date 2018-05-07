package com.bs.repository;

import com.bs.entity.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface CollectionRepository extends JpaRepository<Collection, Long> {
    List<Collection> findByUserIdOrderByLoveDesc(Long userId);

    List<Collection> findByUserIdOrderByCountDesc(Long userId);

    List<Collection> findByMusicIdOrderByCountDesc(Long musicId);

    List<Collection> findByUserId(Long userId);

    @Modifying
    @Transactional
    @Query("delete from Collection where musicId=?1 and userId=?2")
    void deleteByMusicIdAndUserId(Long musicId,Long userId);
}
