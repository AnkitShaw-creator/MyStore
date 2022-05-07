package Order;


import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import generate_hash.ValueHash;

public class order {
    private static final String TAG = "order";
    public String address,name,payment, quantity,rate,order_id, time;

    public order() {}

    public order(String address, String name, String payment, String quantity, String rate, String time) {
        this.address = address;
        this.name = name;
        this.payment = payment;
        this.quantity = quantity;
        this.rate = rate;
        this.time = time;
    }

    public order(String name, String quantity, String rate, String order_id, String time) {
        this.name = name;
        this.quantity = quantity;
        this.rate = rate;
        this.order_id = order_id;
        this.time = time;
    }

    public order(String name, String quantity, String rate, String time) {
        this.name = name;
        this.quantity = quantity;
        this.rate = rate;
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
        if(order_id != null)
            return order_id;
        setOrder_id(null);
        return order_id;
    }
    public void setOrder_id(@Nullable String order_id) {
        if(order_id != null)
            this.order_id = order_id;
        ValueHash hash = new ValueHash(this.getName());
        this.order_id = hash.getValue();
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
        m.put("order_id",(order_id != null)?this.order_id: getOrder_id());
        m.put("quantity", this.quantity);
        m.put("rate", this.rate);
        String date = new Date().toString();
        m.put("time", date);
        return m;
    }
}
