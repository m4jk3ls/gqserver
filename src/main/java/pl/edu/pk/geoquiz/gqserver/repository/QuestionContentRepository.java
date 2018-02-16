package pl.edu.pk.geoquiz.gqserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pk.geoquiz.gqserver.model.entity.QuestionContent;

@Repository
public interface QuestionContentRepository extends JpaRepository<QuestionContent, Integer> {
}
