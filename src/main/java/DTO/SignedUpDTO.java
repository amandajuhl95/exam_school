/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import entities.SignedUp;
import java.io.Serializable;

/**
 *
 * @author aamandajuhl
 */
public class SignedUpDTO implements Serializable {

    private long id;
    private String grade;
    private String passedDate;
    private String semester;
    private int maxNumberStudent;

    public SignedUpDTO(SignedUp signedup) {

        this.id = signedup.getId();
        this.grade = signedup.getGrade();
        this.passedDate = signedup.getPassedDate();
        this.semester = signedup.getSchoolclass().getSemester();
        this.maxNumberStudent = signedup.getSchoolclass().getMaxNumberStudent();
    }

    public SignedUpDTO(String grade, String passedDate, String semester) {
        this.grade = grade;
        this.passedDate = passedDate;
        this.semester = semester;
    }

    public long getId() {
        return id;
    }

    public String getGrade() {
        return grade;
    }

    public String getPassedDate() {
        return passedDate;
    }

    public String getSemester() {
        return semester;
    }

    public int getMaxNumberStudent() {
        return maxNumberStudent;
    }

}
