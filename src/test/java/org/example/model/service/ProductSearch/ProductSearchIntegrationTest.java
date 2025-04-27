package org.example.model.service.ProductSearch;

import org.example.config.DbConnection;
import org.example.model.Service.ProductSearchService;
import org.example.model.DTO.products.BookDTO;
import org.example.model.DTO.products.MusicDTO;
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
        ProductSearchService productSearchDAO = new ProductSearchService(sessionFactory);

        ArrayList<BookDTO> results = productSearchDAO.QueryBooks(null, "test author");

        assertEquals(3, results.size());

    }

    @Test
    public void testBookQueryEbookFormat() {
        ProductSearchService productSearchDAO = new ProductSearchService(sessionFactory);

        ArrayList<BookDTO> results = productSearchDAO.QueryBooks("ebook", "test author");

        assertEquals(1, results.size()); // Ensure at least one result is returned
        assertEquals("EBookDTO", results.getFirst().getClass().getSimpleName());
    }

    @Test
    public void testBookQueryPaperBookFormat() {
        ProductSearchService productSearchDAO = new ProductSearchService(sessionFactory);

        ArrayList<BookDTO> results = productSearchDAO.QueryBooks("paperbook", "test author");

        assertEquals(1, results.size()); // Ensure at least one result is returned
        assertEquals("PaperBookDTO", results.getFirst().getClass().getSimpleName());
    }

    @Test
    public void testBookQueryAudioBookFormat() {
        ProductSearchService productSearchDAO = new ProductSearchService(sessionFactory);

        ArrayList<BookDTO> results = productSearchDAO.QueryBooks("audiobook", "test author");

        assertEquals(1, results.size()); // Ensure at least one result is returned
        assertEquals("AudioBookDTO", results.getFirst().getClass().getSimpleName());
    }


    @Test
    public void testMusicQueryNullFormat(){
        ProductSearchService productSearchDAO = new ProductSearchService(sessionFactory);

        ArrayList<MusicDTO> results = productSearchDAO.QueryMusic(null, "test artist");

        assertEquals(3, results.size());

    }

    @Test
    public void testMusicQueryEbookFormat() {
        ProductSearchService productSearchDAO = new ProductSearchService(sessionFactory);

        ArrayList<MusicDTO> results = productSearchDAO.QueryMusic("cd", "test artist");

        assertEquals(1, results.size()); // Ensure at least one result is returned
        assertEquals("CdDTO", results.getFirst().getClass().getSimpleName());
    }

    @Test
    public void testMusicQueryPaperBookFormat() {
        ProductSearchService productSearchDAO = new ProductSearchService(sessionFactory);

        ArrayList<MusicDTO> results = productSearchDAO.QueryMusic("digital", "test artist");

        assertEquals(1, results.size()); // Ensure at least one result is returned
        assertEquals("DigitalDTO", results.getFirst().getClass().getSimpleName());
    }

    @Test
    public void testMusicQueryAudioBookFormat() {
        ProductSearchService productSearchDAO = new ProductSearchService(sessionFactory);

        ArrayList<MusicDTO> results = productSearchDAO.QueryMusic("vinyl", "test artist");

        assertEquals(1, results.size()); // Ensure at least one result is returned
        assertEquals("VinylDTO", results.getFirst().getClass().getSimpleName());
    }






}
