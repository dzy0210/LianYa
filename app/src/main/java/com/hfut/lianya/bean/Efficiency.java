package com.hfut.lianya.bean;

public class Efficiency {
    private String worknumber;
    private String name;
    private String areaname;
    private Float today;
    private Float yesterday;
    private Float qiantian;
    private Float threeday;

    public String getWorknumber() {
        return worknumber;
    }

    public void setWorknumber(String worknumber) {
        this.worknumber = worknumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    public Float getToday() {
        return today;
    }

    public void setToday(Float today) {
        this.today = today;
    }

    public Float getYesterday() {
        return yesterday;
    }

    public void setYesterday(Float yesterday) {
        this.yesterday = yesterday;
    }

    public Float getQiantian() {
        return qiantian;
    }

    public void setQiantian(Float qiantian) {
        this.qiantian = qiantian;
    }

    public Float getThreeday() {
        return threeday;
    }

    public void setThreeday(Float threeday) {
        this.threeday = threeday;
    }

    @Override
    public String toString() {
        return "Efficiency{" +
                "worknumber='" + worknumber + '\'' +
                ", name='" + name + '\'' +
                ", areaname='" + areaname + '\'' +
                ", today=" + today +
                ", yesterday=" + yesterday +
                ", qiantian=" + qiantian +
                ", threeday=" + threeday +
                '}';
    }
}
