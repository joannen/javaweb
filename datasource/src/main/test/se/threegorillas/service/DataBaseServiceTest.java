package se.threegorillas.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import se.threegorillas.appconfig.AppConfig;
import se.threegorillas.model.Issue;
import se.threegorillas.model.Team;
import se.threegorillas.model.User;
import se.threegorillas.model.WorkItem;
import se.threegorillas.repository.TeamRepository;
import se.threegorillas.repository.UserRepository;
import se.threegorillas.repository.WorkItemRepository;
import se.threegorillas.status.Status;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by joanne on 30/03/16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class DataBaseServiceTest {

    @Autowired
    private DataBaseService service;

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
    private Issue issue;

    @Before
    public void setup() {
        user1 = new User("user1", "fredrik", "k", "abc", "12");
        user2 = new User("user2", "anna", "a", "123", "10");
        workItem = new WorkItem("CLEAN KITCHEN!!!");
        team = new Team("dreamteam");
        issue = new Issue("kitchen is still dirty!!!");
    }

    @After
    public void destroy() {
        userRepository.findAll().forEach(user -> userRepository.delete(user));
        workItemRepository.findAll().forEach(workItem -> workItemRepository.delete(workItem));
        teamRepository.findAll().forEach(team -> teamRepository.delete(team));
    }

    @Test
    public void workItemShouldBeAddedToUser() {
        User user = service.saveUser(user1);
        WorkItem savedWorkItem = service.saveWorkItem(workItem);
        user.addWorkItem(savedWorkItem);
        User userWithWorkItem = service.saveUser(user);

        assertNotNull(service.findUserByUsername(user.getUserName()));
        User retrieved = service.findUserByUsername(userWithWorkItem.getUserName());

        assertTrue(retrieved.getWorkItems().size() > 0);
    }

    @Test
    public void userShouldBeAddedToTeam() {
        Team savedTeam = service.saveTeam(team);
        assertNotNull(savedTeam);
        User savedUser = service.saveUser(user1);

        savedTeam.addUser(savedUser);
        service.saveUser(savedUser);
        Team savedWithUser = service.saveTeam(savedTeam);

        assertNotNull(savedWithUser);
        Team teamById = service.findByTeamName(savedWithUser.getTeamName());
        assertTrue(teamById.getUsers().size() > 0);
    }

    @Test
    public void workItemStatusShouldBeChanged() {
        WorkItem savedWorkItem = service.saveWorkItem(workItem);
        assertTrue(savedWorkItem.getStatus().equals(Status.UNSTARTED));
        user1.addWorkItem(savedWorkItem);
        service.saveWorkItem(savedWorkItem);
        service.saveUser(user1);
        assertTrue(service.findWorkItemById(savedWorkItem.getId()).getStatus().equals(Status.STARTED));
    }

    @Test
    public void inActivatedUserShouldMakeWorkItemsUnstarted() {
        User savedUser = service.saveUser(user1);
        assertTrue(service.findUserById(savedUser.getId()).getUserStatus().equals(Status.ACTIVE));
        WorkItem savedWorkItem = service.saveWorkItem(workItem);
        savedUser.addWorkItem(savedWorkItem);
        service.saveWorkItem(savedWorkItem);
        service.saveUser(savedUser);

        assertTrue(service.findUserByUsername(savedUser.getUserName()).getWorkItems().contains(savedWorkItem));
        User inactiveUser = service.findUserByUsername(savedUser.getUserName());
        inactiveUser.setStatusInactive();
        service.saveUser(inactiveUser);
        assertTrue(service.findWorkItemById(savedWorkItem.getId()).getStatus().equals(Status.UNSTARTED));
    }

    @Test
    public void issueShouldBeAddedToWorkItem() {
        WorkItem savedWorkItem = service.saveWorkItem(workItem);
        assertNull(savedWorkItem.getIssue());
        WorkItem retrievedItem = service.findWorkItemById(savedWorkItem.getId());
        retrievedItem.setIssue(issue);
        WorkItem workItemWithIssue = service.saveWorkItem(retrievedItem);

        assertEquals(issue, service.findWorkItemById(workItemWithIssue.getId()).getIssue());
    }

    @Test
    public void getWorkItemsWithIssueForUser() {
        User savedUser = service.saveUser(user2);
        savedUser.addWorkItem(workItem);
        workItem.setIssue(issue);
        service.saveWorkItem(workItem);
        service.saveUser(savedUser);

        assertTrue(service.findUserByUsername(savedUser.getUserName()).getWorkItems().contains(workItem));
        Collection<WorkItem> workItems = service.findUserByUsername(savedUser.getUserName()).getWorkItems();
        Collection<Issue> issues = new ArrayList<>();

        for (WorkItem w : workItems) {
            if (w.getIssue() != null) {
                issues.add(w.getIssue());
            }
        }
        assertTrue(issues.contains(issue));
    }

}