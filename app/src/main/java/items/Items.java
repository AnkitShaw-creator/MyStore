package items;

public class Items {
    private int image;
    private String title, description, price, quantity;

    public Items(){}

    public Items(int image, String name, String description) {
        this.image = image;
        this.title = name;
        this.description = description;
    }

    public Items(int ic_icon_order, Items value) {
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public void setImage(int image) {
        this.image = image;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
