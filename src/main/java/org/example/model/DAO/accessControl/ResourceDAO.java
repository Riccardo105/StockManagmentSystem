package org.example.model.DAO.accessControl;

import org.example.model.DTO.AccessControl.ResourceDTO;
import org.hibernate.SessionFactory;

public class ResourceAbstractAccessControlDAO extends AbstractAccessControlDAO<ResourceDTO> {

    public ResourceAbstractAccessControlDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    protected Class<ResourceDTO> getDTOClass() {
        return ResourceDTO.class;
    }
}
