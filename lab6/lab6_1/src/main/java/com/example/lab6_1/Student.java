package com.example.lab6_1;


import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mec;

    @Column(nullable = false, unique = true)
    private int ncc;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String course;

    @Column(nullable = false)
    private String academicDegree;

    public Student() {
    }

    public Student(int ncc, String name, String course, String academicDegree) {
        this.ncc = ncc;
        this.name = name;
        this.course = course;
        this.academicDegree = academicDegree;
    }

    public int getMec() {
        return mec;
    }

    public void setMec(int mec) {
        this.mec = mec;
    }

    public int getNcc() {
        return ncc;
    }

    public void setNcc(int ncc) {
        this.ncc = ncc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getAcademicDegree() {
        return academicDegree;
    }

    public void setAcademicDegree(String academicDegree) {
        this.academicDegree = academicDegree;
    }

}
