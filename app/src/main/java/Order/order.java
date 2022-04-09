package Order;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class order {
    private static final String TAG = "order";
    public String name,quantity,rate,order_id, time;

    public order() {}

    public order(String name, String quantity, String rate, String order_id, String time) {
        this.name = name;
        this.quantity = quantity;
        this.rate = rate;
        this.order_id = order_id;
        this.time = time;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Map<String, Object > toMap(){
        Map<String, Object > m = new HashMap<>();
        m.put("name", this.name);
        m.put("order_id", this.order_id);
        m.put("quantity", this.quantity);
        m.put("rate", this.rate);
        String date = new Date().toString();
        m.put("time", date);
        return m;
    }
}
