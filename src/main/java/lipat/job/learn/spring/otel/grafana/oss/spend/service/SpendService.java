package lipat.job.learn.spring.otel.grafana.oss.spend.service;

import java.util.UUID;
import lipat.job.learn.spring.otel.grafana.oss.spend.model.Spend;
import lipat.job.learn.spring.otel.grafana.oss.spend.repository.SpendRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SpendService {
  private final SpendRepository spendRepository;

  public Spend getSpend(UUID id) {
    return spendRepository.findById(id).orElse(null);
  }

  public UUID saveSpend(Spend spend) {
    return spendRepository.save(spend).getId();
  }
}
