package com.example.service;

import com.example.model.AdditionalService;
import com.example.repository.AdditionalServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdditionalServiceService {

    @Autowired
    private AdditionalServiceRepository serviceRepository;

    public List<AdditionalService> getAllServices() {
        return serviceRepository.findAll();
    }

    public List<AdditionalService> getAvailableServices() {
        return serviceRepository.findByAvailable(true);
    }

    public AdditionalService getServiceById(String id) {
        return serviceRepository.findById(id).orElse(null);
    }

    public AdditionalService save(AdditionalService service) {
        return serviceRepository.save(service);
    }

    public void delete(String id) {
        serviceRepository.deleteById(id);
    }
}
