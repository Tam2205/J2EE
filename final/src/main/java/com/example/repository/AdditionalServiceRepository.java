package com.example.repository;

import com.example.model.AdditionalService;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AdditionalServiceRepository extends MongoRepository<AdditionalService, String> {

    List<AdditionalService> findByAvailable(boolean available);

    List<AdditionalService> findByCategory(String category);
}
