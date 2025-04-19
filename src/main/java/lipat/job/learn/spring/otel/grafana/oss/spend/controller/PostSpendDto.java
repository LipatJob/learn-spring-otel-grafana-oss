package lipat.job.learn.spring.otel.grafana.oss.spend.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import lipat.job.learn.spring.otel.grafana.oss.spend.model.Spend;

public record PostSpendDto(
    @Pattern(
            regexp = descriptionRegex,
            message = "Description must be alphanumeric and can contain spaces")
        String description,
    @NotBlank
        @Pattern(
            regexp = amountRegex,
            message = "Amount must be a valid decimal number with up to 2 decimal places")
        String amount) {
  private static final String descriptionRegex = "^[a-zA-Z0-9\\s]+$";
  private static final String amountRegex = "^\\d+(\\.\\d{1,2})?$";

  Spend toSpend() {
    return new Spend(null, description, new BigDecimal(amount));
  }
}
