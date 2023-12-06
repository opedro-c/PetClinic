package br.ufrn.petclinic.models;

public class Pet {

    private String name;
    private String birthday;
    private String picturePath;


    public Pet(String name, String birthday, String picture) {
        this.name = name;
        this.birthday = birthday;
        this.picturePath = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }
}
