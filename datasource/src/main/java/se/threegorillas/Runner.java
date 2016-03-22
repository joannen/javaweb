package se.threegorillas;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import se.threegorillas.model.User;
import se.threegorillas.repository.UserRepository;
import se.threegorillas.service.DataBaseService;

public final class Runner {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.scan("se.threegorillas");
            context.refresh();

            DataBaseService dataBaseService = context.getBean(DataBaseService.class);

            User user = new User("fredrik", "Fredrik", "Hollinger", "watawt", "132345");

            User saved = dataBaseService.saveUser(user);

            User a = dataBaseService.findById(user.getId());

            System.out.println(a);

        }
    }
}
