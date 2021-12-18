package com.hamza.blood_harv;

public class Profile {
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    private String uid, name, bloodType, accountType, location, age, gender, dp, active;
    public Profile(){

    }
    public Profile(String uid, String name, String bloodType, String accountType, String location, String age, String gender, String dp, String active) {
        this.uid= uid;
        this.name = name;
        this.bloodType = bloodType;
        this.accountType = accountType;
        this.location = location;
        this.age = age;
        this.gender = gender;
        this.dp = dp;
        this.active = active;
    }

    public Profile(String name, String accountType, String location, String dp) {
        this.name = name;
        this.accountType = accountType;
        this.location = location;
        this.dp = dp;
        this.bloodType="";
        this.age="";
        this.active="";
        this.gender="";
    }


    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
