package org.example.model.Service;

import org.example.model.DTO.products.BookDTO;
import org.example.model.DTO.products.MusicDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.ArrayList;


public class ProductSearchService {
    private final SessionFactory sessionFactory;


    public ProductSearchService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // called by Query handlers
    private <T> ArrayList<T> executeQuery(Query<T> query, Session session) {
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            ArrayList<T> results = new ArrayList<>(query.getResultList());
            tx.commit();
            return results;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Failed to perform search operation: ", e);
        }

    }

    private  String ebookQueryBuilder() {
        return "FROM EBookDTO e where e.title like :keyword or e.author like :keyword";
    }

    private  String paperBookQueryBuilder() {
        return "FROM PaperBookDTO p where p.title like :keyword or p.author like :keyword";
    }

    private  String audioBookQueryBuilder() {
        return "FROM AudioBookDTO a where a.title like :keyword or a.author like :keyword";
    }

    private String cdQueryBuilder() {
        return "FROM CdDTO c where c.title like :keyword or c.artist like :keyword";
    }

    private String digitalQueryBuilder() {
        return "FROM DigitalDTO d where d.title like :keyword or d.artist like :keyword ";
    }

    private String vinylQueryBuilder() {
        return "FROM VinylDTO v where v.title like :keyword or v.artist like :keyword";
    }

    /**
     * category chosen by user in search bar (mandatory)
     * @param format chose by user (optional)
     * @param keyword inputted by the user (mandatory
     * @return list of found books
     */
    public ArrayList<BookDTO> QueryBooks(String format, String keyword){
        Session session = sessionFactory.openSession();
        ArrayList<BookDTO> results = new ArrayList<>();

        Query<BookDTO> ebookQuery = session.createQuery(ebookQueryBuilder(), BookDTO.class);
        Query<BookDTO> paperBookQuery = session.createQuery(paperBookQueryBuilder(), BookDTO.class);
        Query<BookDTO> audioBookQuery = session.createQuery(audioBookQueryBuilder(), BookDTO.class);

        ebookQuery.setParameter("keyword", "%" + keyword + "%");
        paperBookQuery.setParameter("keyword", "%" + keyword + "%");
        audioBookQuery.setParameter("keyword", "%" + keyword + "%");

        if (format == null || format.isEmpty()) {
                results.addAll(executeQuery(ebookQuery, session));
                results.addAll(executeQuery(paperBookQuery, session));
                results.addAll(executeQuery(audioBookQuery, session));
        }else {

            switch (format.toLowerCase()) {
                case "ebook":
                    results.addAll(executeQuery(ebookQuery, session));
                    break;
                case "paperbook":
                    results.addAll(executeQuery(paperBookQuery, session));
                    break;
                case "audiobook":
                    results.addAll(executeQuery(audioBookQuery, session));
            }
        }
        session.close();
        return results;
    }

    /**
     * category chosen by user in search bar (mandatory)
     * @param format chose by user (optional)
     * @param keyword inputted by the user (mandatory
     * @return list of found music
     */
    public ArrayList<MusicDTO> QueryMusic(String format, String keyword){
        Session session = sessionFactory.openSession();
        ArrayList<MusicDTO> results = new ArrayList<>();

        Query<MusicDTO> cdQuery = session.createQuery(cdQueryBuilder(), MusicDTO.class);
        Query<MusicDTO> digitalQuery = session.createQuery(digitalQueryBuilder(), MusicDTO.class);
        Query<MusicDTO> vinylQuery = session.createQuery(vinylQueryBuilder(), MusicDTO.class);

        cdQuery.setParameter("keyword", "%" + keyword + "%");
        digitalQuery.setParameter("keyword", "%" + keyword + "%");
        vinylQuery.setParameter("keyword", "%" + keyword + "%");

        if (format == null || format.isEmpty()) {
            results.addAll(executeQuery(cdQuery, session));
            results.addAll(executeQuery(digitalQuery, session));
            results.addAll(executeQuery(vinylQuery, session));
        }else {

            switch (format.toLowerCase()) {
                case "cd":
                    results.addAll(executeQuery(cdQuery, session));
                    break;
                case "digital":
                    results.addAll(executeQuery(digitalQuery, session));
                    break;
                case "vinyl":
                    results.addAll(executeQuery(vinylQuery, session));
            }
        }
        session.close();
        return results;
    }
}
