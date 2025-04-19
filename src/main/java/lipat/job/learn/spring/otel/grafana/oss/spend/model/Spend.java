package lipat.job.learn.spring.otel.grafana.oss.spend.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Spend {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  UUID id;

  String description;

  @Column(precision = 19, scale = 2)
  BigDecimal amount;
}
