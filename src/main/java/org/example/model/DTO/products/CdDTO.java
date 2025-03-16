package org.example.model.DTO.products;

import javax.persistence.*;

@Entity
@Table( name = "cd")
@PrimaryKeyJoinColumn(name = "id")
public class CdDTO extends MusicDTO {
    private int numOfDiscs;
    private String conditions;
    private int bitrateMbps;

    // no argument constructor used by Hibernate to create object after read operation
    protected CdDTO() {}

    private CdDTO (Builder builder) {
        super(builder);
        this.numOfDiscs = builder.numOfDiscs;
        this.conditions = builder.conditions;
        this.bitrateMbps = builder.bitrateMbps;
    }

    public int getNumOfDiscs() {
        return numOfDiscs;
    }

    public String getConditions() {
        return conditions;
    }

    public int getBitrateMbps() {
        return bitrateMbps;
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
                ", releaseDate="  + getReleaseDate() +
                ", playTime=" + getPlayTime() +
                ", tracksNum=" + getTracksNum() +
                ", numOfDiscs=" + getNumOfDiscs() +
                ", conditions='" + getConditions() + '\'' +
                ", bitrateMbps=" + getBitrateMbps() +
                '}';
    }

    public static class Builder extends MusicDTO.Builder<Builder> {
        private int numOfDiscs;
        private String conditions;
        private int bitrateMbps;

        public Builder setNumOfDiscs(int numOfDiscs) {
            this.numOfDiscs = numOfDiscs;
            return this;
        }

        public Builder setConditions(String conditions) {
            this.conditions = conditions;
            return this;
        }

        public Builder setBitrateMbps(int bitrateMbps) {
            this.bitrateMbps = bitrateMbps;
            return this;
        }

        @Override
        public Builder self(){
            return this;
        }

        @Override
        public CdDTO build() {
            return new CdDTO(this);
        }
    }
}
