package se.threegorillas.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import se.threegorillas.appConfig.AppConfig;
import se.threegorillas.model.Team;
import se.threegorillas.model.User;
import se.threegorillas.model.WorkItem;
import se.threegorillas.repository.TeamRepository;
import se.threegorillas.repository.UserRepository;
import se.threegorillas.repository.WorkItemRepository;

import static org.junit.Assert.*;

/**
 * Created by joanne on 30/03/16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class DataBaseServiceTest {



    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WorkItemRepository workItemRepository;

    private User user1;
    private User user2;
    private WorkItem workItem;
    private Team team;


    @Before
    public void setup(){
        user1 = new User("user1", "fredrik", "k", "abc","12");
        user2 = new User("user2", "anna", "a", "123", "10");
        workItem = new WorkItem("CLEAN KITCHEN!!!");
        team = new Team("dreamteam");
    }

    @Test
    public void workitemShouldBeAddedToUser(){
        User user = userRepository.save(user1);
        WorkItem savedWorkItem = workItemRepository.save(workItem);
        user.addWorkItem(savedWorkItem);
        User userWithWorkItem = userRepository.save(user);

        assertTrue(userWithWorkItem.getWorkItems().contains(workItem));
        assertEquals(workItemRepository.findOne(savedWorkItem.getId()).getUser(), user1.getUserName());

    }


}