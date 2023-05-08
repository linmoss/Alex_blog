package org.Alex.mapper;

import org.Alex.pojo.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;

public interface BannerMapper extends JpaRepository<Banner, Integer> {

    @Transactional
    @Modifying
    @Query("update Banner set ordered = ?2 where  id = ?1")
    void ordered(Integer id, Integer order);
}
