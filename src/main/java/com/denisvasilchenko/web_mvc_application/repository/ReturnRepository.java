package com.denisvasilchenko.web_mvc_application.repository;

import com.denisvasilchenko.web_mvc_application.entity.Return;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReturnRepository extends JpaRepository<Return, Long> {

    public List<Return> findAllByReturnDateTimeBetween(LocalDateTime from, LocalDateTime to);
}
