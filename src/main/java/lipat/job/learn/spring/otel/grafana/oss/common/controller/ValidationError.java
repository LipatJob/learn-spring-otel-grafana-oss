package lipat.job.learn.spring.otel.grafana.oss.common.controller;

import java.util.Collection;
import org.springframework.validation.FieldError;

record ValidationError(String in, String name, String message) {
  static ValidationError fromFieldError(String in, FieldError fieldError) {
    return new ValidationError(in, fieldError.getField(), fieldError.getDefaultMessage());
  }

  static Collection<ValidationError> fromFieldErrors(
      String in, Collection<FieldError> fieldErrors) {
    return fieldErrors.stream().map(fieldError -> fromFieldError(in, fieldError)).toList();
  }
}
