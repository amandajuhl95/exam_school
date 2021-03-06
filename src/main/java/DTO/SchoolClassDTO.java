/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import entities.SchoolClass;
import entities.Teacher;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author aamandajuhl
 */
public class SchoolClassDTO implements Serializable {

    private long id;
    private String semester;
    private int maxNumberStudent;
    private String courseName;
    private Set<TeacherDTO> teachers = new HashSet();

    public SchoolClassDTO(SchoolClass schoolclass) {
        this.id = schoolclass.getId();
        this.semester = schoolclass.getSemester();
        this.maxNumberStudent = schoolclass.getMaxNumberStudent();
        this.courseName = schoolclass.getCourse().getCourseName();

        for (Teacher teacher : schoolclass.getTeachers()) {
            this.teachers.add(new TeacherDTO(teacher));
        }
    }

    public SchoolClassDTO(String semester, String maxNumberStudent, String courseName) {
        this.semester = semester;
        this.maxNumberStudent = Integer.parseInt(maxNumberStudent);
        this.courseName = courseName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getSemester() {
        return semester;
    }

    public int getMaxNumberStudent() {
        return maxNumberStudent;
    }

    public Set<TeacherDTO> getTeachers() {
        return teachers;
    }

}
