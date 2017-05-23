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

    public CafeInfo() {
    }
    public CafeInfo(String cafeName, String cafeAddress, String cafePhone, String cafeOpen, String cafeClose, String cafePerson,String cafeMaximum) {
        CafeName = cafeName;
        CafeAddress = cafeAddress;
        CafePhone = cafePhone;
        CafeOpen = cafeOpen;
        CafeClose = cafeClose;
        CafePerson = cafePerson;
        CafeMaximum = cafeMaximum;
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
}
