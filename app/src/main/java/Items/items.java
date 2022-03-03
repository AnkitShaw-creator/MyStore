package Items;

public class items {
    private int image;
    private String title, description, price, quantity;

    public items(){}


    public items(String title, String description, String price, String quantity ) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
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
