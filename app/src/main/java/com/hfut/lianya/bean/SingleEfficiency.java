package com.hfut.lianya.bean;

public class SingleEfficiency {
    private String packageid;
    private Float efficieny;
    private String nameid;
    private String name;

    public String getPackageid() {
        return packageid;
    }

    public void setPackageid(String packageid) {
        this.packageid = packageid;
    }

    public Float getEfficieny() {
        return efficieny;
    }

    public void setEfficieny(Float efficieny) {
        this.efficieny = efficieny;
    }

    public String getNameid() {
        return nameid;
    }

    public void setNameid(String nameid) {
        this.nameid = nameid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SingleEfficiency{" +
                "packageid='" + packageid + '\'' +
                ", efficieny=" + efficieny +
                ", nameid='" + nameid + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
