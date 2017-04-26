package com.homefixer.homefixer;

public class Class_Offer {

    private int oofer_id;
    private int sub_srvice_id;
    private String image;
    private String title;
    private String description;
    private int owner_id;
    private int service_type;
    private int discounts;
    private String start_time;
    private String end_time;
    private int max_customer;

    public Class_Offer(int oofer_id, int sub_srvice_id, String image, String title, String description, int owner_id, int service_type,
                       int discounts, String start_time, String end_time, int max_customer) {
        this.oofer_id = oofer_id;
        this.sub_srvice_id = sub_srvice_id;
        this.image = image;
        this.title = title;
        this.description = description;
        this.owner_id = owner_id;
        this.service_type = service_type;
        this.discounts = discounts;
        this.start_time = start_time;
        this.end_time = end_time;
        this.max_customer = max_customer;
    }

    public int getOofer_id() {
        return oofer_id;
    }

    public void setOofer_id(int oofer_id) {
        this.oofer_id = oofer_id;
    }

    public int getSub_srvice_id() {
        return sub_srvice_id;
    }

    public void setSub_srvice_id(int sub_srvice_id) {
        this.sub_srvice_id = sub_srvice_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public int getService_type() {
        return service_type;
    }

    public void setService_type(int service_type) {
        this.service_type = service_type;
    }

    public int getDiscounts() {
        return discounts;
    }

    public void setDiscounts(int discounts) {
        this.discounts = discounts;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public int getMax_customer() {
        return max_customer;
    }

    public void setMax_customer(int max_customer) {
        this.max_customer = max_customer;
    }
}
