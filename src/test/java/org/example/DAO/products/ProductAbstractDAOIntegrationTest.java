package org.example.DAO.products;

import org.example.DAO.AbstractDAOIntegrationTest;

public abstract class ProductAbstractDAOIntegrationTest<T> extends AbstractDAOIntegrationTest<T> {

    public abstract void testUpdateList();
}
