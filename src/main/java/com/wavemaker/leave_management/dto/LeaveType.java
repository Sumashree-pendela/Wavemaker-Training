package com.wavemaker.leave_management.dto;

import com.wavemaker.leave_management.enums.LeaveName;

public class LeaveType {
    private int id;
    private LeaveName name;
    private int count;
    private String gender;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LeaveName getName() {
        return name;
    }

    public void setName(LeaveName name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "LeaveType{" +
                "id=" + id +
                ", name=" + name +
                ", count=" + count +
                ", gender='" + gender + '\'' +
                '}';
    }
}
