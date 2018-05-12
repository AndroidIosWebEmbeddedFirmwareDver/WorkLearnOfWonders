package com.wonders.health.venus.open.user.entity;

/**
 * Created by sunning on 16/1/4.
 */
public class HospitalListVO {

    /**
     * id : 343610b3ffbf44cba2aa024abbf4c854
     * photo : http://de.wdjky.me/healthcloud/images/123456
     * level :
     * name :
     * description :
     * count : 123
     */
    private String id;
    private String photo;
    private String level;
    private String name;
    private String description;
    private int count;

    public void setId(String id) {
        this.id = id;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public String getPhoto() {
        return photo;
    }

    public String getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getCount() {
        return count;
    }
}
