package org.example;


import org.example.config.DbConnection;
import org.example.model.DAO.EBookDAO;
import org.example.model.DTO.EBookDTO;
import org.hibernate.HibernateException;
import org.hibernate.Metamodel;
import org.hibernate.SessionFactory;

import java.util.Map;
import java.util.Properties;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        SessionFactory sessionFactory = DbConnection.getSessionFactory();
        System.out.println(sessionFactory);

        Metamodel metamodel = sessionFactory.getMetamodel();
        System.out.println(metamodel);
        if (metamodel.getEntities().isEmpty()) {
            System.out.println("No entities found!"); }
        metamodel.getEntities().forEach(entityType -> {
            System.out.println("Entity: " + entityType.getJavaType().getName());
        });

        // Map<String, Object> props = sessionFactory.getProperties();
        // props.forEach((key, value) -> System.out.println(key + ": " + value));

    }
    }
