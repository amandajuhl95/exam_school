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
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author aamandajuhl
 */
public class SchoolFacade {

    private static SchoolFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private SchoolFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static SchoolFacade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new SchoolFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<CourseDTO> getCourseList() {
        EntityManager em = getEntityManager();

        List<CourseDTO> coursesDTO = new ArrayList();

        TypedQuery<Course> query = em.createQuery("SELECT c FROM Course c", Course.class);
        List<Course> courses = query.getResultList();

        for (Course course : courses) {
            coursesDTO.add(new CourseDTO(course));
        }

        return coursesDTO;

    }

    public List<SchoolClassDTO> getClassListByTeacher(String teacher_name) {

        EntityManager em = getEntityManager();

        List<SchoolClassDTO> schoolclassesDTO = new ArrayList();
        
        TypedQuery<SchoolClass> query = em.createQuery("SELECT sc FROM SchoolClass sc INNER JOIN sc.teachers t WHERE t.name = :name", SchoolClass.class);
        List<SchoolClass> schoolclasses = query.setParameter("name", teacher_name).getResultList();

        for (SchoolClass schoolclass : schoolclasses) {
            schoolclassesDTO.add(new SchoolClassDTO(schoolclass));
        }

        return schoolclassesDTO;
    }

}
