package org.example.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.example.Models.TransportVehicleMission;
import org.example.configuration.SessionFactoryUtil;
import org.hibernate.query.Query;

import java.util.List;

public class TransportVehicleMissionDAO {
    /**
     * Retrieves a list of all transport vehicle missions from the database.
     *
     * @return A list of all transport vehicle missions.
     * @throws org.hibernate.HibernateException If there is an issue with the Hibernate operations.
     *                            Check the nested exceptions for specific details.
     * @since 1.0
     */
    public static List<TransportVehicleMission> getAllTransportVehicleMissions() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            String hql = "FROM TransportVehicleMission";
            Query<TransportVehicleMission> query = session.createQuery(hql, TransportVehicleMission.class);
            return query.list();
        }
    }
    /**
     * Calculates the sum of prices for all transport vehicle missions in the database.
     *
     * @return The sum of prices for all transport vehicle missions. Returns 0.0 if there are no missions.
     * @throws org.hibernate.HibernateException If there is an issue with the Hibernate operations.
     *                            Check the nested exceptions for specific details.
     * @since 1.0
     */
    public static double getSumOfPricesForMissions() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            String hql = "SELECT SUM(m.PriceForMission) FROM TransportVehicleMission m";
            Query<Double> query = session.createQuery(hql, Double.class);
            return query.uniqueResult() != null ? query.uniqueResult() : 0.0;
        }
    }
}
