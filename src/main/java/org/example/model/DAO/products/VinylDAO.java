package org.example.model.DAO.products;
import com.google.inject.Inject;
import org.example.model.DTO.products.VinylDTO;
import org.hibernate.SessionFactory;

public class VinylDAO extends AbstractProductDAO<VinylDTO> {

    @Inject
    public VinylDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    protected Class<VinylDTO> getDTOClass() {
        return VinylDTO.class;
    }
}
