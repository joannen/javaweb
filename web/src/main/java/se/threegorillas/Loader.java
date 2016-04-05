package se.threegorillas;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import se.threegorillas.model.User;
import se.threegorillas.service.DataBaseService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.ServletContext;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;

/**
 * Created by TheYellowBelliedMarmot on 2016-03-22.
 */

@ApplicationPath("/*")
public final class Loader extends Application{

    private AnnotationConfigApplicationContext context;

    @Context
    private ServletContext servletContext;

    @PostConstruct
    public void init(){
        context = new AnnotationConfigApplicationContext();
        context.scan("se.threegorillas");
        context.refresh();

        DataBaseService dataBaseService = context.getBean(DataBaseService.class);
        servletContext.setAttribute("database", dataBaseService);
        saveSampleUser();
    }

    private void saveSampleUser(){
        DataBaseService service = (DataBaseService) servletContext.getAttribute("database");
        service.saveUser(new User(1L, "A", "A", "A", "A", "A"));
    }

    @PreDestroy
    public void destroy(){
        context.destroy();
    }
}
