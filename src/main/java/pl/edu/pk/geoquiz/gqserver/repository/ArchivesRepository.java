package pl.edu.pk.geoquiz.gqserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.edu.pk.geoquiz.gqserver.model.entity.Archives;

import java.util.Optional;

@Repository
public interface ArchivesRepository extends JpaRepository<Archives, Integer> {

	@Query(value = "SELECT * FROM ARCHIVES WHERE QUESTION_ATTRIBUTES_ID = :qAttribId", nativeQuery = true)
	Optional<Archives> findByQAttribId(@Param("qAttribId") int qAttribId);
}