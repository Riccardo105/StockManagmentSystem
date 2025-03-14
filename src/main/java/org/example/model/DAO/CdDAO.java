package org.example.model.DAO;

import org.example.model.DTO.CdDTO;
import org.hibernate.SessionFactory;

public class CdDAO extends AbstractDAO<CdDTO> {

    public CdDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    protected Class<CdDTO> getDTOClass() {
        return CdDTO.class;
    }
}
