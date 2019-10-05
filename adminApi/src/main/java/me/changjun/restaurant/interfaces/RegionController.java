package me.changjun.restaurant.interfaces;

import me.changjun.restaurant.application.RegionService;
import me.changjun.restaurant.domain.Region;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RegionController {

    private final RegionService regionService;

    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping("/api/regions")
    public List<Region> getRegions() {
        return regionService.getRegions();
    }
}
