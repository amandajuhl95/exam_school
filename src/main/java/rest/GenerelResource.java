/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import DTO.CourseDTO;
import DTO.SchoolClassDTO;
import DTO.StudentDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.SchoolFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import utils.EMF_Creator;

/**
 * REST Web Service
 *
 * @author aamandajuhl
 */
@Path("generel")
public class GenerelResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);
    private static final SchoolFacade SF = SchoolFacade.getFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of InfoResource
     */
    public GenerelResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getTest() {
        return "{\"msg\":\"You are now connected\"}";
    }

    @GET
    @Path("/courselist")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CourseDTO> getCourseList() {

        List<CourseDTO> courses = SF.getCourseList();
        return courses;

    }

    @GET
    @Path("/teacher/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SchoolClassDTO> getClassListByTeacher(@PathParam("name") String name) {

        List<SchoolClassDTO> classes = SF.getClassListByTeacher(name);
        return classes;

    }

    @GET
    @Path("/student/class/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SchoolClassDTO> getClassAndCourseForStudents(@PathParam("name") String name) {

        List<SchoolClassDTO> classes = SF.getSignedUpClassesByStudent(name);
        return classes;
    }

    @GET
    @Path("/student/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<StudentDTO> getStudent(@PathParam("name") String name) {

        List<StudentDTO> students = SF.getStudent(name);
        return students;
    }

    @GET
    @Path("/populate")
    @Produces(MediaType.APPLICATION_JSON)
    public String populateDB() {
        //SF.populateDB();
        return "{\"msg\":\"Your database how been populated\"}";
    }

}
