package org.example.model.DAO.products;

import org.example.model.DTO.products.CdDTO;
import org.hibernate.SessionFactory;

public class CdDAO extends AbstractDAO<CdDTO> {

    public CdDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    protected Class<CdDTO> getDTOClass() {
        return CdDTO.class;
    }
}
