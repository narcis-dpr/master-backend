package com.howtodojava.rest.models;


import org.apache.commons.codec.binary.Base64;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.UUID;

@Entity
@Table(name = "employeepro")
public class EmployeePro{
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "ID", nullable = false)
    private String id ;
    @Column(name = "OWNER",nullable = false)
    private String owner;
    @Transient
    private String image;
    @Transient
    private String image_salamat;
    @Transient
    private String image_card;


    private String imageAddress;
    private Integer AccountStage;


    public EmployeePro() {
    }

    public EmployeePro(String id, String image, String image_salamat, String owner, String image_card) {
        this.id = id;
        this.image = image;
        this.image_salamat = image_salamat;
        this.owner = owner;
        this.image_card = image_card;
    }

    public String getId() {
        return id;
    }

    public void setId(String id ) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage_salamat() {
        return image_salamat;
    }

    public void setImage_salamat(String image_salamat) {
        this.image_salamat = image_salamat;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getImage_card() {
        return image_card;
    }

    public void setImage_card(String image_card) {
        this.image_card = image_card;
    }

    public Integer getAccountStage() {
        return AccountStage;
    }

    public void setAccountStage(Integer accountStage) {
        AccountStage = accountStage;
    }

    public void saveImage() throws IOException {
        // Note preferred way of declaring an array variable
        File dir = new File("/usr/local/var/run/pro/"+ getOwner());

        if (!dir.exists()){
            dir.mkdir();
        }
        imageAddress = newImageUrl();
        String baseUrl = String.valueOf(dir);
        byte[] data = Base64.decodeBase64(image);
        try (OutputStream stream = new FileOutputStream(baseUrl + imageAddress)) {
            stream.write(data);

        }
    }

    public void saveSalamatImage() throws IOException {
        // Note preferred way of declaring an array variable
        File dir = new File("/usr/local/var/run/pro/"+ getOwner());

        if (!dir.exists()){
            dir.mkdir();
        }


        imageAddress = newImageUrl();
        String baseUrl = String.valueOf(dir);  // "/usr/local/var/www";
        byte[] data = Base64.decodeBase64(image_salamat);
        try (OutputStream stream = new FileOutputStream(baseUrl + imageAddress)) {
           stream.write(data);

        }
    }

    public void saveCardImage() throws IOException {
        File dir = new File("/usr/local/var/run/pro/"+ getOwner());

        if (!dir.exists()){
            dir.mkdir();
        }
        imageAddress = newImageUrl();
        String baseUrl = String.valueOf(dir);
        byte[] data = Base64.decodeBase64(image_card);
        try (OutputStream stream = new FileOutputStream(baseUrl + imageAddress)) {
            stream.write(data);

        }
    }

    private String newImageUrl() {
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        return String.format("/%s.jpg", randomUUIDString);

    }


    public Object serialize() {
        return new HashMap<String, Object>() {{
            put("id", id);
            put("image",image);
            put("image_salamat",image_salamat);
            put("image_card",image_card);
            put("owner",owner);
        }};
    }
    @Override
    public String toString() {
        return "EmployeePro{" +
                "id='" + id + '\'' +
                ", image='" + image + '\'' +
                ", image_salamat='" + image_salamat + '\'' +
                ", image_card='" + image_card + '\'' +
                ", owner='" + owner + '\'' +
                '}';
    }

}
