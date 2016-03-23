package se.threegorillas.web;

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
}
