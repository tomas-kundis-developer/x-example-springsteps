package org.springsteps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot application class.
 */
@SpringBootApplication
public class Application {

  /**
   * Spring Boot starts here.
   *
   * @param args Java command-line arguments.
   */
  static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
