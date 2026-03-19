package org.springsteps;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ApiVersionConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring REST configuration.
 */
@Configuration
public class SpringWebConfiguration implements WebMvcConfigurer {

  /**
   * Enable out-of-the-box API versioning with /{api-version}/my-service UTL path segment.
   */
  @Override
  public void configureApiVersioning(ApiVersionConfigurer configurer) {
    configurer.usePathSegment(0);
  }
}
