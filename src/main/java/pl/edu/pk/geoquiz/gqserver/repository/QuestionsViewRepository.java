package pl.edu.pk.geoquiz.gqserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edu.pk.geoquiz.gqserver.model.entity.QuestionsView;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface QuestionsViewRepository extends JpaRepository<QuestionsView, Integer> {

	@Query(value = "SELECT ID FROM QUESTIONS_VIEW", nativeQuery = true)
	List<BigDecimal> findAllIds();
}