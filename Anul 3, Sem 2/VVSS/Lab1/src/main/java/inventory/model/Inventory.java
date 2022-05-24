
package inventory.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    
    // Declare fields
    private ObservableList<Product> allProducts;
    private ObservableList<Part> allParts;
    private int autoPartId;
    private int autoProductId;


    public Inventory(){
        this.allProducts = FXCollections.observableArrayList();
        this.allParts= FXCollections.observableArrayList();
        this.autoProductId=0;
        this.autoPartId=0;
    }

    // Declare methods
    /**
     * Add new product to observable list products
     * @param product 
     */
    public void addProduct(Product product) {
        allProducts.addAll(product);
    }
    
    /**
     * Remove product from observable list products
     * @param product 
     */
    public void removeProduct(Product product) {
        allProducts.removeAll(product);
    }
    
    /**
     * Accepts search parameter and if an ID or name matches input, that product is returned.
     * @param searchItem - the ID/name of the product to be searched.
     * @return the coresponding product, if found. Otherwise returns an empty object.
     */
    public Product lookupProduct(String searchItem) {
        if (searchItem.equals("")) {
            return null;
        }
        boolean isFound = false;
        for (Product p: allProducts) {
            if (p.getName().contains(searchItem) || (p.getProductId()+"").equals(searchItem))
                return p;
            isFound = true;
        }
        if (!isFound) {
            return new Product(0, null, 0.0, 0, 0, 0, null);
        }
        return null;
    }
    
    /**
     * Update product at given index
     * @param index
     * @param product 
     */
    public void updateProduct(int index, Product product) {
        allProducts.setAll(product);
    }
    
    /**
     * Getter for Product Observable List
     * @return 
     */
    public ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    public void setAllProducts(ObservableList<Product> list) {
        allProducts =list;
    }
    
    /**
     * Add new part to observable list allParts
     * @param part 
     */
    public void addPart(Part part) {
        allParts.addAll(part);
    }
    
    /**
     * Removes part passed as parameter from allParts
     * @param part 
     */
    public void deletePart(Part part) {
        allParts.removeAll(part);
    }
    
    /**
     * Accepts search parameter and if an ID or name matches input, that part is returned
     * @param searchItem
     * @return 
     */
    public Part lookupPart(String searchItem) {
        for(Part p:allParts) {
            if(p.getName().contains(searchItem) || (p.getPartId()+"").equals(searchItem)) return p;
        }
        return null;
    }
    
    /**
     * Update part at given index
     * @param index
     * @param part 
     */
    public void updatePart(int index, Part part) {
        allParts.setAll(part);
    }
    
    /**
     * Getter for allParts Observable List
     * @return 
     */
    public ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     *
     * @param list
     */
    public void setAllParts(ObservableList<Part> list) {
        allParts=list;
    }
    
    /**
     * Method for incrementing part ID to be used to automatically
     * assign ID numbers to parts
     * @return 
     */
    public int getAutoPartId() {
        autoPartId++;
        return autoPartId;
    }
    
    /**
     * Method for incrementing product ID to be used to automatically
     * assign ID numbers to products
     * @return 
     */
    public int getAutoProductId() {
        autoProductId++;
        return autoProductId;
    }


    public void setAutoPartId(int id){
        autoPartId=id;
    }

    public void setAutoProductId(int id){
        autoProductId=id;
    }
    
}
