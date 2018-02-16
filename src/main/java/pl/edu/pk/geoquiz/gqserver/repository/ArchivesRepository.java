package pl.edu.pk.geoquiz.gqserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pk.geoquiz.gqserver.model.entity.Archives;

@Repository
public interface ArchivesRepository extends JpaRepository<Archives, Integer> {
}