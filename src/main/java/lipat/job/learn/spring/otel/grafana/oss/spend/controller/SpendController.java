package lipat.job.learn.spring.otel.grafana.oss.spend.controller;

import io.opentelemetry.instrumentation.annotations.SpanAttribute;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.UUID;
import lipat.job.learn.spring.otel.grafana.oss.spend.model.Spend;
import lipat.job.learn.spring.otel.grafana.oss.spend.service.SpendService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@Controller
@RequestMapping("/v1/spend")
public class SpendController {
  private final SpendService spendService;

  @PostMapping
  @WithSpan
  public ResponseEntity<Spend> createSpend(
      @SpanAttribute @Valid @RequestBody PostSpendDto spendDto) {
    var spend = spendDto.toSpend();
    var id = spendService.saveSpend(spend);
    var location = "/v1/spend/%s".formatted(id);

    log.atInfo()
        .setMessage("Spend created with id: {}")
        .addArgument(id)
        .addKeyValue("event", "spend.create")
        .addKeyValue("status", "success")
        .addKeyValue("spend", spend)
        .log();
    return ResponseEntity.created(URI.create(location)).body(spend);
  }

  @GetMapping("/{id}")
  @WithSpan
  public ResponseEntity<Spend> getSpend(@SpanAttribute @PathVariable UUID id) {
    var spend = spendService.getSpend(id);
    if (spend == null) {
      log.atWarn()
          .setMessage("Spend not found with id: {}")
          .addArgument(id)
          .addKeyValue("status", "not_found")
          .log();
      return ResponseEntity.notFound().build();
    }
    log.atInfo()
        .setMessage("Spend found with id: {}")
        .addArgument(id)
        .addKeyValue("event", "spend.get")
        .addKeyValue("spend", spend)
        .addKeyValue("status", "success")
        .log();
    return ResponseEntity.ok(spend);
  }
}
