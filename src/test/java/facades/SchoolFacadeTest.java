/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import DTO.CourseDTO;
import DTO.SchoolClassDTO;
import entities.Course;
import entities.SchoolClass;
import entities.SignedUp;
import entities.Student;
import entities.Teacher;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import utils.EMF_Creator;

/**
 *
 * @author aamandajuhl
 */
public class SchoolFacadeTest {

    private static EntityManagerFactory emf;
    private static SchoolFacade facade;

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

    public SchoolFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {

        emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.TEST, EMF_Creator.Strategy.DROP_AND_CREATE);
        facade = SchoolFacade.getFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
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

        su1 = new SignedUp();
        su2 = new SignedUp(12, "2019-06-20");
        su3 = new SignedUp(7, "2018-12-15");
        su4 = new SignedUp(10, "2020-01-19");

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

        su1.setSchoolclass(sc3);
        su2.setSchoolclass(sc3);
        su3.setSchoolclass(sc1);
        su4.setSchoolclass(sc2);

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
    }

    /**
     * Test of getFacade method, of class SchoolFacade.
     */
    @Test
    public void testGetFacade() {
        System.out.println("getFacade");

        SchoolFacade expResult = facade;
        SchoolFacade result = SchoolFacade.getFacade(emf);
        assertEquals(expResult, result);
    }

    /**
     * Test of getCourseList method, of class SchoolFacade.
     */
    @Test
    public void testGetCourseList() {
        System.out.println("getCourseList");

        List<CourseDTO> courses = facade.getCourseList();
        assertEquals(2, courses.size());
        assertEquals("Programming", c1.getCourseName());

    }

    /**
     * Test of getClassListByTeacher method, of class SchoolFacade.
     */
    @Test
    public void testGetClassListByTeacher() {
        System.out.println("getClassListByTeacher");

        List<SchoolClassDTO> schoolclasses = facade.getClassListByTeacher(t2.getName());
        assertEquals(2, schoolclasses.size());

    }

}
