package se.threegorillas;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import se.threegorillas.model.User;
import se.threegorillas.repository.UserRepository;

public final class Runner {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.scan("se.threegorillas");
            context.refresh();

            UserRepository repo = context.getBean(UserRepository.class);

            User user = new User("fredrik", "Fredrik", "Hollinger", "watawt", "132345");

            User saved = repo.save(user);

            System.out.println(saved == null);

        }
    }
}
