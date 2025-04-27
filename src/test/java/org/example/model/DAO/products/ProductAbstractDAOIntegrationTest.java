package org.example.model.DAO.products;

import org.example.model.DAO.AbstractDAOIntegrationTest;

public abstract class ProductAbstractDAOIntegrationTest<T> extends AbstractDAOIntegrationTest<T> {

    public abstract void testUpdateList();
}
