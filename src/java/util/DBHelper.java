/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.Serializable;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Cristofer
 */
public class DBHelper {

    Session session;
    Transaction tx;
    private static DBHelper helper;

    public static DBHelper getHelper() {
        if (helper == null) {
            helper = new DBHelper();
        }
        return helper;
    }

    public DBHelper() {
        session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();
    }

    public int add(Object o) throws Exception {
        Integer id = (Integer) session.save(o);
        tx.commit();
        return id;
    }

    public List buscarTodos(Class cla) throws Exception {
        try {
            List res = session.createSQLQuery("SELECT * FROM " + cla.getSimpleName()).addEntity(cla).list();
            if (res.size() > 0) {
                return res;
            }
            return null;
        } catch (Exception e) {
            throw e;
        }
    }

    public List buscar(Class cla, String columna, String valor) {
        try {
            List res = session.createSQLQuery("SELECT * FROM " + cla.getSimpleName() + " WHERE " + columna + " = " + valor).addEntity(cla).list();
            return res;
        } catch (Exception e) {
            throw e;
        }
    }

    public static void main(String[] args) {
        System.out.println(DBHelper.class.getCanonicalName());
        System.out.println(DBHelper.class.getSimpleName());
        System.out.println(DBHelper.class.getName());
    }

}
