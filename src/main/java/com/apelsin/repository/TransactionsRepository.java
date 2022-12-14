package com.apelsin.repository;

import com.apelsin.entity.TransactionsEntity;
import com.apelsin.enums.TranStatus;
import com.apelsin.mapper.TransactionsMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionsRepository extends JpaRepository<TransactionsEntity, String> {
    @Query("SELECT t.id as t_id,fcr.id as fcr_id,fcr.number as fcr_number,fcr.phone as fcr_phone," +
            " fcl.id as fcl_id,fcl.name as fcl_name,fcl.surname as fcl_surname, " +
            " tcr.id as tcr_id,tcr.number as tcr_number,tcr.phone as tcr_phone," +
            " tcl.id as tcl_id,tcl.name as tcl_name,tcl.surname as tcl_surname," +
            " t.amount as t_amount,t.status as t_status,t.createdDate as t_createdDate FROM TransactionsEntity as t" +
            " inner join t.fromCard as fcr" +
            " inner join fcr.profile as fcl" +
            " inner join t.toCard as tcr" +
            " inner join tcr.profile as tcl" +
            " where fcr.id=:cid  and t.status=:status" +
            " order by t.createdDate")
    Page<TransactionsMapper> getByCardId(Pageable pageable, @Param("cid") String cid, @Param("status")TranStatus status);
    @Query("SELECT t.id as t_id,fcr.id as fcr_id,fcr.number as fcr_number,fcr.phone as fcr_phone," +
            " fcl.id as fcl_id,fcl.name as fcl_name,fcl.surname as fcl_surname, " +
            " tcr.id as tcr_id,tcr.number as tcr_number,tcr.phone as tcr_phone," +
            " tcl.id as tcl_id,tcl.name as tcl_name,tcl.surname as tcl_surname," +
            " t.amount as t_amount,t.status as t_status,t.createdDate as t_createdDate FROM TransactionsEntity as t" +
            " inner join t.fromCard as fcr" +
            " inner join fcr.profile as fcl" +
            " inner join t.toCard as tcr" +
            " inner join tcr.profile as tcl" +
            " where fcl.id=:cid  and t.status=:status" +
            " order by t.createdDate")
    Page<TransactionsMapper> getByClientId(Pageable pageable, @Param("cid") String cid, @Param("status") TranStatus status);
    @Query("SELECT t.id as t_id,fcr.id as fcr_id,fcr.number as fcr_number,fcr.phone as fcr_phone," +
            " fcl.id as fcl_id,fcl.name as fcl_name,fcl.surname as fcl_surname, " +
            " tcr.id as tcr_id,tcr.number as tcr_number,tcr.phone as tcr_phone," +
            " tcl.id as tcl_id,tcl.name as tcl_name,tcl.surname as tcl_surname," +
            " t.amount as t_amount,t.status as t_status,t.createdDate as t_createdDate FROM TransactionsEntity as t" +
            " inner join t.fromCard as fcr" +
            " inner join fcr.profile as fcl" +
            " inner join t.toCard as tcr" +
            " inner join tcr.profile as tcl" +
            " where fcr.phone=:phone  and t.status=:status" +
            " order by t.createdDate")
    Page<TransactionsMapper> getByPhone(Pageable pageable, @Param("phone") String cid, @Param("status") TranStatus status);

}