package com.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * 
 * @author Cassio Lemos
 *
 */
public class EntityManagerUtil {
	private static EntityManagerFactory emf;
	 
    public static EntityManager getEntityManager() {
         if (emf == null){
                  emf = Persistence.createEntityManagerFactory("controlehoras");
         }
         return emf.createEntityManager();
    }
}
