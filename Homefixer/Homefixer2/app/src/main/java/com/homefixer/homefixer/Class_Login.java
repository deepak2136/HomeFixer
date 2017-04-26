package com.homefixer.homefixer;

public class Class_Login {

    private int login_id;
    private String fname;
    private String mname;
    private String lname;
    private String gen;
    private String pic;
    private String address_line1;
    private String address_line2;
    private String area_name;
    private String city_name;
    private String state_name;
    private int pincode;
    private String mail;
    private String contact_no;
    private String account_state;
    public String user_type;

    Class_Login() {

    }

    public Class_Login(int login_id, String fname, String mname, String lname, String gen, String pic, String address_line1,
                       String address_line2, String area_name, String city_name, String state_name, int pincode, String mail,
                       String contact_no, String account_state, String user_type) {
        this.login_id = login_id;
        this.fname = fname;
        this.mname = mname;
        this.lname = lname;
        this.gen = gen;
        this.pic = pic;
        this.address_line1 = address_line1;
        this.address_line2 = address_line2;
        this.area_name = area_name;
        this.city_name = city_name;
        this.state_name = state_name;
        this.pincode = pincode;
        this.mail = mail;
        this.contact_no = contact_no;
        this.account_state = account_state;
        this.user_type = user_type;
    }

    public int getLogin_id() {
        return login_id;
    }

    public void setLogin_id(int login_id) {
        this.login_id = login_id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getGen() {
        return gen;
    }

    public void setGen(String gen) {
        this.gen = gen;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getAddress_line1() {
        return address_line1;
    }

    public void setAddress_line1(String address_line1) {
        this.address_line1 = address_line1;
    }

    public String getAddress_line2() {
        return address_line2;
    }

    public void setAddress_line2(String address_line2) {
        this.address_line2 = address_line2;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getAccount_state() {
        return account_state;
    }

    public void setAccount_state(String account_state) {
        this.account_state = account_state;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
}
