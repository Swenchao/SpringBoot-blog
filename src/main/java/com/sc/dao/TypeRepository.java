package com.sc.dao;

import com.sc.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface TypeRepository extends JpaRepository<Type,Long> {

    Type findByName(String name);


    // 分类查询排序（分页查询，只取第一页）

    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);
}
