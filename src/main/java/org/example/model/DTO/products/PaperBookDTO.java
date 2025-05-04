package org.example.model.DTO.products;

import javax.persistence.*;

@Entity
@Table(name = "paperbook")
@PrimaryKeyJoinColumn(name = "id")
public class PaperBookDTO extends BookDTO {
    private String bindingType;
    private int numPages;
    private String edition;

    // no argument constructor used by Hibernate to create object after read operation
    protected PaperBookDTO() {}

    private PaperBookDTO( Builder builder){
        super(builder);
        this.bindingType = builder.bindingType;
        this.numPages = builder.numPages;
        this.edition = builder.edition;
    }

    public String getBindingType() {
        return bindingType;
    }

    public int getNumPages() {
        return numPages;
    }

    public String getEdition() {
        return edition;
    }

    @Override
    public String toString() {
        return "PaperBookDTO{" +
                "title='" + getTitle() + '\'' +
                ", stock=" + getStock() +
                ", buyingPrice=" + getBuyingPrice() +
                ", sellingPrice=" + getSellingPrice() +
                ", format='" + getFormat() + '\'' +
                ", author='" + getAuthor() + '\'' +
                ", publisher='" + getPublisher() + '\'' +
                ", genre='" + getGenre() + '\'' +
                ", series='" + getSeries() + '\'' +
                ", releaseDate=" + getReleaseDate() +
                ", bindingType='" + getBindingType() + '\'' +
                ", numPages=" + getNumPages() +
                ", edition='" + getEdition() + '\'' +
                '}';

    }

    public static class Builder extends BookDTO.Builder<Builder>{
        private String bindingType;
        private int numPages;
        private String edition;

        public Builder setBindingType(String bindingType){
            this.bindingType = bindingType;
            return this;
        }

        public Builder setNumPages(int numPages){
            this.numPages = numPages;
            return this;
        }

        public Builder setEdition(String edition){
            this.edition = edition;
            return this;
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public PaperBookDTO build(){
            return new PaperBookDTO(this);
        }
    }

    // Copy constructor needed by clone method
    public PaperBookDTO (PaperBookDTO other){
        super(other);
        this.bindingType = other.bindingType;
        this.numPages = other.numPages;
        this.edition = other.edition;
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public PaperBookDTO clone(){
        return new PaperBookDTO(this);
    };
}
