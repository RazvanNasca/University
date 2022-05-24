package model;

/**
 * 
 */
public class Product extends Entity<String> {

    /**
     * Default constructor
     */
    public Product() {
    }

    public Product(String name, double price, int quantity)
    {
        this.setID(name);
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private double price;

    /**
     * 
     */
    private int quantity;



    /**
     * @return
     */
    public String getName() {
        // TODO implement here
        return this.name;
    }

    /**
     * @return
     */
    public double getPrice() {
        // TODO implement here
        return this.price;
    }

    /**
     * @return
     */
    public int getQuantity() {
        // TODO implement here
        return this.quantity;
    }

    /**
     * @param n 
     * @return
     */
    public void setName(String n) {
        // TODO implement here
        this.name = n;
    }

    /**
     * @param p 
     * @return
     */
    public void setPrice(double p) {
        // TODO implement here
        this.price = p;
    }

    /**
     * @param q 
     * @return
     */
    public void setQuantity(int q) {
        // TODO implement here
        this.quantity = q;
    }

}