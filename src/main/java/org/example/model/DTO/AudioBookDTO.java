package org.example.model.DTO;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "audioBook")
@PrimaryKeyJoinColumn(name = "id")
public class AudioBookDTO extends BookDTO {
    private String narrator;
    private float fileSize;
    private String fileFormat;

    // no argument constructor required by JPA
    protected AudioBookDTO() {}

    private AudioBookDTO(Builder builder) {
        super(builder);
        this.narrator = builder.narrator;
        this.fileSize = builder.fileSize;
        this.fileFormat = builder.fileFormat;
    }

    public String getNarrator() {
        return narrator;
    }

    public float getFileSize() {
        return fileSize;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    @Override
    public String toString() {
        return "AudioBookDTO{" +
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
                ", narrator='" + getNarrator() + '\'' +
                ", fileSize=" + getFileSize() +
                ", fileFormat='" + getFileFormat() + '\'' +
                '}';

    }

    public static class Builder extends BookDTO.Builder<Builder>{
        private String narrator;
        private float fileSize;
        private String fileFormat;

        public Builder setNarrator(String narrator) {
            this.narrator = narrator;
            return this;
        }

        public Builder setFileSize(float fileSize) {
            this.fileSize = fileSize;
            return this;
        }

        public Builder setFileFormat(String fileFormat) {
            this.fileFormat = fileFormat;
            return this;
        }

        @Override
        public Builder self() {
            return this;
        }

        @Override
        public AudioBookDTO build() {
            return new AudioBookDTO(this);
        }
    }
}
