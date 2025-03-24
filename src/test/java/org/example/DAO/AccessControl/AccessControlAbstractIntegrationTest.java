package org.example.DAO.AccessControl;

import org.example.DAO.AbstractDAOIntegrationTest;

public abstract class AccessControlAbstractIntegrationTest<T> extends AbstractDAOIntegrationTest<T> {

    public abstract void readAll();
}
