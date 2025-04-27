package org.example.model.DAO.products;

import org.example.model.DTO.products.CdDTO;
import org.hibernate.SessionFactory;
import com.google.inject.Inject;

public class CdDAO extends AbstractProductDAO<CdDTO> {

    @Inject
    public CdDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    protected Class<CdDTO> getDTOClass() {
        return CdDTO.class;
    }
}
