package org.example.model.DAO.products;

import org.example.model.DAO.AbstractDAO;
import org.example.model.DTO.products.DigitalDTO;
import org.hibernate.SessionFactory;

public class DigitalDAO extends AbstractProductDAO<DigitalDTO> {

    public DigitalDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    protected Class<DigitalDTO> getDTOClass() {
        return DigitalDTO.class;
    }
}
