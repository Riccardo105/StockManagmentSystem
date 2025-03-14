package org.example.model.DAO;

import org.example.model.DTO.AudioBookDTO;
import org.hibernate.SessionFactory;

public class AudioBookDAO extends AbstractDAO<AudioBookDTO> {

    public AudioBookDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    protected Class<AudioBookDTO> getDTOClass() {
        return AudioBookDTO.class;
    }
}
