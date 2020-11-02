package com.howtodojava.rest.models;


import org.apache.commons.codec.binary.Base64;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import static org.apache.commons.lang3.time.DateUtils.toCalendar;


@Entity
@Table(name = "food")
public class Food {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "ID", nullable = false)
    private String id;
    @Column(name = "OWNER", nullable = false)
    private String owner;
    @NotBlank
    @Length(min = 2, max = 255)
    @Column(name = "FOODNAME", nullable = false)
    private String foodName;
    @Column(name = "FOODNUMBER", nullable = false)
    private Integer foodNumber;   /// THE PRICE!
    @Column(name = "DISH", nullable = false)
    private boolean dish;
    @Column(name = "SPOON", nullable = false)
    private boolean spoon;
    @Column(name = "FORK", nullable = false)
    private boolean fork;
    @Column(name = "HOTCOLD", nullable = false)
    private boolean hotcold;
    @Column(name = "EXPIRE")
    private Integer expire;
    @Column(name = "PORSNUMBER", nullable = false)
    private Integer porsNumber;
    @Column(name = "INFOS")
    private String infos;
    @Column(name = "FOODKIND")
    private String foodKind;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Transient
    private String imageBase64;
    private String imageAddress;
    private Double lat;
    private Double lng;
    private Date expireTime;






    public Food() {
    }

    public Food(String id, String owner, String foodName, Integer foodNumber, boolean dish, boolean spoon) {
        this.id = id;
        this.owner = owner;
        this.foodName = foodName;
        this.foodNumber = foodNumber;
        this.dish = dish;
        this.spoon = spoon;

    }


//Getter


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public Integer getFoodNumber() {
        return foodNumber;
    }

    public void setFoodNumber(Integer foodNumber) {
        this.foodNumber = foodNumber;
    }
    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }
//Setter

    public boolean isDish() {
        return dish;
    }

    public void setDish(boolean dish) {
        this.dish = dish;
    }

    public boolean isSpoon() {
        return spoon;
    }

    public void setSpoon(boolean spoon) {
        this.spoon = spoon;
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
    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Integer getExpire() {
        return expire;
    }

    public void setExpireOne(Date expireTime) {
     Calendar c = toCalendar(expireTime);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
        c.add(Calendar.DATE,expire);
        System.out.println("99999999999999999     " + dateFormat.format(c.getTime()));
        setExpireTime(c.getTime());
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
//    public void setExpire(Date expireTime,Integer expire) {
//        Calendar c = toCalendar(expireTime);
//        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        c.add(Calendar.DATE,expire);
//        System.out.println(dateFormat.format("99999999999999999     " + c.getTime()));
//        expireTime =c.getTime();
//        this.expire = expire;
//    }

    public boolean isFork() {
        return fork;
    }

    public void setFork(boolean fork) {
        this.fork = fork;
    }

    public boolean isHotcold() {
        return hotcold;
    }

    public void setHotcold(boolean hotcold) {
        this.hotcold = hotcold;
    }

    public Integer getPorsNumber() {
        return porsNumber;
    }

    public void setPorsNumber(Integer porsNumber) {
        this.porsNumber = porsNumber;
    }

    public String getInfos() {
        return infos;
    }

    public void setInfos(String infos) {
        this.infos = infos;
    }

    public String getFoodKind() {
        return foodKind;
    }

    public void setFoodKind(String foodKind) {
        this.foodKind = foodKind;
    }

    public void saveImage() throws IOException {
        // Note preferred way of declaring an array variable
        imageAddress = newImageUrl();
        String baseUrl = "/usr/local/var/www";
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
      put("id", id);
      put("owner", owner);
      put("foodName", foodName);
      put("foodNumber", foodNumber);
      put("dish", dish);
      put("spoon", spoon);
      put("imageAddress", imageAddress);
      put("lat",lat);
      put("lng",lng);
    }};


}

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", owner=" + owner +
                ", foodName='" + foodName + '\'' +
                ", foodNumber=" + foodNumber +
                ", dish=" + dish +
                ", spoon=" + spoon +
                ", imageBase64='" + imageBase64 + '\'' +
                ", imageAddress='" + imageAddress + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                '}';
    }

}
