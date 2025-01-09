package com.denisvasilchenko.web_mvc_application.service;

import com.denisvasilchenko.web_mvc_application.entity.Return;
import com.denisvasilchenko.web_mvc_application.repository.ReturnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReturnService {
    @Autowired
    private ReturnRepository returnRepository;

    public List<Return> findAll() {
        return returnRepository.findAll();
    }

    public void save(Return entity) {
        returnRepository.save(entity);
    }

    public List<Return> getReturnsPerDay(LocalDate localDate) {
        return returnRepository.findAllByReturnDateTimeBetween(localDate.atStartOfDay(), localDate.atTime(LocalTime.MAX));
    }
}
