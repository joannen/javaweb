package se.threegorillas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.threegorillas.exception.EntityNotFoundException;
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

    public User findUserByUsername(String username){ return userRepository.findByUserName(username);}

    //WorkItem
    public WorkItem saveWorkItem(WorkItem workItem) {
        return workItemRepository.save(workItem);
    }

    public WorkItem findWorkItemById(Long id) {
        return workItemRepository.findOne(id);
    }

    public boolean workItemExists(WorkItem workItem) {
        return workItemRepository.findOne(workItem.getId()) != null;
    }

    public Collection<WorkItem> getAllWorkItems() {
        return workItemRepository.findAll();
    }

    public void deleteWorkItem(Long id) {
        workItemRepository.delete(id);
    }

    //Team
    public Team saveTeam(Team team){ return teamRepository.save(team);}

    public Team findTeamById(Long id){ return teamRepository.findOne(id); }

    public Collection<Team> getAllTeams(){return teamRepository.findAll();}

    public void removeTeam(Long id){
        teamRepository.delete(id);
    }

    public boolean teamExists(Team team){ return teamRepository.findOne(team.getId()) != null;}

    public Team findByTeamName(String teamName) {return teamRepository.findByTeamName(teamName);}


    public User findUserByUserNumber(String usernumber) {
        User user = userRepository.findByUserNumber(usernumber);
        if(user==null){
            throw new EntityNotFoundException("User with userNumber: " +usernumber +" not found");
        }
        return user;
    }

    public Collection<User> searchForUser(String search) {
        return userRepository.findByFirstNameLike(search);
    }
}
