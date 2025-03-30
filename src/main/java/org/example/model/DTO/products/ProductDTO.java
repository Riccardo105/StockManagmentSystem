package org.example.model.DTO.products;


import javax.persistence.*;

/** ProductDTO represent the product table in the Database.
 * Stock, buyingPrice and sellingPrice are the only mutable fields (through setter)
 */
@Entity
@Table(name = "product")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ProductDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private int stock;
    private float buyingPrice;
    private float sellingPrice;

    // no argument constructor required by JPA
    protected ProductDTO() {}

    /**
     * object creation enforced through builder
     * @param builder: takes is unknown type, so that it can take in specific subclasses' builders
     */
    protected ProductDTO(Builder<?> builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.stock = builder.stock;
        this.buyingPrice = builder.buyingPrice;
        this.sellingPrice = builder.sellingPrice;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getStock() {
        return stock;
    }

    public float getBuyingPrice() {
        return buyingPrice;
    }

    public float getSellingPrice() {
        return sellingPrice;
    }

    public void updateStock(int stock) {
        this.stock = stock;
    }

    public void updateBuyingPrice(float buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public void updateSellingPrice(float sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
    
    public abstract static class Builder<T extends Builder<T>> {
        private int id;
        private String title;
        private int stock;
        private float buyingPrice;
        private float sellingPrice;

        /**
         *
         * @param id reflects id Primary key field in database.
         * NOTE: do not set id when creating a new product, as database id field is set to auto-increment.
         *
         */
        public T setId(int id) {
            this.id = id;
            return self();
        }

        public T setTitle(String title) {
            this.title = title;
            return self();
        }

        public T setStock(int stock) {
            this.stock = stock;
            return self();
        }

        public T setBuyingPrice(float buyingPrice) {
            this.buyingPrice = buyingPrice;
            return self();
        }

        public T setSellingPrice(float sellingPrice) {
            this.sellingPrice = sellingPrice;
            return self();
        }

        /**
         * this method is needed so that each subclass can assign its own builder to the method's return
         * @return subclasses' builder will return itself
         */
        protected abstract T self();


        public abstract ProductDTO build();

    }
}

