package me.changjun.restaurant.interfaces;

import me.changjun.restaurant.application.RegionService;
import me.changjun.restaurant.domain.Region;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
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

    @PostMapping("/api/regions")
    public ResponseEntity create(@RequestBody Region resource) throws URISyntaxException {
        Region newRegion = regionService.addRegion(resource);
        URI uri = new URI("/api/regions/" + newRegion.getId());
        return ResponseEntity.created(uri).body("{}");
    }
}
