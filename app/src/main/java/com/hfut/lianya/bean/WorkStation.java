package com.hfut.lianya.bean;

/**
 * @author huqiwei
 */

public class WorkStation {
    private Integer id;
    private String number;
    private String name;
    private String groupby;
    private String department;

    public WorkStation(Integer id, String number, String name, String groupby, String department) {
        this.id = id;
        this.number = number;
        this.name = name;
        this.groupby = groupby;
        this.department = department;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupby() {
        return groupby;
    }

    public void setGroupby(String groupby) {
        this.groupby = groupby;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "WorkerInfo{" +
                "id=" + id +
                ", number=" + number +
                ", name='" + name + '\'' +
                ", groupby='" + groupby + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}
