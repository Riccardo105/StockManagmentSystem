package org.example.model.DAO;

import org.example.model.DTO.EBookDTO;
import org.hibernate.SessionFactory;

public class EBookDAO extends AbstractDAO<EBookDTO> {

    public EBookDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    protected Class<EBookDTO> getDTOClass(){
        return EBookDTO.class;
    }
}
