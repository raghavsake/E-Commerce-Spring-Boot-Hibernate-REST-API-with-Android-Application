package com.ecommerce.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ecommerce.model.Product;

@Repository
public class CartDAOImpl implements CartDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Product> getAllProducts(int userId) {
		System.out.println("All Products");
		List<Product> p=new ArrayList<>();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		String sql = String.format("SELECT product.id,"
				+ "product.product_name, product.price, product.image, product.category,product.quantity,"
				+ "product.supplier FROM product JOIN User JOIN cart "
				+ "ON cart.product_id = product.id AND cart.user_id = user.id "
				+ "WHERE user_id = %s",userId);
		
		Query query = entityManager.createNativeQuery(sql);
		
		 @SuppressWarnings("unchecked")
		List<Object> rows=query.getResultList();
		 
		for(Object row : rows){
			Object[] r=(Object[])row;
			Product a=new Product(
					Integer.parseInt(r[0].toString()),
					r[1].toString(),
					Double.parseDouble(r[2].toString()),
					Integer.parseInt(r[5].toString()),
					r[6].toString(),
					r[4].toString(),
					r[3].toString()
					);
			a.setIsInCart(1);
			p.add(a);
			
		}
		session.getTransaction().commit();
		session.close();
		return p;
	}

	@Override
	public void addProduct(int userId, int productId) {
		System.out.println("Add Products");
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		String sql = String.format( "INSERT INTO Cart(user_Id, product_Id) VALUES(%s, %s);",
				userId,productId);
		session.createSQLQuery(sql).executeUpdate();
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public void deleteProduct(int userId, int productId) {	
		System.out.println("Delete Products");

		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		String sql = String.format("DELETE FROM cart WHERE user_id = %s and product_id = %s;",
				userId,productId);
		session.createSQLQuery(sql).executeUpdate();
		session.getTransaction().commit();
		session.close();
	}

}
