package se.threegorillas.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import se.threegorillas.appconfig.AppConfig;
import se.threegorillas.model.Team;
import se.threegorillas.model.User;
import se.threegorillas.model.WorkItem;
import se.threegorillas.status.Status;

import static org.junit.Assert.*;

/**
 * Created by joanne on 30/03/16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class DataBaseServiceTest {

    @Autowired
    private DataBaseService service;

//    @Autowired
//    private TeamRepository teamRepository;
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private WorkItemRepository workItemRepository;

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
        User user = service.saveUser(user1);
        WorkItem savedWorkItem = service.saveWorkItem(workItem);
        user.addWorkItem(savedWorkItem);
        User userWithWorkItem = service.saveUser(user);

        assertNotNull(service.findUserById(user.getId()));
        User retrieved = service.findUserById(userWithWorkItem.getId());

        assertTrue(retrieved.getWorkItems().size() > 0);
    }

    @Test
    @Transactional
    public void userShouldBeAddedToTeam() {
        Team saved = service.saveTeam(team);
        assertNotNull(saved);

        User savedUser = service.saveUser(user1);

        saved.addUser(savedUser);
        Team savedWithUser = service.saveTeam(saved);

        assertNotNull(savedWithUser);

        savedWithUser.getUsers().forEach(System.out::println);

        assertTrue(savedWithUser.getUsers().size() > 0);
    }

    @Test
    @Transactional
    public void workItemStatusShouldBeChanged(){

        WorkItem savedWorkItem = service.saveWorkItem(workItem);
        assertTrue(savedWorkItem.getStatus().equals(Status.UNSTARTED));
        user1.addWorkItem(savedWorkItem);
        service.saveUser(user1);
        assertTrue(service.findWorkItemById(savedWorkItem.getId()).getStatus().equals(Status.STARTED));
    }

    @Test
    @Transactional
    public void inActivatedUserShouldMakeWorkItemsUnstarted(){

        User savedUser = service.saveUser(user1);
        assertTrue(service.findUserById(savedUser.getId()).getUserStatus().equals(Status.ACTIVE));
        WorkItem savedWorkItem = service.saveWorkItem(workItem);
        savedUser.addWorkItem(savedWorkItem);
        service.saveUser(savedUser);

        assertTrue(service.findUserById(savedUser.getId()).getWorkItems().contains(savedWorkItem));
        service.findUserById(savedUser.getId()).setStatusInactive();
        service.saveUser(savedUser);
        assertTrue(service.findWorkItemById(savedWorkItem.getId()).getStatus().equals(Status.UNSTARTED));
    }

}