package org.example.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.example.Models.TransportVehicleMission;
import org.example.configuration.SessionFactoryUtil;
import org.hibernate.query.Query;

import java.util.List;

public class TransportVehicleMissionDAO {
    public static List<TransportVehicleMission> getAllTransportVehicleMissions() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            String hql = "FROM TransportVehicleMission";
            Query<TransportVehicleMission> query = session.createQuery(hql, TransportVehicleMission.class);
            return query.list();
        }
    }
    public static double getSumOfPricesForMissions() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            String hql = "SELECT SUM(m.PriceForMission) FROM TransportVehicleMission m";
            Query<Double> query = session.createQuery(hql, Double.class);
            return query.uniqueResult() != null ? query.uniqueResult() : 0.0;
        }
    }
}
