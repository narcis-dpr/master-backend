package com.howtodojava.rest.models;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

@Entity

public class Employee {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "OWNER", nullable = false)
    private String owner;
    @NotBlank
    @Length(min = 2, max = 255)
    @Column(name = "FIRSTNAME")
    private String firstName;
    @NotBlank
    @Length(min = 2, max = 255)
    @Column(name = "LASTNAME")
    private String lastName;
    //@Pattern(regexp = ".+@.+\\.[a-z]+")
   // @NotBlank
    @Length(min = 2, max = 255)
    @Column(name = "PHONENUMBER")
    private String phoneNumber;
    //dastrassiHa :
    @Column(name = "SELLRECORD")
    private Integer sellRecord;
    @Column(name = "STARS")
    private double stars;
    @Column(name = "LABELE")
    private Integer lable;
    @Column(name = "USERNAME")
    private Integer userName;
    @Column(name = "SHABA")
    private Integer shaba;
    @Column(name = "CREDIT")
    private Integer credit;
    @Transient
    private String imageBase64;
    private String imageAddress;



   // @NotBlank
    //@Column(name = "STARS", nullable = false)
  //  private String stars;

    public Employee() {
    }

    public Employee(String owner, String firstName, String lastName, String phoneNumber,Integer sellRecord,
                    double stars,Integer lable,Integer userName,Integer shaba) {
        this.owner = owner;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.sellRecord = sellRecord;
        this.stars = stars;
        this.lable = lable;
        this.userName = userName;
        this.shaba = shaba;

    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getSellRecord() {
        return sellRecord;
    }

    public void setSellRecord(Integer sellRecord) {

        this.sellRecord = sellRecord + 1;
    }

    public double getStars() {
        return stars;
    }

    public void setStars(double stars) {
     //   Double holder = (stars + this.stars)/getSellRecord();

        this.stars = stars;
    }

    public Integer getLable() {
        return lable;
    }

    public void setLable(Integer lable) {
        this.lable = lable;
    }

    public Integer getUserName() {
        return userName;
    }

    public void setUserName(Integer userName) {
        this.userName = userName;
    }

    public Integer getShaba() {
        return shaba;
    }

    public void setShaba(Integer shaba) {
        this.shaba = shaba;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public String getImageAddress() {
        return imageAddress;
    }

    public void setImageAddress(String imageAddress) {
        this.imageAddress = imageAddress;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
      //  int holder = credit + getCredit();
        this.credit = credit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(owner, employee.owner) &&
                Objects.equals(firstName, employee.firstName) &&
                Objects.equals(lastName, employee.lastName) &&
                Objects.equals(phoneNumber, employee.phoneNumber) &&
                Objects.equals(sellRecord, employee.sellRecord) &&
                Objects.equals(stars, employee.stars) &&
                Objects.equals(lable, employee.lable) &&
                Objects.equals(userName, employee.userName) &&
                Objects.equals(shaba, employee.shaba) &&
                Objects.equals(credit,employee.credit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner, firstName, lastName, phoneNumber, sellRecord, stars, lable, userName, shaba);
    }

    public void saveImage() throws IOException {
        // Note preferred way of declaring an array variable
        imageAddress = newImageUrl();
        String baseUrl = "/usr/local/var/www/";
        byte[] data = Base64.decodeBase64(imageBase64);
        try (OutputStream stream = new FileOutputStream(baseUrl + imageAddress)) {
            stream.write(data);

        }
    }

    private String newImageUrl() {
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        return String.format("/%s.jpg", randomUUIDString);

    }


    public Object serialize(){
        return new HashMap<String, Object>() {{
            put("owner", owner);
            put("firstName", firstName);
            put("lastName", lastName);
            put("phoneNumber", phoneNumber);
            put("sellRecord", sellRecord);
            put("stars", stars);
            put("imageAddress", imageAddress);
            put("lable",lable);
            put("userName",userName);
            put("shaba",shaba);
            put("credit",credit);

        }};


    }

    @Override
    public String toString() {
        return "Employee{" +
                "owner=" + owner +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", sellRecord=" + sellRecord +
                ", stars=" + stars +
                ", lable=" + lable +
                ", userName='" + userName + '\'' +
                ", shaba=" + shaba +
                ", imageBase64='" + imageBase64 + '\'' +
                ", imageAddress='" + imageAddress + '\'' +
                ", credit='" + credit + '\'' +
                '}';
    }
}
