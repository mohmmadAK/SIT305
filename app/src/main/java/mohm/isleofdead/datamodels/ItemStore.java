package mohm.isleofdead.datamodels;

public class ItemStore {

    public String id;
    public String itemTitle;
    public String category;
    public double price;

    public ItemStore() { }

    public ItemStore(String id, String itemTitle, String category, double price) {
        this.id = id;
        this.itemTitle = itemTitle;
        this.category = category;
        this.price = price;
    }
}
