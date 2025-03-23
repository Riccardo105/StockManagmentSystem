package org.example.DAO.ProductSearch;

import org.example.config.DbConnection;
import org.example.model.DAO.products.ProductSearchDAO;
import org.example.model.DTO.products.BookDTO;
import org.example.model.DTO.products.MusicDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;


public class ProductSearchIntegrationTest {
    protected static SessionFactory sessionFactory = DbConnection.getSessionFactory();

    @BeforeAll
    public static void setUp() {
        DataLoading.loadProducts();

    }

    @AfterAll
    public static void tearDown() {
        DataLoading.deleteProducts();

    }

    @Test
    public void testBookQueryNullFormat(){
        ProductSearchDAO productSearchDAO = new ProductSearchDAO(sessionFactory);

        ArrayList<BookDTO> results = productSearchDAO.QueryBooks(null, "test author");

        assertEquals(3, results.size());

    }

    @Test
    public void testBookQueryEbookFormat() {
        ProductSearchDAO productSearchDAO = new ProductSearchDAO(sessionFactory);

        ArrayList<BookDTO> results = productSearchDAO.QueryBooks("ebook", "test author");

        assertEquals(1, results.size()); // Ensure at least one result is returned
        assertEquals("EBookDTO", results.getFirst().getClass().getSimpleName());
    }

    @Test
    public void testBookQueryPaperBookFormat() {
        ProductSearchDAO productSearchDAO = new ProductSearchDAO(sessionFactory);

        ArrayList<BookDTO> results = productSearchDAO.QueryBooks("paperbook", "test author");

        assertEquals(1, results.size()); // Ensure at least one result is returned
        assertEquals("PaperBookDTO", results.getFirst().getClass().getSimpleName());
    }

    @Test
    public void testBookQueryAudioBookFormat() {
        ProductSearchDAO productSearchDAO = new ProductSearchDAO(sessionFactory);

        ArrayList<BookDTO> results = productSearchDAO.QueryBooks("audiobook", "test author");

        assertEquals(1, results.size()); // Ensure at least one result is returned
        assertEquals("AudioBookDTO", results.getFirst().getClass().getSimpleName());
    }


    @Test
    public void testMusicQueryNullFormat(){
        ProductSearchDAO productSearchDAO = new ProductSearchDAO(sessionFactory);

        ArrayList<MusicDTO> results = productSearchDAO.QueryMusic(null, "test artist");

        assertEquals(3, results.size());

    }

    @Test
    public void testMusicQueryEbookFormat() {
        ProductSearchDAO productSearchDAO = new ProductSearchDAO(sessionFactory);

        ArrayList<MusicDTO> results = productSearchDAO.QueryMusic("cd", "test artist");

        assertEquals(1, results.size()); // Ensure at least one result is returned
        assertEquals("CdDTO", results.getFirst().getClass().getSimpleName());
    }

    @Test
    public void testMusicQueryPaperBookFormat() {
        ProductSearchDAO productSearchDAO = new ProductSearchDAO(sessionFactory);

        ArrayList<MusicDTO> results = productSearchDAO.QueryMusic("digital", "test artist");

        assertEquals(1, results.size()); // Ensure at least one result is returned
        assertEquals("DigitalDTO", results.getFirst().getClass().getSimpleName());
    }

    @Test
    public void testMusicQueryAudioBookFormat() {
        ProductSearchDAO productSearchDAO = new ProductSearchDAO(sessionFactory);

        ArrayList<MusicDTO> results = productSearchDAO.QueryMusic("vinyl", "test artist");

        assertEquals(1, results.size()); // Ensure at least one result is returned
        assertEquals("VinylDTO", results.getFirst().getClass().getSimpleName());
    }






}
