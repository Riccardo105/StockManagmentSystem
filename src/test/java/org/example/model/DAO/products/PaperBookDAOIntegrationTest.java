package org.example.model.DAO.products;
import org.example.model.DTO.products.PaperBookDTO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class PaperBookDAOIntegrationTest extends ProductAbstractDAOIntegrationTest<PaperBookDTO> {

    protected Integer HelperCreateDTO(Session session){
        PaperBookDTO ebook = new PaperBookDTO.Builder()
                .setTitle("Test title")
                .setBuyingPrice(10.0f)
                .setStock(10)
                .setSellingPrice(10.0f)
                .setFormat("Test format")
                .setAuthor("Test author")
                .setPublisher("Test publisher")
                .setGenre("Test genre")
                .setSeries("Test series")
                .setReleaseDate(java.sql.Date.valueOf("2023-10-15"))
                .setBindingType("Test binding type")
                .setNumPages(10)
                .setEdition("Test edition")
                .build();

        Transaction tx = session.beginTransaction();
        Integer generatedId = (Integer) session.save(ebook);
        tx.commit();

        return generatedId;
    }


    protected void HelperDeleteDTO( Session session, PaperBookDTO dto) {
        Transaction tx = session.beginTransaction();
        session.delete(dto);
        tx.commit();
        session.close();
    }

    @Test
    @Override
    public void testCreate() {
        PaperBookDAO paperBookDAO = new PaperBookDAO(sessionFactory);
        PaperBookDTO paperBook = new PaperBookDTO.Builder()
                .setTitle("Test title")
                .setBuyingPrice(10.0f)
                .setStock(10)
                .setSellingPrice(10.0f)
                .setFormat("Test format")
                .setAuthor("Test author")
                .setPublisher("Test publisher")
                .setGenre("Test genre")
                .setSeries("Test series")
                .setReleaseDate(java.sql.Date.valueOf("2023-10-15"))
                .setBindingType("Test binding type")
                .setNumPages(10)
                .setEdition("Test edition")
                .build();

        Integer generatedId = paperBookDAO.create(paperBook);

        Session session = sessionFactory.openSession();

        Transaction tx = session.beginTransaction();
        PaperBookDTO dto = session.get(PaperBookDTO.class, generatedId);
        tx.commit();
        session.close();

        // entry is deleted regardless of assertion
        HelperDeleteDTO(sessionFactory.openSession(), dto);

        System.out.println("Expected title: " + paperBook.getTitle());
        System.out.println("Actual title: " + dto.getTitle());
        assertEquals(paperBook.getTitle(), dto.getTitle());

        System.out.println("Expected author: " + paperBook.getAuthor());
        System.out.println("Actual author: " + dto.getAuthor());
        assertEquals(paperBook.getAuthor(), dto.getAuthor());

        System.out.println("Expected number of pages: " + paperBook.getNumPages());
        System.out.println("Actual number of pages: " + dto.getNumPages());
        assertEquals(paperBook.getNumPages(), dto.getNumPages());


    }

    @Test
    @Override
    public void testRead(){
        Integer generatedId = HelperCreateDTO(sessionFactory.openSession());
        PaperBookDAO paperBookDAO = new PaperBookDAO(sessionFactory);
        PaperBookDTO paperBook = paperBookDAO.read( generatedId);

        // entry is deleted regardless of assertion
        HelperDeleteDTO(sessionFactory.openSession(), paperBook);

        System.out.println("Expected title: Test title");
        System.out.println("Actual title: " + paperBook.getTitle());
        assertEquals("Test title", paperBook.getTitle());

        System.out.println("Expected author: Test author");
        System.out.println("Actual author: " + paperBook.getAuthor());
        assertEquals("Test author", paperBook.getAuthor());

        System.out.println("Expected number of pages: 10");
        System.out.println("Actual number of pages: " + paperBook.getNumPages());
        assertEquals(10, paperBook.getNumPages());
    }

    @Test
    @Override
    public void testUpdate() {
        Integer generatedId = HelperCreateDTO(sessionFactory.openSession());
        PaperBookDAO paperBookDAO = new PaperBookDAO(sessionFactory);
        Session session = sessionFactory.openSession();

        PaperBookDTO dto = session.get(PaperBookDTO.class, generatedId);
        dto.updateStock(15);

        paperBookDAO.update(dto);

        session.refresh(dto);

        PaperBookDTO updatedDto = session.get(PaperBookDTO.class, generatedId);
        session.close();

        // entry is deleted regardless of assertion
        HelperDeleteDTO(sessionFactory.openSession(), updatedDto);

        System.out.println("Expected stock: " + dto.getStock());
        System.out.println("Actual stock: " + updatedDto.getStock());
        assertEquals(updatedDto.getStock(), dto.getStock());


    }

    @Test
    @Override
    public void testUpdateList() {
        Integer generatedId = HelperCreateDTO(sessionFactory.openSession());
        Integer generatedId2 = HelperCreateDTO(sessionFactory.openSession());
        PaperBookDAO paperBookDAO = new PaperBookDAO(sessionFactory);
        Session session = sessionFactory.openSession();

        PaperBookDTO dto = session.get(PaperBookDTO.class, generatedId);
        PaperBookDTO dto2 = session.get(PaperBookDTO.class, generatedId2);

        dto.updateStock(15);
        dto2.updateStock(15);

        List<PaperBookDTO> updatedDtos = new ArrayList<>();
        updatedDtos.add(dto);
        updatedDtos.add(dto2);

        paperBookDAO.update(updatedDtos);

        session.refresh(dto);
        session.refresh(dto2);

        PaperBookDTO updatedDto = session.get(PaperBookDTO.class, generatedId);
        PaperBookDTO updatedDto2 = session.get(PaperBookDTO.class, generatedId2);

        HelperDeleteDTO(sessionFactory.openSession(), dto);
        HelperDeleteDTO(sessionFactory.openSession(), dto2);

        session.close();

        System.out.println("Expected stock: 15 ");
        System.out.println("Actual stock: " + updatedDto.getStock());
        assertEquals(15, updatedDto.getStock());
        System.out.println("Expected stock: 15 ");
        System.out.println("Actual stock: " + updatedDto2.getStock());
        assertEquals(15, updatedDto2.getStock());


    }


    @Test
    @Override
    public void testDelete() {
        Integer generatedId = HelperCreateDTO(sessionFactory.openSession());
        PaperBookDAO paperBookDAO = new PaperBookDAO(sessionFactory);

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        PaperBookDTO paperBookToDelete = session.get(PaperBookDTO.class, generatedId);
        if (paperBookToDelete != null) {
            paperBookDAO.delete(paperBookToDelete);
        }

        tx.commit();
        session.close();

        PaperBookDTO deletedPaperBook = paperBookDAO.read(generatedId);

        System.out.println("Expected object: null");
        System.out.println("Actual object: " + deletedPaperBook);
        assertNull(deletedPaperBook);
    }

    @Test
    public void testDeleteList() {
        Integer generatedId = HelperCreateDTO(sessionFactory.openSession());
        Integer generatedId2 = HelperCreateDTO(sessionFactory.openSession());
        PaperBookDAO paperBookDAO = new PaperBookDAO(sessionFactory);

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        PaperBookDTO paperBookToDelete1 = session.get(PaperBookDTO.class, generatedId);
        PaperBookDTO paperBookToDelete2 = session.get(PaperBookDTO.class, generatedId2);

        ArrayList<PaperBookDTO> paperBookToDelete = new ArrayList<>();
        paperBookToDelete.add(paperBookToDelete1);
        paperBookToDelete.add(paperBookToDelete2);

        paperBookDAO.delete(paperBookToDelete);

        tx.commit();
        session.close();

        PaperBookDTO deletedPaperBook1 = paperBookDAO.read(generatedId);
        PaperBookDTO deletedPaperBook2 = paperBookDAO.read(generatedId2);


        System.out.println("Expected object: null");
        System.out.println("Actual object: " + deletedPaperBook1);
        assertNull(deletedPaperBook1);

        System.out.println("Expected object: null");
        System.out.println("Actual object: " + deletedPaperBook2);
        assertNull(deletedPaperBook2);
    }
}
