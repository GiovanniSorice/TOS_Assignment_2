////////////////////////////////////////////////////////////////////
// [GIOVANNI] [SORICE] [1144588]
////////////////////////////////////////////////////////////////////

package it.unipd.tos.model;
public class MenuItem {
    private final ItemType itemType;
    private String name;
    private double price;
    
    /**
     * Sole contructor
     * @param itemType
     * @param name
     * @param price
     */
    public MenuItem(ItemType itemType, String name, double price) {
        this.itemType = itemType;
        this.name = name;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public ItemType getItemType() {
        return itemType;
}
}
