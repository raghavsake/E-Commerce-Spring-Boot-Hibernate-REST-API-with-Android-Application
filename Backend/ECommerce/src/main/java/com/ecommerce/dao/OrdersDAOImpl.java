package com.ecommerce.dao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ecommerce.model.Order;

@Repository
public class OrdersDAOImpl implements OrdersDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void addOrder(String status, String name_on_card, String card_number, String expiration_date, int userId,
			int productId) {
		
		String date="";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
		LocalDateTime now = LocalDateTime.now();  
		date=dtf.format(now);
		
		int order_number=(int)(Math.random()*100000);
		
		String sql = String.format("INSERT INTO Ordering(order_number,order_date ,status,name_on_card, card_number,expiration_date,user_id, product_id) VALUES(%s,'%s','%s','%s','%s','%s',%s,%s)",
				order_number,date,status,name_on_card,card_number,expiration_date,userId,productId);
		
		System.out.println(sql);
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		session.createSQLQuery(sql).executeUpdate();
		session.getTransaction().commit();
		session.close();

	}

	@Override
	public List<Order> getAllOrders(int userId) {
		List<Order> o=new ArrayList<>();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		String sql = String.format("SELECT DISTINCT Ordering.order_number,"
				+ "Ordering.order_date As order_date, "
				+ "Ordering.status,Product.product_name,"
				+ "Product.price,"
				+ "Product.id,"
				+ "User.name,"
				+ "Shipping.address FROM ordering JOIN product JOIN user JOIN shipping ON Ordering.product_id = product.id AND Ordering.user_id = user.id AND Ordering.product_id = Shipping.product_id"
				+ " WHERE Ordering.user_id =  %s",userId);
		
		Query query = entityManager.createNativeQuery(sql);
		
		 @SuppressWarnings("unchecked")
		List<Object> rows=query.getResultList();
		 
		 		 
		for(Object row : rows){
			Object[] r=(Object[])row;
			Order a=new Order(
					Integer.parseInt(r[5].toString()),
					r[3].toString(),
					r[0].toString(),
					r[1].toString(),
					Double.parseDouble(r[4].toString()),
					r[2].toString(),
					r[6].toString(),
					r[7].toString());
			System.out.println(a);
			o.add(a);
			
		}
		session.getTransaction().commit();
		session.close();
		return o;
	}

}
