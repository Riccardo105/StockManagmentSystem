package org.example.model.DTO;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "vinyl")
@PrimaryKeyJoinColumn(name = "id")
public class VinylDTO extends MusicDTO {
    private int rpm;
    private int size;
    private String edition;

    // no argument constructor required by JPA
    protected VinylDTO() {}

    private VinylDTO(Builder builder) {
        super(builder);
        this.rpm = builder.rpm;
        this.size = builder.size;
        this.edition = builder.edition;
    }

    public int getRpm() {
        return rpm;
    }

    public int getSize() {
        return size;
    }

    public String getEdition() {
        return edition;
    }

    @Override
    public String toString() {
        return "CdDTO{" +
                "title='" + getTitle() + '\'' +
                ", stock=" + getStock() +
                ", buyingPrice=" + getBuyingPrice() +
                ", sellingPrice=" + getSellingPrice() +
                ", format='" + getFormat() + '\'' +
                ", artist='" + getArtist() + '\'' +
                ", label='" + getLabel() + '\'' +
                ", genre='" + getGenre() + '\'' +
                ", releaseDate=" + getReleaseDate() +
                ", playTime=" + getPlayTime() +
                ", tracksNum=" + getTracksNum() +
                ", rpm=" + getRpm() +
                ", size=" + getSize() +
                ", edition='" + getEdition() + '\'' +
                '}';
    }

    public static class Builder extends MusicDTO.Builder<Builder> {
        private int rpm;
        private int size;
        private String edition;

        public Builder setRpm(int rpm) {
            this.rpm = rpm;
            return this;
        }

        public Builder setSize(int size) {
            this.size = size;
            return this;
        }

        public Builder setEdition(String edition) {
            this.edition = edition;
            return this;
        }

        @Override
        protected Builder self(){
            return this;
        }
        @Override
        public VinylDTO build() {
            return new VinylDTO(this);
        }
    }
}
