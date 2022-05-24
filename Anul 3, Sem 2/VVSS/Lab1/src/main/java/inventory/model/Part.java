
package inventory.model;


public abstract class Part {

    // Declare fields
    private int partId;
    private String name;
    private double price;
    private int unitsStock;
    private int minUnits;
    private int maxUnits;
    
    // Constructor
    protected Part(int partId, String name, double price, int unitsStock, int minUnits, int maxUnits) {
        this.partId = partId;
        this.name = name;
        this.price = price;
        this.unitsStock = unitsStock;
        this.minUnits = minUnits;
        this.maxUnits = maxUnits;
    }
    
    // Getters
    public int getPartId() {
        return partId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getUnitsStock() {
        return unitsStock;
    }

    public int getMinUnits() {
        return minUnits;
    }

    public int getMaxUnits() {
        return maxUnits;
    }
    
    // Setters
    public void setPartId(int partId) {
        this.partId = partId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setUnitsStock(int unitsStock) {
        this.unitsStock = unitsStock;
    }

    public void setMinUnits(int minUnits) {
        this.minUnits = minUnits;
    }

    public void setMaxUnits(int maxUnits) {
        this.maxUnits = maxUnits;
    }
    
    /**
     * Generate an error message for invalid values in a part
     * Valid part will return an empty string
     * @param name
     * @param price
     * @param inStock
     * @param min
     * @param max
     * @param errorMessage
     * @return 
     */
    public static String isValidPart(String name, double price, int inStock, int min, int max, String errorMessage) {
        if(name.equals("")) {
            errorMessage += "A name has not been entered. ";
        }
        if(price < 0.01) {
            errorMessage += "The price must be greater than 0. ";
        }
        if(inStock < 1) {
            errorMessage += "Inventory level must be greater than 0. ";
        }
        if(min > max) {
            errorMessage += "The Min value must be less than the Max value. ";
        }
        if(inStock < min) {
            errorMessage += "Inventory level is lower than minimum value. ";
        }
        if(inStock > max) {
            errorMessage += "Inventory level is higher than the maximum value. ";
        }
        return errorMessage;
    }

    @Override
    public String toString() {
        return this.partId+","+this.name+","+this.price+","+this.unitsStock +","+
                this.minUnits +","+this.maxUnits;
    }
}