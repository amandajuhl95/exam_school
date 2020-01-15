/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import DTO.CourseDTO;
import DTO.SchoolClassDTO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

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
    
    public List<CourseDTO> getCourseAndClassList()
    {
        return null;
        
    }

}
