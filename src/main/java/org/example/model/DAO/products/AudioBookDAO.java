package org.example.model.DAO.products;

import org.example.model.DAO.AbstractDAO;
import org.example.model.DTO.products.AudioBookDTO;
import org.hibernate.SessionFactory;

public class AudioBookDAO extends AbstractProductDAO<AudioBookDTO> {

    public AudioBookDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    protected Class<AudioBookDTO> getDTOClass() {
        return AudioBookDTO.class;
    }
}
