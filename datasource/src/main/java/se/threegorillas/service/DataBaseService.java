package se.threegorillas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.threegorillas.model.Team;
import se.threegorillas.model.User;
import se.threegorillas.model.WorkItem;
import se.threegorillas.repository.TeamRepository;
import se.threegorillas.repository.UserRepository;
import se.threegorillas.repository.WorkItemRepository;

import java.util.Collection;

/**
 ;* Created by TheYellowBelliedMarmot on 2016-03-22.
 */
@Service
public class DataBaseService {

    private TeamRepository teamRepository;
    private UserRepository userRepository;
    private WorkItemRepository workItemRepository;

    @Autowired
    public DataBaseService(TeamRepository teamRepository, UserRepository userRepository, WorkItemRepository workItemRepository){
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.workItemRepository = workItemRepository;
    }

    //User
    public User saveUser(User user){
        return userRepository.save(user);
    }

    public User findUserById(Long id){
        return userRepository.findOne(id);
    }

    public Collection<User> getAllUsers(){
        return userRepository.findAll();
    }

    //WorkItem
    public WorkItem save(WorkItem workItem) {
        return workItemRepository.save(workItem);
    }

    public WorkItem findWorkItemById(Long id) {
        return workItemRepository.findOne(id);
    }

    //Team
    public Team saveTeam(Team team){ return teamRepository.save(team);}

    public Team findTeamById(Long id){ return teamRepository.findOne(id); }

    public Collection<Team> getAllTeams(){return teamRepository.findAll();}

    public boolean workItemExists(WorkItem workItem) {
        return workItemRepository.findOne(workItem.getId()) != null;
    }

    public Collection<WorkItem> getAllWorkItems() {
        return workItemRepository.findAll();
    }

    public void deleteWorkItem(Long id) {
        workItemRepository.delete(id);
    }

}
