package br.ufrn.petclinic.models;

import android.net.Uri;

public class Pet {



    private String id;
    private String name;

    private String type;

    private String appointmentDate;
    private String picturePath;


    public Pet(String id, String name, String appointmentDate, String picture) {
        this.id = id;
        this.name = name;
        this.appointmentDate = appointmentDate;
        this.picturePath = picture;
    }

    public Pet() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

}
