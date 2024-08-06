package com.example.demo.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Notes;

@Repository
public interface NotificationRepo extends CrudRepository<Notes, Integer> {

}