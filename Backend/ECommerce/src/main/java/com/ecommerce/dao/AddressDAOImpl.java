package com.ecommerce.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AddressDAOImpl implements AddressDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void addAddress(String address, String city, String country, String zip, String phone, int userId,
			int productId) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		String sql = String.format("INSERT INTO Shipping(address, city ,country, zip,phone,user_id, product_id) VALUES('%s','%s','%s','%s','%s',%s,%s);",
				address,city,country,zip,phone,userId,productId);
		session.createSQLQuery(sql).executeUpdate();
		session.getTransaction().commit();
		session.close();
	}
	
	

}
