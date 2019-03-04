package com.example.naveen.recyclerview.Utility;

public class ListItem {

    private String title;
    private String address;

    public ListItem(String title,String address) {
        this.title = title;
        this.address=address;
    }
    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }
}
