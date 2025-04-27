package org.example.model.DAO.products;
import org.example.model.DTO.products.AudioBookDTO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


public class AudioBookDAOIntegrationTest extends ProductAbstractDAOIntegrationTest<AudioBookDTO> {

    protected Integer HelperCreateDTO(Session session){
        AudioBookDTO audioBook = new AudioBookDTO.Builder()
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
                .setNarrator("test narrator")
                .setFileSize(10.0f)
                .setFileFormat("Test File Format")
                .build();

        Transaction tx = session.beginTransaction();
        Integer generatedId = (Integer) session.save(audioBook);
        tx.commit();

        return generatedId;
    }


    protected void HelperDeleteDTO( Session session, AudioBookDTO dto) {
        Transaction tx = session.beginTransaction();
        session.delete(dto);
        tx.commit();
        session.close();
    }

    @Test
    @Override
    public void testCreate() {
        AudioBookDAO audioBookDAO = new AudioBookDAO(sessionFactory);
        AudioBookDTO audioBook = new AudioBookDTO.Builder()
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


        Integer generatedId = audioBookDAO.create(audioBook);

        Session session = sessionFactory.openSession();

        Transaction tx = session.beginTransaction();
        AudioBookDTO dto = session.get(AudioBookDTO.class, generatedId);
        tx.commit();
        session.close();

        // entry is deleted regardless of assertion
        HelperDeleteDTO(sessionFactory.openSession(), dto);

        System.out.println("Expected title: " + audioBook.getTitle());
        System.out.println("Actual title: " + dto.getTitle());
        assertEquals(audioBook.getTitle(), dto.getTitle());

        System.out.println("Expected author: " + audioBook.getAuthor());
        System.out.println("Actual author: " + dto.getAuthor());
        assertEquals(audioBook.getAuthor(), dto.getAuthor());

        System.out.println("Expected narrator: " + audioBook.getNarrator());
        System.out.println("Actual narrator: " + dto.getNarrator());
        assertEquals(audioBook.getNarrator(), dto.getNarrator());


    }

    @Test
    @Override
    public void testRead(){
        Integer generatedId = HelperCreateDTO(sessionFactory.openSession());
        AudioBookDAO audioBookDAO = new AudioBookDAO(sessionFactory);
        AudioBookDTO audioBook = audioBookDAO.read( generatedId);

        // entry is deleted regardless of assertion
        HelperDeleteDTO(sessionFactory.openSession(), audioBook);

        System.out.println("Expected title: Test title");
        System.out.println("Actual title: " + audioBook.getTitle());
        assertEquals("Test title", audioBook.getTitle());

        System.out.println("Expected author: Test author");
        System.out.println("Actual author: " + audioBook.getAuthor());
        assertEquals("Test author", audioBook.getAuthor());

        System.out.println("Expected narrator: test narrator");
        System.out.println("Actual narrator: " + audioBook.getNarrator());
        assertEquals("test narrator", audioBook.getNarrator());
    }

    @Test
    @Override
    public void testUpdate() {
        Integer generatedId = HelperCreateDTO(sessionFactory.openSession());
        AudioBookDAO audioBookDAO = new AudioBookDAO(sessionFactory);
        Session session = sessionFactory.openSession();

        AudioBookDTO dto = session.get(AudioBookDTO.class, generatedId);
        dto.updateStock(15);

        audioBookDAO.update(dto);

        session.refresh(dto);

        AudioBookDTO updatedDto = session.get(AudioBookDTO.class, generatedId);
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
        AudioBookDAO audioBookDAO = new AudioBookDAO(sessionFactory);
        Session session = sessionFactory.openSession();

        AudioBookDTO dto = session.get(AudioBookDTO.class, generatedId);
        AudioBookDTO dto2 = session.get(AudioBookDTO.class, generatedId2);

        dto.updateStock(15);
        dto2.updateStock(15);

        List<AudioBookDTO> updatedDtos = new ArrayList<>();
        updatedDtos.add(dto);
        updatedDtos.add(dto2);

        audioBookDAO.update(updatedDtos);

        session.refresh(dto);
        session.refresh(dto2);

        AudioBookDTO updatedDto = session.get(AudioBookDTO.class, generatedId);
        AudioBookDTO updatedDto2 = session.get(AudioBookDTO.class, generatedId2);

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
        AudioBookDAO audioBookDAO = new AudioBookDAO(sessionFactory);

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        AudioBookDTO audioBookToDelete = session.get(AudioBookDTO.class, generatedId);
        if (audioBookToDelete != null) {
            audioBookDAO.delete(audioBookToDelete);
        }

        tx.commit();
        session.close();

        AudioBookDTO deletedAudioBook = audioBookDAO.read(generatedId);

        System.out.println("Expected object: null");
        System.out.println("Actual object: " + deletedAudioBook);
        assertNull(deletedAudioBook);
    }

    @Test
    public void testDeleteList() {
        Integer generatedId = HelperCreateDTO(sessionFactory.openSession());
        Integer generatedId2 = HelperCreateDTO(sessionFactory.openSession());
        AudioBookDAO audioBookDAO = new AudioBookDAO(sessionFactory);

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        AudioBookDTO audioBookToDelete1 = session.get(AudioBookDTO.class, generatedId);
        AudioBookDTO audioBookToDelete2 = session.get(AudioBookDTO.class, generatedId2);

        ArrayList<AudioBookDTO> audioBooksToDelete = new ArrayList<>();
        audioBooksToDelete.add(audioBookToDelete1);
        audioBooksToDelete.add(audioBookToDelete2);

        audioBookDAO.delete(audioBooksToDelete);

        tx.commit();
        session.close();

        AudioBookDTO deletedAudioBook1 = audioBookDAO.read(generatedId);
        AudioBookDTO deletedAudioBook2 = audioBookDAO.read(generatedId2);


        System.out.println("Expected object: null");
        System.out.println("Actual object: " + deletedAudioBook1);
        assertNull(deletedAudioBook1);

        System.out.println("Expected object: null");
        System.out.println("Actual object: " + deletedAudioBook2);
        assertNull(deletedAudioBook2);
    }
}
