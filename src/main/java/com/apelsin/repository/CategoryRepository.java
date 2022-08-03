package com.apelsin.repository;

import com.apelsin.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, String> {
    Optional<CategoryEntity> findByIdAndVisible(String id, boolean b);

    List<CategoryEntity> findAllByVisible(boolean b);

    @Transactional
    @Modifying
    @Query("update CategoryEntity set visible = :visible where id = :id")
    int deleteStatus(@Param("visible") boolean b, @Param("id") String id);
}