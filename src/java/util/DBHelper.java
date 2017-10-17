/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import org.hibernate.Session;

/**
 *
 * @author Cristofer
 */
public class DBHelper {
    Session session;

    public DBHelper() {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
    }
    
    
    
    
}
