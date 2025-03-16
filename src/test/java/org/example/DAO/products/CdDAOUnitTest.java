package org.example.DAO.products;

import org.example.model.DAO.products.CdDAO;
import org.example.model.DTO.products.CdDTO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CdDAOUnitTest extends AbstractDAOUnitTest<CdDTO> {

    protected Integer HelperCreateDTO(Session session) {
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

        Transaction tx = session.beginTransaction();
        Integer generatedId = (Integer) session.save(cd);
        tx.commit();

        return generatedId;
    }


    protected void HelperDeleteDTO(Session session, CdDTO dto) {
        Transaction tx = session.beginTransaction();
        session.delete(dto);
        tx.commit();
        session.close();
    }

    @Test
    @Override
    public void testCreate() {
        CdDAO cdDAO = new CdDAO(sessionFactory);
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


        Integer generatedId = cdDAO.create(cd);

        Session session = sessionFactory.openSession();

        Transaction tx = session.beginTransaction();
        CdDTO dto = session.get(CdDTO.class, generatedId);
        tx.commit();
        session.close();

        // entry is deleted regardless of assertion
        HelperDeleteDTO(sessionFactory.openSession(), dto);

        System.out.println("Expected title: " + cd.getTitle());
        System.out.println("Actual title: " + dto.getTitle());
        assertEquals(cd.getTitle(), dto.getTitle());

        System.out.println("Expected artist: " + cd.getArtist());
        System.out.println("Actual artist: " + dto.getArtist());
        assertEquals(cd.getArtist(), dto.getArtist());

        System.out.println("Expected numOfDiscs: " + cd.getNumOfDiscs());
        System.out.println("Actual numOfDiscs: " + dto.getNumOfDiscs());
        assertEquals(cd.getNumOfDiscs(), dto.getNumOfDiscs());


    }

    @Test
    @Override
    public void testRead() {
        Integer generatedId = HelperCreateDTO(sessionFactory.openSession());
        CdDAO cdDAO = new CdDAO(sessionFactory);
        CdDTO cd = cdDAO.read(generatedId);

        // entry is deleted regardless of assertion
        HelperDeleteDTO(sessionFactory.openSession(), cd);

        System.out.println("Expected title: Test title");
        System.out.println("Actual title: " + cd.getTitle());
        assertEquals("Test title", cd.getTitle());

        System.out.println("Expected artist: Test artist");
        System.out.println("Actual artist: " + cd.getArtist());
        assertEquals("Test artist", cd.getArtist());

        System.out.println("Expected numOfDiscs: 10");
        System.out.println("Actual numOfDiscs: " + cd.getNumOfDiscs());
        assertEquals(10, cd.getNumOfDiscs());
    }

    @Test
    @Override
    public void testUpdate() {
        Integer generatedId = HelperCreateDTO(sessionFactory.openSession());
        CdDAO cdDAO = new CdDAO(sessionFactory);
        Session session = sessionFactory.openSession();

        CdDTO dto = session.get(CdDTO.class, generatedId);
        dto.updateStock(15);

        cdDAO.update(dto);

        session.refresh(dto);

        CdDTO updatedDto = session.get(CdDTO.class, generatedId);
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
        CdDAO cdDAO = new CdDAO(sessionFactory);
        Session session = sessionFactory.openSession();

        CdDTO dto = session.get(CdDTO.class, generatedId);
        CdDTO dto2 = session.get(CdDTO.class, generatedId2);

        dto.updateStock(15);
        dto2.updateStock(15);

        List<CdDTO> updatedDtos = new ArrayList<>();
        updatedDtos.add(dto);
        updatedDtos.add(dto2);

        cdDAO.update(updatedDtos);

        session.refresh(dto);
        session.refresh(dto2);

        CdDTO updatedDto = session.get(CdDTO.class, generatedId);
        CdDTO updatedDto2 = session.get(CdDTO.class, generatedId2);

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
        CdDAO cdDAO = new CdDAO(sessionFactory);

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        CdDTO cdToDelete = session.get(CdDTO.class, generatedId);
        if (cdToDelete != null) {
            cdDAO.delete(cdToDelete);
        }

        tx.commit();
        session.close();

        CdDTO deletedCd = cdDAO.read(generatedId);

        System.out.println("Expected object: null");
        System.out.println("Actual object: " + deletedCd);
        assertNull(deletedCd);
    }
}
