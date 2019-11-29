/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.datacollection.ui.work;

import java.io.Serializable;

public class Work implements Serializable {
    String totPrice,totArea,totTime,totWorkers,costPerHr,costPerSqFt,projName,desc,rooms,floors,bathrooms,type,balcony,uid;


    public Work() {
    }

    public Work(String totPrice, String totArea, String totTime, String totWorkers, String costPerHr, String costPerSqFt, String projName, String desc, String rooms, String floors, String bathrooms, String type, String balcony) {
        this.totPrice = totPrice;
        this.totArea = totArea;
        this.totTime = totTime;
        this.totWorkers = totWorkers;
        this.costPerHr = costPerHr;
        this.costPerSqFt = costPerSqFt;
        this.projName = projName;
        this.desc = desc;
        this.rooms = rooms;
        this.floors = floors;
        this.bathrooms = bathrooms;
        this.type = type;
        this.balcony = balcony;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTotPrice() {
        return totPrice;
    }

    public void setTotPrice(String totPrice) {
        this.totPrice = totPrice;
    }

    public String getTotArea() {
        return totArea;
    }

    public void setTotArea(String totArea) {
        this.totArea = totArea;
    }

    public String getTotTime() {
        return totTime;
    }

    public void setTotTime(String totTime) {
        this.totTime = totTime;
    }

    public String getTotWorkers() {
        return totWorkers;
    }

    public void setTotWorkers(String totWorkers) {
        this.totWorkers = totWorkers;
    }

    public String getCostPerHr() {
        return costPerHr;
    }

    public void setCostPerHr(String costPerHr) {
        this.costPerHr = costPerHr;
    }

    public String getCostPerSqFt() {
        return costPerSqFt;
    }

    public void setCostPerSqFt(String costPerSqFt) {
        this.costPerSqFt = costPerSqFt;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRooms() {
        return rooms;
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
    }

    public String getFloors() {
        return floors;
    }

    public void setFloors(String floors) {
        this.floors = floors;
    }

    public String getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(String bathrooms) {
        this.bathrooms = bathrooms;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBalcony() {
        return balcony;
    }

    public void setBalcony(String balcony) {
        this.balcony = balcony;
    }
}
