package pl.javasenior;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/horses")
class HorseController {

    private final HorseRepository horseRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Horse> getHorse(@PathVariable("id") Long id) {
        return ResponseEntity.ok(horseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horse not found with id: " + id)));
    }

    @PostMapping
    public ResponseEntity<Long> addHorse(@RequestBody Horse newHorse) {
        return ResponseEntity.ok(horseRepository.save(newHorse).getId());
    }
}