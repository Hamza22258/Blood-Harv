package com.hamza.blood_harv;

public class BloodTypes {
    private String type, availability;

    public BloodTypes(String type, String availability) {
        this.type = type;
        this.availability = availability;
    }
    public  BloodTypes(){

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
}

