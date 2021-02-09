package com.gsm.jupjup.repo;

import com.gsm.jupjup.model.Notice;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import javax.sql.rowset.Predicate;

public interface NoticeRepo extends JpaRepository<Notice, Long> {


}
