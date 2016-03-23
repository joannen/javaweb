package se.threegorillas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public User findById(Long id){
        return userRepository.findOne(id);
    }

    public Collection<User> getAllUsers(){
        return userRepository.findAll();
    }

    public WorkItem save(WorkItem workItem) {
        return workItemRepository.save(workItem);
    }
}
