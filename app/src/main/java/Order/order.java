package Order;

public class order {
    public String itemId, order_item, quantity, total_amount, address;

    public order() {
    }

    public order(String itemId, String order_item, String quantity, String total_amount, String address) {
        this.itemId = itemId;
        this.order_item = order_item;
        this.quantity = quantity;
        this.total_amount = total_amount;
        this.address = address;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getOrder_item() {
        return order_item;
    }

    public void setOrder_item(String order_item) {
        this.order_item = order_item;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
