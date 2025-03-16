package org.example.DAO.products;
import org.example.model.DAO.products.EBookDAO;
import org.example.model.DTO.products.EBookDTO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/* throughout the tests, the sessionFactory passed to the DAO is taken from
   the @BeforeAll in the AbstractDAUnitTest
 */
public class EBookDAOUnitTest extends AbstractDAOUnitTest<EBookDTO> {

    // session passed by tests
    protected Integer HelperCreateDTO(Session session) {
        EBookDTO ebook = new EBookDTO.Builder()
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
                .setFileSize(10)
                .setFileFormat("Test File Format")
                .setNumPages(10)
                .build();

        Transaction tx = session.beginTransaction();
        Integer generatedId = (Integer) session.save(ebook);
        tx.commit();

        return generatedId;
    }

    protected void HelperDeleteDTO( Session session, EBookDTO dto) {
        Transaction tx = session.beginTransaction();
        session.delete(dto);
        tx.commit();
        session.close();
    }

    @Test
    @Override
    public void testCreate() {
        EBookDAO ebookDao = new EBookDAO(sessionFactory);
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
                .setReleaseDate(Date.valueOf("2023-10-15"))
                .setFileSize(10.0f)
                .setFileFormat("Test File Format")
                .setNumPages(10)
                .build();

        Integer generatedId = ebookDao.create(ebook);

        Session session = sessionFactory.openSession();

        Transaction tx = session.beginTransaction();
        EBookDTO dto = session.get(EBookDTO.class, generatedId);
        tx.commit();
        session.close();

        // entry is deleted regardless of assertion
        HelperDeleteDTO(sessionFactory.openSession(), dto);

        System.out.println("Expected title: " + ebook.getTitle());
        System.out.println("Actual title: " + dto.getTitle());
        assertEquals(ebook.getTitle(), dto.getTitle());

        System.out.println("Expected author: " + ebook.getAuthor());
        System.out.println("Actual author: " + dto.getAuthor());
        assertEquals(ebook.getAuthor(), dto.getAuthor());

        System.out.println("Expected number of pages: " + ebook.getNumPages());
        System.out.println("Actual number of pages: " + dto.getNumPages());
        assertEquals(ebook.getNumPages(), dto.getNumPages());


    }

    @Test
    @Override
    public void testRead(){

        Integer generatedId = HelperCreateDTO(sessionFactory.openSession());
        EBookDAO ebookDao = new EBookDAO(sessionFactory);
        EBookDTO ebook = ebookDao.read( generatedId);

        // entry is deleted regardless of assertion
        HelperDeleteDTO(sessionFactory.openSession(), ebook);

        System.out.println("Expected title: Test title");
        System.out.println("Actual title: " + ebook.getTitle());
        assertEquals("Test title", ebook.getTitle());

        System.out.println("Expected author: Test author");
        System.out.println("Actual author: " + ebook.getAuthor());
        assertEquals("Test author", ebook.getAuthor());

        System.out.println("Expected number of pages: 10");
        System.out.println("Actual number of pages: " + ebook.getNumPages());
        assertEquals(10, ebook.getNumPages());
    }

    @Test
    @Override
    public void testUpdate() {
        Integer generatedId = HelperCreateDTO(sessionFactory.openSession());
        EBookDAO ebookDao = new EBookDAO(sessionFactory);
        Session session = sessionFactory.openSession();

        EBookDTO dto = session.get(EBookDTO.class, generatedId);
        dto.updateStock(15);

        ebookDao.update(dto);

        session.refresh(dto);

        EBookDTO updatedDto = session.get(EBookDTO.class, generatedId);
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
        EBookDAO ebookDao = new EBookDAO(sessionFactory);
        Session session = sessionFactory.openSession();

        EBookDTO dto = session.get(EBookDTO.class, generatedId);
        EBookDTO dto2 = session.get(EBookDTO.class, generatedId2);

        dto.updateStock(15);
        dto2.updateStock(15);

        List<EBookDTO> updatedDtos = new ArrayList<>();
        updatedDtos.add(dto);
        updatedDtos.add(dto2);

        ebookDao.update(updatedDtos);

        session.refresh(dto);
        session.refresh(dto2);

        EBookDTO updatedDto = session.get(EBookDTO.class, generatedId);
        EBookDTO updatedDto2 = session.get(EBookDTO.class, generatedId2);

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
        EBookDAO ebookDao = new EBookDAO(sessionFactory);

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        EBookDTO ebookToDelete = session.get(EBookDTO.class, generatedId);
        if (ebookToDelete != null) {
            ebookDao.delete(ebookToDelete);
        }

        tx.commit();
        session.close();

        EBookDTO deletedEbook = ebookDao.read(generatedId);

        System.out.println("Expected object: null");
        System.out.println("Actual object: " + deletedEbook);
        assertNull(deletedEbook);


    }

}
