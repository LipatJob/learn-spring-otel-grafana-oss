package lipat.job.learn.spring.otel.grafana.oss.spend.repository;

import java.util.Optional;
import java.util.UUID;
import lipat.job.learn.spring.otel.grafana.oss.spend.model.Spend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpendRepository extends JpaRepository<Spend, UUID> {
  Spend save(Spend spend);

  Optional<Spend> findById(UUID id);
}
