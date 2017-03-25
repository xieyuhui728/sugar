package com.web.mvc.admin.model;

import java.io.Serializable;

/**
 * Created by xieyuhui on 17-3-25.
 */
public class Movie implements Serializable {
    private int id;
    private String cName;
    private String eName;
    private String oName;
    private String introduce;
    private int order;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }

    public String getoName() {
        return oName;
    }

    public void setoName(String oName) {
        this.oName = oName;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", cName='" + cName + '\'' +
                ", eName='" + eName + '\'' +
                ", oName='" + oName + '\'' +
                ", introduce='" + introduce + '\'' +
                ", order=" + order +
                '}';
    }
}
