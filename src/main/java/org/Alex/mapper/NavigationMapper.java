package org.Alex.mapper;

import org.Alex.pojo.Navigation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NavigationMapper extends JpaRepository<Navigation, Integer> {

    @Transactional
    @Modifying
    @Query("update Navigation set ordered = ?2 where  id = ?1")
    void ordered(Integer id, Integer order);

    List<Navigation> findAllByEnableOrderByOrderedAsc(Boolean enable);
}
