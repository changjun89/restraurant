package me.changjun.restaurant.application;

import me.changjun.restaurant.domain.Region;
import me.changjun.restaurant.domain.RegionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionService {
    private final RegionRepository regionRepository;

    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    public List<Region> getRegions() {
        return regionRepository.findAll();
    }
}
