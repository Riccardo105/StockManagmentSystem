package org.example.model.service.ProductSearch;

import org.example.config.DbConnection;
import org.example.model.DTO.products.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;

public class DataLoading {

    private static final ArrayList<ProductDTO> testProducts = createProducts();

    private static ArrayList<ProductDTO> createProducts() {
        ArrayList<ProductDTO> products = new ArrayList<>();
        EBookDTO ebook = new EBookDTO.Builder()
                .setTitle("Test title")
                .setBuyingPrice(10.0f)
                .setStock(10)
                .setSellingPrice(10.0f)
                .setFormat("Test format")
                .setAuthor("Test Author")
                .setPublisher("Test publisher")
                .setGenre("Test genre")
                .setSeries("Test series")
                .setReleaseDate(java.sql.Date.valueOf("2023-10-15"))
                .setFileSize(10.0f)
                .setFileFormat("Test File Format")
                .setNumPages(10)
                .build();

        PaperBookDTO paperBook = new PaperBookDTO.Builder()
                .setTitle("Test title")
                .setBuyingPrice(10.0f)
                .setStock(10)
                .setSellingPrice(10.0f)
                .setFormat("Test format")
                .setAuthor("Test Author")
                .setPublisher("Test publisher")
                .setGenre("Test genre")
                .setSeries("Test series")
                .setReleaseDate(java.sql.Date.valueOf("2023-10-15"))
                .setBindingType("Test binding type")
                .setNumPages(10)
                .setEdition("test edition")
                .build();

        AudioBookDTO audioBook =  new AudioBookDTO.Builder()
                .setTitle("Test title")
                .setBuyingPrice(10.0f)
                .setStock(10)
                .setSellingPrice(10.0f)
                .setFormat("Test format")
                .setAuthor("Test Author")
                .setPublisher("Test publisher")
                .setGenre("Test genre")
                .setSeries("Test series")
                .setReleaseDate(java.sql.Date.valueOf("2023-10-15"))
                .setNarrator("test narrator")
                .setFileSize(10.0f)
                .setFileFormat("Test File Format")
                .build();

        CdDTO cd = new CdDTO.Builder()
                .setTitle("Test title")
                .setBuyingPrice(10.0f)
                .setStock(10)
                .setSellingPrice(10.0f)
                .setFormat("Test format")
                .setArtist("Test artist")
                .setLabel("Test label")
                .setGenre("Test genre")
                .setReleaseDate(java.sql.Date.valueOf("2023-10-15"))
                .setPlayTime(java.sql.Time.valueOf("01:10:00"))
                .setTracksNum(10)
                .setNumOfDiscs(10)
                .setConditions("Test condition")
                .setBitrateMbps(10)
                .build();

        DigitalDTO digital = new DigitalDTO.Builder()
                .setTitle("Test title")
                .setBuyingPrice(10.0f)
                .setStock(10)
                .setSellingPrice(10.0f)
                .setFormat("Test format")
                .setArtist("Test artist")
                .setLabel("Test label")
                .setGenre("Test genre")
                .setReleaseDate(java.sql.Date.valueOf("2023-10-15"))
                .setPlayTime(java.sql.Time.valueOf("01:10:00"))
                .setTracksNum(10)
                .setFileFormat("Test file format")
                .setFileSize(100.0f)
                .setBitrateMbps(100)
                .build();

        VinylDTO vinyl = new VinylDTO.Builder()
                .setTitle("Test title")
                .setBuyingPrice(10.0f)
                .setStock(10)
                .setSellingPrice(10.0f)
                .setFormat("Test format")
                .setArtist("Test artist")
                .setLabel("Test label")
                .setGenre("Test genre")
                .setReleaseDate(java.sql.Date.valueOf("2023-10-15"))
                .setPlayTime(java.sql.Time.valueOf("01:10:00"))
                .setTracksNum(10)
                .setRpm(100)
                .setSize(10)
                .setEdition("Test edition")
                .build();


        products.add(ebook);
        products.add(paperBook);
        products.add(audioBook);
        products.add(cd);
        products.add(digital);
        products.add(vinyl);
        return products;

    };

    public static void loadProducts() {
        System.setProperty("test.env", "true");
        SessionFactory sessionFactory = DbConnection.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        for (ProductDTO product : testProducts) {
            session.save(product);
        }

        tx.commit();
        session.close();
    }

    public static void deleteProducts() {
        SessionFactory sessionFactory = DbConnection.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        for (ProductDTO product : testProducts) {
            session.delete(product);
        }

        tx.commit();
        session.close();
    }
}
