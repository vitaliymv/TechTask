package com.example.demo.repository;

import com.example.demo.entity.Info;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface InfoRepository extends JpaRepository<Info, Long> {

}
