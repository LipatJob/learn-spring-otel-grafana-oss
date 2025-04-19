package lipat.job.learn.spring.otel.grafana.oss.common.util;

import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class ProblemDetailBuilder {
  private final ProblemDetail problemDetail;

  private ProblemDetailBuilder(HttpStatus status) {
    this.problemDetail = ProblemDetail.forStatus(status);
  }

  public static ProblemDetailBuilder withStatus(HttpStatus status) {
    return new ProblemDetailBuilder(status);
  }

  public ProblemDetailBuilder title(String title) {
    problemDetail.setTitle(title);
    return this;
  }

  public ProblemDetailBuilder detail(String detail) {
    problemDetail.setDetail(detail);
    return this;
  }

  public ProblemDetailBuilder instance(URI instance) {
    problemDetail.setInstance(instance);
    return this;
  }

  public ProblemDetailBuilder type(URI type) {
    problemDetail.setType(type);
    return this;
  }

  public ProblemDetailBuilder property(String name, Object value) {
    problemDetail.setProperty(name, value);
    return this;
  }

  public ProblemDetail build() {
    return problemDetail;
  }
}
