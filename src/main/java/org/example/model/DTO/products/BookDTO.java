package org.example.model.DTO.products;

import javax.persistence.*;

import java.sql.Date;

/**
 * BookDTO represents book table in Database
 * functionality is the same as the ProductDTO but with added attributes (to reflect Database normalization)
 */
@Entity
@Table(name = "book")
@PrimaryKeyJoinColumn(name = "id")
public abstract class BookDTO extends ProductDTO {
    private String format;
    private String author;
    private String publisher;
    private String genre;
    private String series;
    private Date releaseDate;

    // no argument constructor used by Hibernate to create object after read operation
    protected BookDTO() {}

    protected BookDTO(Builder<?> builder) {
        super(builder);
        this.format = builder.format;
        this.author = builder.author;
        this.publisher = builder.publisher;
        this.genre = builder.genre;
        this.series = builder.series;
        this.releaseDate = builder.releaseDate;

    }

    public String getFormat() {
        return format;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getGenre() {
        return genre;
    }

    public String getSeries() {
        return series;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    /*
    publisher and series are optional fields (nullable in database) so setter is not required for object creation
     */
    public abstract static class Builder<T extends Builder<T>> extends ProductDTO.Builder<T> {
        private String format;
        private String author;
        private String publisher ;
        private String genre;
        private String series ;
        private Date releaseDate;


        public T setFormat(String format) {
            this.format = format;
            return self();
        }

        public T setAuthor(String author) {
            this.author = author;
            return self();
        }

        public T setPublisher(String publisher) {
            this.publisher = publisher;
            return self();
        }

        public T setGenre(String genre) {
            this.genre = genre;
            return self();
        }

        public T setSeries(String series) {
            this.series = series;
            return self();
        }

        /**
         *
         * @param releaseDate set using java.swl.date type, format: "YYYY-MM-DD"
         *
         */
        public T setReleaseDate(Date releaseDate) {
            this.releaseDate = releaseDate;
            return self();
        }

    }
}
