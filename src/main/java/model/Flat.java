package model;

public class Flat {

    private String phoneNumber, rooms, series, place, floor, updateDate, headText, price, createDate, views;

    public Flat(String price, String phoneNumbers, String rooms, String series,
                String place, String floor, String createDate, String updateDate,String views, String headText) {

        this.price = price;
        this.phoneNumber = phoneNumbers;
        this.rooms = rooms;
        this.series = series;
        this.place = place;
        this.floor = floor;
        this.updateDate = updateDate;
        this.createDate = createDate;
        this.headText = headText;
        this.views = views;

    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getRooms() {
        return rooms;
    }

    public String getSeries() {
        return series;
    }

    public String getPlace() {
        return place;
    }

    public String getFloor() {
        return floor;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public String getHeadText() {
        return headText;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getPrice() {
        return price;
    }

    public String getViews() {
        return views;
    }



}
