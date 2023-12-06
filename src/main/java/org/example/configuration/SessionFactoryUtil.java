package org.example.configuration;

import org.example.Models.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
public class SessionFactoryUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            configuration.addAnnotatedClass(Client.class);
            configuration.addAnnotatedClass(Obligation.class);
            configuration.addAnnotatedClass(DriverEmployee.class);
            configuration.addAnnotatedClass(TransportCompany.class);
            configuration.addAnnotatedClass(ClientTransportCompany.class);
            configuration.addAnnotatedClass(TransportVehicle.class);
            configuration.addAnnotatedClass(Qualification.class);
            configuration.addAnnotatedClass(TransportVehicleMission.class);
            ServiceRegistry serviceRegistry
                    = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }

        return sessionFactory;
    }
}
