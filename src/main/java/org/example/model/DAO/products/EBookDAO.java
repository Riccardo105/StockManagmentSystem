package org.example.model.DAO.products;
import com.google.inject.Inject;
import org.example.model.DTO.products.EBookDTO;
import org.hibernate.SessionFactory;

public class EBookDAO extends AbstractProductDAO<EBookDTO> {

    @Inject
    public EBookDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    protected Class<EBookDTO> getDTOClass(){
        return EBookDTO.class;
    }
}
