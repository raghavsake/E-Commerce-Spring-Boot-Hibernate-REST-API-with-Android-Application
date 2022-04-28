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
public class ProductsDAOImpl implements ProductsDAO {

	@Autowired
	SessionFactory sessionFactory;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Product> getProductsByCategory(int userId, String category) {
		List<Product> p=new ArrayList<>();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		String sql = String.format("SELECT product.id, product.product_name,product.price,product.quantity,product.supplier,product.image,product.category, (SELECT IF(COUNT(*) >= 1, TRUE, FALSE) FROM cart WHERE cart.user_id = %s AND cart.product_id = product.id) as isInCart FROM product WHERE category = '%s'" 
,userId,category);
		Query query = entityManager.createNativeQuery(sql);
		
		 @SuppressWarnings("unchecked")
		List<Object> rows=query.getResultList();
		 
		for(Object row : rows){
			Object[] r=(Object[])row;
			Product a=new Product(
					Integer.parseInt(r[0].toString()),
					r[1].toString(),
					Double.parseDouble(r[2].toString()),
					Integer.parseInt(r[3].toString()),
					r[4].toString(),
					r[6].toString(),
					r[5].toString()
					);
			a.setIsInCart(Integer.parseInt(r[7].toString()));
			p.add(a);
			
		}
		session.getTransaction().commit();
		session.close();
		return p;

	}

	@Override
	public List<Product> searchProduct(int userId, String keyword) {
		
			
	    List<Product> p=new ArrayList<>();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		keyword=keyword.toLowerCase();
		String sql = String.format("SELECT product.id,product.product_name,product.price,product.quantity,product.supplier,product.image,product.category,(SELECT IF(COUNT(*) >= 1, TRUE, FALSE) FROM cart WHERE cart.user_id = %s AND cart.product_id = product.id) as isInCart FROM product WHERE product_name LIKE '%%%s%%' OR category LIKE '%%%s%%'"
		    	,userId,keyword,keyword);
		System.out.println(sql);
		Query query = entityManager.createNativeQuery(sql);
		
		 @SuppressWarnings("unchecked")
		List<Object> rows=query.getResultList();
		 
		for(Object row : rows){
			Object[] r=(Object[])row;
			Product a=new Product(
					Integer.parseInt(r[0].toString()),
					r[1].toString(),
					Double.parseDouble(r[2].toString()),
					Integer.parseInt(r[3].toString()),
					r[4].toString(),
					r[6].toString(),
					r[5].toString()
					);
			a.setIsInCart(Integer.parseInt(r[7].toString()));
			p.add(a);
			
		}
		session.getTransaction().commit();
		session.close();
		return p;
	}

	@Override
	public void insertProduct(String product_ame, double price, int quantity, String supplier, String category,
			String image) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		String sql = String.format( "INSERT INTO product(product_name, price, quantity, supplier, category, image) VALUES('%s', %s, %s, '%s', '%s','%s')",
				product_ame,price,quantity,supplier,category,image);
		session.createSQLQuery(sql).executeUpdate();
		session.getTransaction().commit();
		session.close();
		
	}

}
