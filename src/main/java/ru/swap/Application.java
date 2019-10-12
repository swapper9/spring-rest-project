package ru.swap;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration("mainBean")
public class Application {

  public static void main(String[] args) {
    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
    try {
      ctx.register(Application.class);
      ctx.refresh();
      Application application = (Application) ctx.getBean("mainBean");
    } finally {
      ctx.close();
    }
  }
}
