/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import entities.Course;
import entities.SchoolClass;
import entities.SignedUp;
import entities.Student;
import entities.Teacher;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

/**
 *
 * @author aamandajuhl
 */
public class GenerelResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    private Course c1;
    private Course c2;

    private SchoolClass sc1;
    private SchoolClass sc2;
    private SchoolClass sc3;

    private Teacher t1;
    private Teacher t2;

    private Student s1;
    private Student s2;
    private Student s3;

    private SignedUp su1;
    private SignedUp su2;
    private SignedUp su3;
    private SignedUp su4;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    public GenerelResourceTest() {
    }

    @BeforeAll
    public static void setUpClass() {

        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.TEST, EMF_Creator.Strategy.DROP_AND_CREATE);

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void tearDownClass() {

        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    @BeforeEach
    public void setUp() {

        EntityManager em = emf.createEntityManager();

        c1 = new Course("Programming", "Fundamentel and object oriented programming");
        c2 = new Course("System Development", "Analyse, design and knowledge about IT-tools");

        sc1 = new SchoolClass("fall2018", 30, c1);
        sc2 = new SchoolClass("summer2019", 20, c1);
        sc3 = new SchoolClass("fall2019", 20, c2);

        t1 = new Teacher("Tine");
        t2 = new Teacher("Tue");

        s1 = new Student("Benjamin", "benja@gmail.com");
        s2 = new Student("Amalie", "amalie@gmail.com");
        s3 = new Student("Amanda", "amanda@gmail.com");

        su1 = new SignedUp("null", "null");
        su2 = new SignedUp("12", "2019-06-20");
        su3 = new SignedUp("7", "2018-12-15");
        su4 = new SignedUp("10", "2020-01-19");

        sc1.addTeacher(t1);
        sc2.addTeacher(t2);
        sc3.addTeacher(t2);

        c1.addSchoolClass(sc1);
        c2.addSchoolClass(sc1);
        c1.addSchoolClass(sc2);
        c2.addSchoolClass(sc3);

        s1.addSignedUp(su1);
        s1.addSignedUp(su2);
        s2.addSignedUp(su4);
        s3.addSignedUp(su3);
        s3.addSignedUp(su2);

        su1.setSchoolclass(sc3);
        su2.setSchoolclass(sc1);
        su3.setSchoolclass(sc1);
        su4.setSchoolclass(sc3);
        su2.setSchoolclass(sc2);

        try {

            em.getTransaction().begin();

            em.createNamedQuery("Teacher.deleteAllRows").executeUpdate();
            em.createNamedQuery("Student.deleteAllRows").executeUpdate();
            em.createNamedQuery("SignedUp.deleteAllRows").executeUpdate();
            em.createNamedQuery("SchoolClass.deleteAllRows").executeUpdate();
            em.createNamedQuery("Course.deleteAllRows").executeUpdate();

            em.persist(s1);
            em.persist(s2);
            em.persist(s3);

            em.getTransaction().commit();

        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {

        // Remove any data after each test was run
    }

    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/generel").then().statusCode(200);
    }

    /**
     * Test of getCourseList method, of class GenerelResource.
     */
    @Test
    public void testGetCourseList() {
        System.out.println("getCourseList");

        given()
                .contentType("application/json")
                .when()
                .get("/generel/courselist").then()
                .statusCode(200)
                .assertThat()
                .body("description", hasSize(2), "courseName", hasItems("Programming", "System Development"));

    }

    /**
     * Test of getClassListByTeacher method, of class GenerelResource.
     */
    @Test
    public void testGetClassListByTeacher() {
        System.out.println("getClassListByTeacher");

        given()
                .contentType("application/json")
                .when()
                .get("/generel/teacher/" + t1.getName()).then()
                .statusCode(200)
                .assertThat()
                .body("courseName", hasSize(1), "courseName", hasItems("Programming"), "semester", hasItems("fall2018"));

    }

    /**
     * Test of getClassAndCourseForStudents method, of class GenerelResource.
     */
    @Test
    public void testGetClassAndCourseForStudents() {
        System.out.println("getClassAndCourseForStudents");

        given()
                .contentType("application/json")
                .when()
                .get("/generel/student/class/" + s1.getName()).then()
                .statusCode(200)
                .assertThat()
                .body("courseName", hasSize(2), "courseName", hasItems("Programming", "System Development"), "teachers", hasSize(2));

    }

    /**
     * Test of getStudent method, of class GenerelResource.
     */
    @Test
    public void testGetStudent() {
        System.out.println("getStudent");
        
        given()
                .contentType("application/json")
                .when()
                .get("/generel/student/" + s2.getName()).then()
                .statusCode(200)
                .assertThat()
                .body("email", hasItems("amalie@gmail.com"), "signedup", hasSize(1), "signedup.passedDate", hasSize(1));

        
    }

 

}
