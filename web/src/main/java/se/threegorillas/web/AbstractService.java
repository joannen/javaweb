package se.threegorillas.web;

import se.threegorillas.model.Team;
import se.threegorillas.model.User;
import se.threegorillas.model.WorkItem;
import se.threegorillas.model.WebTeam;
import se.threegorillas.model.WebUser;
import se.threegorillas.model.WebWorkItem;
import se.threegorillas.service.DataBaseService;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * Created by TheYellowBelliedMarmot on 2016-03-23.
 */
public abstract class AbstractService {

    @Context
    protected ServletContext context;

    @Context
    protected UriInfo uriInfo;

    protected DataBaseService service;

    @PostConstruct
    public void setupService() {
        service = (DataBaseService) context.getAttribute("database");
    }

    protected WebUser toWebUser(User user){

        WebUser webUser = new WebUser(user.getId(), user.getFirstName(), user.getLastName(),
                user.getUserName(), user.getPassword(), user.getUserNumber(), user.getUserStatus());
        return webUser;

    }

    protected WebWorkItem toWebWorkItem(WorkItem workItem){

        boolean hasIssue = workItem.getIssue() != null;

        WebWorkItem webWorkItem = new WebWorkItem.Builder(workItem.getId(), workItem.getDescription())
                                    .withAssignedUserName(workItem.getAssignedUsername())
                                    .withStatus(workItem.getStatus())
                                    .withIssue((hasIssue) ? workItem.getIssue().getIssueDescription() : null)
                                    .build();
        return webWorkItem;
    }

    protected WebTeam toWebTeam(Team t) {
        return new WebTeam(t.getId(), t.getTeamName(), t.getTeamStatus());
    }

}
