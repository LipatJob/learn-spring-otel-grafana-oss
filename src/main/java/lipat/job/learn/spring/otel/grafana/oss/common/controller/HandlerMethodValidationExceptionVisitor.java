package lipat.job.learn.spring.otel.grafana.oss.common.controller;

import java.util.ArrayList;
import lombok.Getter;
import org.springframework.validation.method.ParameterErrors;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@Getter
public class HandlerMethodValidationExceptionVisitor
    implements HandlerMethodValidationException.Visitor {
  private final ArrayList<ValidationError> errorResults;

  public HandlerMethodValidationExceptionVisitor() {
    this.errorResults = new ArrayList<>();
  }

  @Override
  public void cookieValue(CookieValue cookieValue, ParameterValidationResult result) {
    errorResults.add(
        new ValidationError(
            "cookieValue",
            cookieValue.name(),
            result.getResolvableErrors().getFirst().getDefaultMessage()));
  }

  @Override
  public void matrixVariable(MatrixVariable matrixVariable, ParameterValidationResult result) {
    errorResults.add(
        new ValidationError(
            "matrixVariable",
            matrixVariable.name(),
            result.getResolvableErrors().getFirst().getDefaultMessage()));
  }

  @Override
  public void modelAttribute(ModelAttribute modelAttribute, ParameterErrors errors) {
    var validationMessages =
        ValidationError.fromFieldErrors("modelAttribute", errors.getFieldErrors());
    errorResults.addAll(validationMessages);
  }

  @Override
  public void pathVariable(PathVariable pathVariable, ParameterValidationResult result) {
    errorResults.add(
        new ValidationError(
            "pathVariable",
            pathVariable.name(),
            result.getResolvableErrors().getFirst().getDefaultMessage()));
  }

  @Override
  public void requestBody(RequestBody requestBody, ParameterErrors errors) {
    var validationMessages =
        ValidationError.fromFieldErrors("requestBody", errors.getFieldErrors());
    errorResults.addAll(validationMessages);
  }

  @Override
  public void requestHeader(RequestHeader requestHeader, ParameterValidationResult result) {
    errorResults.add(
        new ValidationError(
            "requestHeader",
            requestHeader.name(),
            result.getResolvableErrors().getFirst().getDefaultMessage()));
  }

  @Override
  public void requestParam(RequestParam requestParam, ParameterValidationResult result) {
    errorResults.add(
        new ValidationError(
            "requestParam",
            requestParam != null ? requestParam.name() : "unknown",
            result.getResolvableErrors().getFirst().getDefaultMessage()));
  }

  @Override
  public void requestPart(RequestPart requestPart, ParameterErrors errors) {
    errorResults.add(
        new ValidationError(
            "requestParam",
            requestPart.name(),
            errors.getResolvableErrors().getFirst().getDefaultMessage()));
  }

  @Override
  public void other(ParameterValidationResult result) {
    errorResults.add(
        new ValidationError(
            "other", "message", result.getResolvableErrors().getFirst().getDefaultMessage()));
  }
}
