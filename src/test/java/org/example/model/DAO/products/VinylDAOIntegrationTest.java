package org.example.model.DAO.products;

import org.example.model.DTO.products.VinylDTO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class VinylDAOIntegrationTest extends ProductAbstractDAOIntegrationTest<VinylDTO> {

    protected Integer HelperCreateDTO(Session session) {
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

        Transaction tx = session.beginTransaction();
        Integer generatedId = (Integer) session.save(vinyl);
        tx.commit();

        return generatedId;
    }

    protected void HelperDeleteDTO(Session session, VinylDTO dto) {
        Transaction tx = session.beginTransaction();
        session.delete(dto);
        tx.commit();
        session.close();
    }

    @Test
    @Override
    public void testCreate() {
        VinylDAO vinylDAO = new VinylDAO(sessionFactory);
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


        Integer generatedId = vinylDAO.create(vinyl);

        Session session = sessionFactory.openSession();

        Transaction tx = session.beginTransaction();
        VinylDTO dto = session.get(VinylDTO.class, generatedId);
        tx.commit();
        session.close();

        // entry is deleted regardless of assertion
        HelperDeleteDTO(sessionFactory.openSession(), dto);

        System.out.println("Expected title: " + vinyl.getTitle());
        System.out.println("Actual title: " + dto.getTitle());
        assertEquals(vinyl.getTitle(), dto.getTitle());

        System.out.println("Expected artist: " + vinyl.getArtist());
        System.out.println("Actual artist: " + dto.getArtist());
        assertEquals(vinyl.getArtist(), dto.getArtist());

        System.out.println("Expected rpm: " + vinyl.getRpm());
        System.out.println("Actual rpm: " + dto.getRpm());
        assertEquals(vinyl.getRpm(), dto.getRpm());

    }

    @Test
    @Override
    public void testRead() {
        Integer generatedId = HelperCreateDTO(sessionFactory.openSession());
        VinylDAO vinylDAO = new VinylDAO(sessionFactory);
        VinylDTO vinyl = vinylDAO.read(generatedId);

        // entry is deleted regardless of assertion
        HelperDeleteDTO(sessionFactory.openSession(), vinyl);

        System.out.println("Expected title: Test title");
        System.out.println("Actual title: " + vinyl.getTitle());
        assertEquals("Test title", vinyl.getTitle());

        System.out.println("Expected artist: Test artist");
        System.out.println("Actual artist: " + vinyl.getArtist());
        assertEquals("Test artist", vinyl.getArtist());

        System.out.println("Expected rpm: 100");
        System.out.println("Actual rpm: " + vinyl.getRpm());
        assertEquals(100, vinyl.getRpm());
    }

    @Test
    @Override
    public void testUpdate() {
        Integer generatedId = HelperCreateDTO(sessionFactory.openSession());
        VinylDAO vinylDAO = new VinylDAO(sessionFactory);
        Session session = sessionFactory.openSession();

        VinylDTO dto = session.get(VinylDTO.class, generatedId);
        dto.updateStock(15);

        vinylDAO.update(dto);

        session.refresh(dto);

        VinylDTO updatedDto = session.get(VinylDTO.class, generatedId);

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
        VinylDAO vinylDAO = new VinylDAO(sessionFactory);
        Session session = sessionFactory.openSession();

        VinylDTO dto = session.get(VinylDTO.class, generatedId);
        VinylDTO dto2 = session.get(VinylDTO.class, generatedId2);

        dto.updateStock(15);
        dto2.updateStock(15);

        List<VinylDTO> updatedDtos = new ArrayList<>();
        updatedDtos.add(dto);
        updatedDtos.add(dto2);

        vinylDAO.update(updatedDtos);

        session.refresh(dto);
        session.refresh(dto2);

        VinylDTO updatedDto = session.get(VinylDTO.class, generatedId);
        VinylDTO updatedDto2 = session.get(VinylDTO.class, generatedId2);

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
        VinylDAO vinylDAO = new VinylDAO(sessionFactory);

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        VinylDTO vinylToDelete = session.get(VinylDTO.class, generatedId);
        if (vinylToDelete != null) {
            vinylDAO.delete(vinylToDelete);
        }

        tx.commit();
        session.close();

        VinylDTO deletedVinyl = vinylDAO.read(generatedId);

        System.out.println("Expected object: null");
        System.out.println("Actual object: " + deletedVinyl);
        assertNull(deletedVinyl);
    }

    @Test
    public void testDeleteList() {
        Integer generatedId = HelperCreateDTO(sessionFactory.openSession());
        Integer generatedId2 = HelperCreateDTO(sessionFactory.openSession());
        VinylDAO vinylBookDAO = new VinylDAO(sessionFactory);

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        VinylDTO vinylToDelete1 = session.get(VinylDTO.class, generatedId);
        VinylDTO vinylToDelete2 = session.get(VinylDTO.class, generatedId2);

        ArrayList<VinylDTO> vinylToDelete = new ArrayList<>();
        vinylToDelete.add(vinylToDelete1);
        vinylToDelete.add(vinylToDelete2);

        vinylBookDAO.delete(vinylToDelete);

        tx.commit();
        session.close();

        VinylDTO deletedVinyl1 = vinylBookDAO.read(generatedId);
        VinylDTO deletedVinyl2 = vinylBookDAO.read(generatedId2);


        System.out.println("Expected object: null");
        System.out.println("Actual object: " + deletedVinyl1);
        assertNull(deletedVinyl1);

        System.out.println("Expected object: null");
        System.out.println("Actual object: " + deletedVinyl2);
        assertNull(deletedVinyl2);
    }
}
