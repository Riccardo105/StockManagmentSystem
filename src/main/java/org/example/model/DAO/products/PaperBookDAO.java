package org.example.model.DAO.products;
import com.google.inject.Inject;
import org.example.model.DTO.products.PaperBookDTO;
import org.hibernate.SessionFactory;

public class PaperBookDAO extends AbstractProductDAO<PaperBookDTO> {

    @Inject
    public PaperBookDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    protected Class<PaperBookDTO> getDTOClass(){
        return PaperBookDTO.class;
    }

}
