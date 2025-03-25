package org.example.model.DAO.accessControl;

import org.example.model.DTO.AccessControl.ResourceDTO;
import org.hibernate.SessionFactory;

public class ResourceDAO extends AbstractAccessControlDAO<ResourceDTO> {

    public ResourceDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    protected Class<ResourceDTO> getDTOClass() {
        return ResourceDTO.class;
    }
}
