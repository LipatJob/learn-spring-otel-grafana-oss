package lipat.job.learn.spring.otel.grafana.oss.common.controller;

import java.net.URI;
import lipat.job.learn.spring.otel.grafana.oss.common.util.ProblemDetailBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(DataAccessException.class)
  public ProblemDetail handleDataAccessException(DataAccessException e) {
    log.error(e.getMessage(), e);
    return ProblemDetailBuilder.withStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        .type(URI.create("/errors/DATABASE_ERROR"))
        .title("Database Error")
        .detail(e.getMessage())
        .property("code", "DATABASE_ERROR")
        .build();
  }

  @ExceptionHandler(Exception.class)
  public ProblemDetail handleGlobalException(Exception e) {
    log.error(e.getMessage(), e);
    return ProblemDetailBuilder.withStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        .type(URI.create("/errors/INTERNAL_SERVER_ERROR"))
        .title("Internal Server Error")
        .detail(e.getMessage())
        .property("code", "INTERNAL_SERVER_ERROR")
        .build();
  }

  @ExceptionHandler(HandlerMethodValidationException.class)
  public ProblemDetail handleValidationException(HandlerMethodValidationException e) {
    log.error(e.getMessage(), e);
    var visitor = new HandlerMethodValidationExceptionVisitor();
    var errors = visitor.getErrorResults();
    return ProblemDetailBuilder.withStatus(HttpStatus.BAD_REQUEST)
        .type(URI.create("/errors/INVALID_REQUEST"))
        .title("Invalid Request")
        .detail("There are validation errors in the request")
        .property("code", "INVALID_REQUEST")
        .property("errors", errors)
        .build();
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    log.error(e.getMessage(), e);
    var errors = ValidationError.fromFieldErrors("body", e.getBindingResult().getFieldErrors());
    return ProblemDetailBuilder.withStatus(HttpStatus.BAD_REQUEST)
        .type(URI.create("/errors/INVALID_REQUEST"))
        .title("Invalid Request")
        .detail("There are validation errors in the request")
        .property("code", "INVALID_REQUEST")
        .property("errors", errors)
        .build();
  }
}
