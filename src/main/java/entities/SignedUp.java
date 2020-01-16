/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

/**
 *
 * @author aamandajuhl
 */
@Entity
@NamedQuery(name = "SignedUp.deleteAllRows", query = "DELETE from SignedUp")
public class SignedUp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String grade;
    private String passedDate;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private SchoolClass schoolclass;

    public SignedUp(String grade, String passedDate) {
        this.grade = grade;
        this.passedDate = passedDate;
    }

    public SignedUp() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getPassedDate() {
        return passedDate;
    }

    public void setPassedDate(String passedDate) {
        this.passedDate = passedDate;
    }

    public SchoolClass getSchoolclass() {
        return schoolclass;
    }

    public void setSchoolclass(SchoolClass schoolclass) {
        this.schoolclass = schoolclass;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.grade);
        hash = 47 * hash + Objects.hashCode(this.passedDate);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SignedUp other = (SignedUp) obj;
        if (!Objects.equals(this.grade, other.grade)) {
            return false;
        }
        if (!Objects.equals(this.passedDate, other.passedDate)) {
            return false;
        }
        return true;
    }

    

}
