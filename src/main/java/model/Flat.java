package model;

public class Flat {

    private String phoneNumber;
    private String rooms;
    private String series;
    private int place;
    private String floor;
    private String updateDate;
    private String headText;
    private int price;
    private String createDate;


    public Flat(int price, String phoneNumbers, String rooms, String series,
                int place, String floor, String createDate, String updateDate, String headText) {

        this.price = price;
        this.phoneNumber = phoneNumbers;
        this.rooms = rooms;
        this.series = series;
        this.place = place;
        this.floor = floor;
        this.updateDate = updateDate;
        this.createDate = createDate;
        this.headText = headText;

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

    public int getPlace() {
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


    public int getPrice() {
        return price;
    }

}
