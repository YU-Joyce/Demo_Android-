package com.example.newone;

import java.util.Collection;

public class Contact {
    private String hoTen;
    private String soDienThoai;

    //xây dựng phương thức đối tượng
    public Contact(String hoTen, String soDienThoai) {
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
    }

    /*
    Phương thức get & set
     */
    public String getHoTen() {
        return hoTen;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    @Override
    public String toString() {
        return hoTen + " - " + soDienThoai;
    }

    /*
    Khai báo ktra xem có cùng đối tuợng hay không
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; //Trả về là true nếu cả hai cùng 1 đối tượng
        if (obj == null || getClass() != obj.getClass()) return false; //Trả về false thì không cùng đối tượng đã khai báo ở dòng 9
        Contact contact = (Contact) obj;
        //Ktra xem sdt và ho ten có bằng nhau hay không
        return soDienThoai.equals(contact.soDienThoai) && hoTen.equals(contact.hoTen);
    }

    public Collection<Object> getSdt() {

        return java.util.Collections.emptyList();
    }
}