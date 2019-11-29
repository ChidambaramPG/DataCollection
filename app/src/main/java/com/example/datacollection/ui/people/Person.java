package com.example.datacollection.ui.people;

import java.io.Serializable;

public class Person implements Serializable {
    String name,address,num,altNum,qualification,experience,jobType,uid,url;

    public Person(){

    }

    public Person(String name, String address, String num, String altNum, String qualification, String experience,String jobType) {
        this.name = name;
        this.address = address;
        this.num = num;
        this.altNum = altNum;
        this.qualification = qualification;
        this.experience = experience;
        this.jobType = jobType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getAltNum() {
        return altNum;
    }

    public void setAltNum(String altNum) {
        this.altNum = altNum;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }
}
