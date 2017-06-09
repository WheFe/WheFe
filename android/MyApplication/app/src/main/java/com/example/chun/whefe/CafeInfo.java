package com.example.chun.whefe;

/**
 * Created by Chun on 2017-05-15.
 */

public class CafeInfo {
    String CafeName;
    String CafeAddress;
    String CafePhone;
    String CafeOpen;
    String CafeClose;
    String CafePerson;
    String CafeMaximum;
    String cafe_id;
    String cafe_intro;
    String cafe_image1;
    String cafe_image2;
    String cafe_image3;

    public CafeInfo() {
    }
    public CafeInfo(String cafe_id,String cafeName, String cafeAddress, String cafePhone, String cafeOpen, String cafeClose, String cafePerson,String cafeMaximum, String cafe_intro, String cafe_image1, String cafe_image2, String cafe_image3) {
        this.cafe_id = cafe_id;
        CafeName = cafeName;
        CafeAddress = cafeAddress;
        CafePhone = cafePhone;
        CafeOpen = cafeOpen;
        CafeClose = cafeClose;
        CafePerson = cafePerson;
        CafeMaximum = cafeMaximum;
        this.cafe_intro = cafe_intro;
        this.cafe_image1 = cafe_image1;
        this.cafe_image2 = cafe_image2;
        this.cafe_image3 = cafe_image3;
    }

    public String getCafe_id() {
        return cafe_id;
    }
    public void setCafe_id(String cafe_id) {
        this.cafe_id = cafe_id;
    }
    public String getCafePerson() {
        return CafePerson;
    }
    public void setCafePerson(String cafePerson) {
        CafePerson = cafePerson;
    }
    public String getCafeName() {
        return CafeName;
    }
    public void setCafeName(String cafeName) {
        CafeName = cafeName;
    }
    public String getCafeAddress() {
        return CafeAddress;
    }
    public void setCafeAddress(String cafeAddress) {
        CafeAddress = cafeAddress;
    }
    public String getCafePhone() {
        return CafePhone;
    }
    public void setCafePhone(String cafePhone) {
        CafePhone = cafePhone;
    }
    public String getCafeOpen() {
        return CafeOpen;
    }
    public void setCafeOpen(String cafeOpen) {
        CafeOpen = cafeOpen;
    }
    public String getCafeClose() {
        return CafeClose;
    }
    public void setCafeClose(String cafeClose) {
        CafeClose = cafeClose;
    }
    public String getCafeMaximum() {
        return CafeMaximum;
    }
    public void setCafeMaximum(String cafeMaximum) {
        CafeMaximum = cafeMaximum;
    }
    public String getCafe_intro() {
        return cafe_intro;
    }
    public void setCafe_intro(String cafe_intro) {
        this.cafe_intro = cafe_intro;
    }
    public String getCafe_image1() {
        return cafe_image1;
    }
    public void setCafe_image1(String cafe_image1) {
        this.cafe_image1 = cafe_image1;
    }
    public String getCafe_image2() {
        return cafe_image2;
    }
    public String getCafe_image3() {
        return cafe_image3;
    }
}
