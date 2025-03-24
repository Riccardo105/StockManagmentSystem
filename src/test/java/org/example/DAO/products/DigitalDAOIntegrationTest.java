package org.example.DAO.products;

import org.example.model.DAO.products.CdDAO;
import org.example.model.DAO.products.DigitalDAO;
import org.example.model.DTO.products.CdDTO;
import org.example.model.DTO.products.DigitalDTO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DigitalDAOIntegrationTest extends ProductAbstractDAOIntegrationTest<DigitalDTO> {

    protected Integer HelperCreateDTO(Session session) {
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

        Transaction tx = session.beginTransaction();
        Integer generatedId = (Integer) session.save(digital);
        tx.commit();

        return generatedId;
    }

    protected void HelperDeleteDTO(Session session, DigitalDTO dto) {
        Transaction tx = session.beginTransaction();
        session.delete(dto);
        tx.commit();
        session.close();
    }

    @Test
    @Override
    public void testCreate() {
        DigitalDAO digitalDAO = new DigitalDAO(sessionFactory);
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


        Integer generatedId = digitalDAO.create(digital);

        Session session = sessionFactory.openSession();

        Transaction tx = session.beginTransaction();
        DigitalDTO dto = session.get(DigitalDTO.class, generatedId);
        tx.commit();
        session.close();

        // entry is deleted regardless of assertion
        HelperDeleteDTO(sessionFactory.openSession(), dto);

        System.out.println("Expected title: " + digital.getTitle());
        System.out.println("Actual title: " + dto.getTitle());
        assertEquals(digital.getTitle(), dto.getTitle());

        System.out.println("Expected artist: " + digital.getArtist());
        System.out.println("Actual artist: " + dto.getArtist());
        assertEquals(digital.getArtist(), dto.getArtist());

        System.out.println("Expected file format: " + digital.getFileFormat());
        System.out.println("Actual file format: " + dto.getFileFormat());
        assertEquals(digital.getFileFormat(), dto.getFileFormat());

    }

    @Test
    @Override
    public void testRead() {
        Integer generatedId = HelperCreateDTO(sessionFactory.openSession());
        DigitalDAO digitalDAO = new DigitalDAO(sessionFactory);
        DigitalDTO digital = digitalDAO.read(generatedId);

        // entry is deleted regardless of assertion
        HelperDeleteDTO(sessionFactory.openSession(), digital);

        System.out.println("Expected title: Test title");
        System.out.println("Actual title: " + digital.getTitle());
        assertEquals("Test title", digital.getTitle());

        System.out.println("Expected artist: Test artist");
        System.out.println("Actual artist: " + digital.getArtist());
        assertEquals("Test artist", digital.getArtist());

        System.out.println("Expected file format: Test file format");
        System.out.println("Actual file format: " + digital.getFileFormat());
        assertEquals("Test file format", digital.getFileFormat());
    }

    @Test
    @Override
    public void testUpdate() {
        Integer generatedId = HelperCreateDTO(sessionFactory.openSession());
        DigitalDAO digitalDAO = new DigitalDAO(sessionFactory);
        Session session = sessionFactory.openSession();

        DigitalDTO dto = session.get(DigitalDTO.class, generatedId);
        dto.updateStock(15);

        digitalDAO.update(dto);

        session.refresh(dto);

        DigitalDTO updatedDto = session.get(DigitalDTO.class, generatedId);

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
        DigitalDAO digitalDAO = new DigitalDAO(sessionFactory);
        Session session = sessionFactory.openSession();

        DigitalDTO dto = session.get(DigitalDTO.class, generatedId);
        DigitalDTO dto2 = session.get(DigitalDTO.class, generatedId2);

        dto.updateStock(15);
        dto2.updateStock(15);

        List<DigitalDTO> updatedDtos = new ArrayList<>();
        updatedDtos.add(dto);
        updatedDtos.add(dto2);

        digitalDAO.update(updatedDtos);

        session.refresh(dto);
        session.refresh(dto2);

        DigitalDTO updatedDto = session.get(DigitalDTO.class, generatedId);
        DigitalDTO updatedDto2 = session.get(DigitalDTO.class, generatedId2);

        HelperDeleteDTO(sessionFactory.openSession(), dto);
        HelperDeleteDTO(sessionFactory.openSession(), updatedDto2);

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
        DigitalDAO digitalDAO = new DigitalDAO(sessionFactory);

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        DigitalDTO digitalToDelete = session.get(DigitalDTO.class, generatedId);
        if (digitalToDelete != null) {
            digitalDAO.delete(digitalToDelete);
        }

        tx.commit();
        session.close();

        DigitalDTO deletedDigital = digitalDAO.read(generatedId);

        System.out.println("Expected object: null");
        System.out.println("Actual object: " + deletedDigital);
        assertNull(deletedDigital);
    }

    @Test
    public void testDeleteList() {
        Integer generatedId = HelperCreateDTO(sessionFactory.openSession());
        Integer generatedId2 = HelperCreateDTO(sessionFactory.openSession());
        DigitalDAO digitalDAO = new DigitalDAO(sessionFactory);

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        DigitalDTO digitalToDelete1 = session.get(DigitalDTO.class, generatedId);
        DigitalDTO digitalToDelete2 = session.get(DigitalDTO.class, generatedId2);

        ArrayList<DigitalDTO> digitalToDelete = new ArrayList<>();
        digitalToDelete.add(digitalToDelete1);
        digitalToDelete.add(digitalToDelete2);

        digitalDAO.delete(digitalToDelete);

        tx.commit();
        session.close();

        DigitalDTO deletedDigital1 = digitalDAO.read(generatedId);
        DigitalDTO deletedDigital2 = digitalDAO.read(generatedId2);


        System.out.println("Expected object: null");
        System.out.println("Actual object: " + deletedDigital1);
        assertNull(deletedDigital1);

        System.out.println("Expected object: null");
        System.out.println("Actual object: " + deletedDigital2);
        assertNull(deletedDigital2);
    }
}
