package USERS;

import java.util.HashMap;
import java.util.Map;

public class Users {

    String address, email, image, name, phone;

    public Users(){}

    public Users(String address, String email, String image, String name, String phone) {
        this.address = address;
        this.email = email;
        this.image = image;
        this.name = name;
        this.phone = phone;
    }

    public Users(String address, String email, String name, String phone) {
        this.address = address;
        this.email = email;
        this.name = name;
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Map<String, String> toMap(){
        Map<String, String> newMap = new HashMap<>();
        newMap.put("image", image);
        newMap.put("name", name);
        newMap.put("address",address);
        newMap.put("phone",phone);
        newMap.put("email",email);

        return newMap;
    }

}
