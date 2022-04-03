package Order;

import java.util.HashMap;
import java.util.Map;

public class order {

    public String name,quantity,rate,order_id;

    public order() {}

    public order(String name, String quantity, String rate, String order_id) {
        this.name = name;
        this.quantity = quantity;
        this.rate = rate;
        this.order_id = order_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public Map<String, Object > toMap(){
        Map<String, Object > m = new HashMap<>();
        m.put("name", this.name);
        m.put("order_id", this.order_id);
        m.put("quantity", this.quantity);
        m.put("rate", this.rate);

        return m;
    }
}
