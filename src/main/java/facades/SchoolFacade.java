/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import DTO.CourseDTO;
import DTO.SchoolClassDTO;
import DTO.SignedUpDTO;
import DTO.StudentDTO;
import entities.Course;
import entities.SchoolClass;
import entities.SignedUp;
import entities.Student;
import entities.Teacher;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.WebApplicationException;

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

    public List<SchoolClassDTO> getSignedUpClassesByStudent(String student_name) {
        EntityManager em = getEntityManager();

        List<SchoolClassDTO> schoolclassesDTO = new ArrayList();

        TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s WHERE s.name = :name", Student.class);
        List<Student> students = query.setParameter("name", student_name).getResultList();

        if (students.size() > 0) {
            Student student = students.get(0);

            for (SignedUp s : student.getSigneduplist()) {

                schoolclassesDTO.add(new SchoolClassDTO(s.getSchoolclass()));

            }
        } else {
            throw new WebApplicationException("The student doesn't exsist", 400);
        }
        return schoolclassesDTO;

    }

    public List<StudentDTO> getStudent(String student_name) {

        EntityManager em = getEntityManager();

        List<StudentDTO> studentsDTO = new ArrayList();

        TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s WHERE s.name = :name", Student.class);
        List<Student> students = query.setParameter("name", student_name).getResultList();

        for (Student student : students) {
            studentsDTO.add(new StudentDTO(student));
        }

        return studentsDTO;
    }

    public void populateDB() {

        EntityManager em = emf.createEntityManager();

        Course c1 = new Course("Programming", "Fundamentel and object oriented programming");
        Course c2 = new Course("System Development", "Analyse, design and knowledge about IT-tools");

        SchoolClass sc1 = new SchoolClass("fall2018", 30, c1);
        SchoolClass sc2 = new SchoolClass("summer2019", 20, c1);
        SchoolClass sc3 = new SchoolClass("fall2019", 20, c2);

        Teacher t1 = new Teacher("Tine");
        Teacher t2 = new Teacher("Tue");

        Student s1 = new Student("Benjamin", "benja@gmail.com");
        Student s2 = new Student("Amalie", "amalie@gmail.com");
        Student s3 = new Student("Amanda", "amanda@gmail.com");

        SignedUp su1 = new SignedUp(null, null);
        SignedUp su2 = new SignedUp("12", "2019-06-20");
        SignedUp su3 = new SignedUp("7", "2018-12-15");
        SignedUp su4 = new SignedUp("10", "2020-01-19");

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

}
