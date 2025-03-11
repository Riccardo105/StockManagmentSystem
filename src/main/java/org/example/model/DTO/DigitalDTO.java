package org.example.model.DTO;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "digital")
@PrimaryKeyJoinColumn(name = "id")
public class DigitalDTO extends MusicDTO{
    private String fileFormat;
    private float fileSize;
    private int bitrateMbps;

    // no argument constructor required by JPA
    protected DigitalDTO() {}

    private DigitalDTO(Builder builder){
        super(builder);
        this.fileFormat = builder.fileFormat;
        this.fileSize = builder.fileSize;
        this.bitrateMbps = builder.bitrateMbps;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public float getFileSize() {
        return fileSize;
    }

    public int getBitrateMbps() {
        return bitrateMbps;
    }

    @Override
    public String toString() {
        return "DigitalDTO{" +
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
                ", fileFormat='" + getFileFormat() + '\'' +
                ", fileSize=" + getFileSize() +
                ", bitrateMbps=" + getBitrateMbps() +
                '}';
    }

    public static class Builder extends MusicDTO.Builder<Builder>{
        private String fileFormat;
        private float fileSize;
        private int bitrateMbps;

        public Builder setFileFormat(String fileFormat){
            this.fileFormat = fileFormat;
            return this;
        }

        public Builder setFileSize(float fileSize){
            this.fileSize = fileSize;
            return this;
        }

        public Builder setBitrateMbps(int bitrateMbps){
            this.bitrateMbps = bitrateMbps;
            return this;
        }
        @Override
        public Builder self(){
            return this;
        }

        @Override
        public DigitalDTO build(){
            return new DigitalDTO(this);
        }
    }
}
