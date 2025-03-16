package org.example.model.DTO.products;

import javax.persistence.*;

import java.sql.Date;
import java.sql.Time;

/**
 * MusicDTO represents music table in Database
 * functionality is the same as the ProductDTO but with added attributes (to reflect Database normalization)
 */
@Entity
@Table(name = "music")
@PrimaryKeyJoinColumn(name = "id")
public abstract class MusicDTO extends ProductDTO {
        private String format;
        private String Artist;
        private String label;
        private String genre;
        private Date releaseDate;
        private Time playTime;
        private int tracksNum;

    // no argument constructor used by Hibernate to create object after read operation
    protected MusicDTO() {}

        protected MusicDTO(Builder<?> builder) {
            super(builder);
            this.format = builder.format;
            this.Artist = builder.Artist;
            this.label = builder.label;
            this.genre = builder.genre;
            this.releaseDate = builder.releaseDate;
            this.playTime = builder.playTime;
            this.tracksNum = builder.tracksNum;

        }

        public String getFormat() {
            return format;
        };

        public String getArtist() {
            return Artist;
        };

        public String getLabel() {
            return label;
        };

        public String getGenre() {
            return genre;
        };

        public Date getReleaseDate() {
            return releaseDate;
        };

        public Time getPlayTime() {
            return playTime;
        };

        public int getTracksNum() {
            return tracksNum;
        };

        public abstract static class Builder<T extends Builder<T> > extends ProductDTO.Builder<T> {
            private String format;
            private String Artist;
            private String label;
            private String genre;
            private Date releaseDate;
            private Time playTime;
            private int tracksNum;

            public T setFormat(String format) {
                this.format = format;
                return self();
            };

            public T setArtist(String Artist) {
                this.Artist = Artist;
                return self();
            };

            public T setLabel(String label) {
                this.label = label;
                return self();
            };

            public T setGenre(String genre) {
                this.genre = genre;
                return self();
            };

            /**
             *
             * @param releaseDate set using java.swl.date type, format: "YYYY-MM-DD"
             *
             */
            public T setReleaseDate(Date releaseDate) {
                this.releaseDate = releaseDate;
                return self();
            };

            /**
             *
             * @param playTime set using java.sql.time type, format: "HH:MM:SS"
             *
             */
            public T setPlayTime(Time playTime) {
                this.playTime = playTime;
                return self();
            };

            public T setTracksNum(int tracksNum) {
                this.tracksNum = tracksNum;
                return self();
            };
        }

}
