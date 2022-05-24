
package inventory.model;

import javafx.collections.ObservableList;


public class Product {
    
    // Declare fields
    private ObservableList<Part> associatedParts;
    private int productId;
    private String name;
    private double price;
    private int unitsStock;
    private int minUnits;
    private int maxUnits;

    // Constructor
    public Product(int productId, String name, double price, int unitsStock, int minUnits, int maxUnits, ObservableList<Part> partList) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.unitsStock = unitsStock;
        this.minUnits = minUnits;
        this.maxUnits = maxUnits;
        this.associatedParts= partList;
    }
    
    // Getters
    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }

    public int getProductId() {
        return productId;
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
    public void setAssociatedParts(ObservableList<Part> associatedParts) {
        this.associatedParts = associatedParts;
    }

    public void setProductId(int productId) {
        this.productId = productId;
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
    
    // Other methods
    public void addAssociatedPart(Part part) {
        associatedParts.addAll(part);
    }
    
    public void removeAssociatedPart(Part part) {
        associatedParts.removeAll(part);
    }
    
    public Part lookupAssociatedPart(String searchItem) {
        for(Part p:associatedParts) {
            if(p.getName().contains(searchItem) || Integer.toString(p.getPartId()).equals(searchItem)) return p;
        }
        return null;
    }
    
    /**
     * Generate an error message for invalid values in a product
     * and evaluate whether the sum of the price of associated parts
     * is less than the price of the resulting product.
     * A valid product will return an empty error message string.
     * @param name
     * @param min
     * @param max
     * @param inStock
     * @param price
     * @param parts
     * @param errorMessage
     * @return 
     */
    public static String isValidProduct(String name, double price, int inStock, int min, int max, ObservableList<Part> parts, String errorMessage) {
        double sumOfParts = 0.00;
        for (int i = 0; i < parts.size(); i++) {
            sumOfParts += parts.get(i).getPrice();
        }
        if (name.equals("")) {
            errorMessage += "A name has not been entered. ";
        }
        if (min < 0) {
            errorMessage += "The inventory level must be greater than 0. ";
        }
        if (price < 0.01) {
            errorMessage += "The price must be greater than $0. ";
        }
        if (min > max) {
            errorMessage += "The Min value must be less than the Max value. ";
        }
        if(inStock < min) {
            errorMessage += "Inventory level is lower than minimum value. ";
        }
        if(inStock > max) {
            errorMessage += "Inventory level is higher than the maximum value. ";
        }
        if (parts.isEmpty()) {
            errorMessage += "Product must contain at least 1 part. ";
        }
        if (sumOfParts > price) {
            errorMessage += "Product price must be greater than cost of parts. ";
        }
        return errorMessage;
    }

    @Override
    public String toString() {
        return "P,"+this.productId+","+this.name+","+this.price+","+this.unitsStock +","+
                this.minUnits +","+this.maxUnits;
    }
}