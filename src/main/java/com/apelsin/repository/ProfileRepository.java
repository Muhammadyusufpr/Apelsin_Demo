package com.apelsin.repository;

import com.apelsin.entity.ProfileEntity;
import com.apelsin.enums.ProfileStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, String> {
    List<ProfileEntity> findAllByVisible(boolean b);

    Optional<ProfileEntity> findByIdAndVisible(String id, boolean b);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set visible = :visible where id = :id")
    int deleteStatus(@Param("visible") boolean b, @Param("id") String id);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set status = :status where id = :id")
    int changeStatus(@Param("status") ProfileStatus status, @Param("id") String id);

    Optional<ProfileEntity> findByPhone(String phone);

    Optional<ProfileEntity> findByPhoneAndPassword(String phone, String password);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set status = :status where phone = :phone")
    int activation(@Param("status") ProfileStatus status, @Param("phone") String phone);
}