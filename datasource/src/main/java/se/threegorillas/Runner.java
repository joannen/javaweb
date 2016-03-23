package se.threegorillas;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import se.threegorillas.model.User;
import se.threegorillas.model.WorkItem;
import se.threegorillas.repository.UserRepository;
import se.threegorillas.service.DataBaseService;

import java.util.ArrayList;
import java.util.Collection;

public final class Runner {

    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.scan("se.threegorillas");
            context.refresh();

            DataBaseService dataBaseService = context.getBean(DataBaseService.class);

            WorkItem workItem = new WorkItem("do it!");

            Collection<WorkItem> workItemCollection = new ArrayList<WorkItem>();
            System.out.println(workItemCollection.getClass());

        }
    }

}
