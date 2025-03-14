package org.example.model.DAO;

import org.example.model.DTO.DigitalDTO;
import org.hibernate.SessionFactory;

public class DigitalDAO extends AbstractDAO<DigitalDTO> {

    public DigitalDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    protected Class<DigitalDTO> getDTOClass() {
        return DigitalDTO.class;
    }
}
