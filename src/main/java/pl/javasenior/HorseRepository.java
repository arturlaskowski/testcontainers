package pl.javasenior;

import org.springframework.data.jpa.repository.JpaRepository;

interface HorseRepository extends JpaRepository<Horse, Long> {
}
