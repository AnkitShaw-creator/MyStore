package Items;

import java.util.HashMap;
import java.util.Map;

public class items {

    String description, price, quantity, title,id;
    public items(){}

    public items(String description, String price, String quantity, String title, String id){
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.title = title;
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();
        return map;
    }

    public String HashCode(){
        return null;
    }

}
