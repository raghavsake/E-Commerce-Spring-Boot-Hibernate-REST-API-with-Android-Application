package com.ecommerce.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ecommerce.model.Image;
import com.ecommerce.model.LoginApiResponse;
import com.ecommerce.model.RegisterApiResponse;
import com.ecommerce.model.User;

@Repository
public class UsersDAOImpl implements UsersDAO {

	@Autowired
	SessionFactory sessionFactory;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public RegisterApiResponse register(String name, String email, String password, String gender, int age,
			String image) {
		RegisterApiResponse registerApiResponse =null;
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		String sql = String.format("SELECT id FROM user WHERE email = '%s'",email);
		
		Query query = entityManager.createNativeQuery(sql);
		
		 @SuppressWarnings("unchecked")
		List<Object> rows=query.getResultList();
		 
		if (rows.size()>=1)
		{
			registerApiResponse=new RegisterApiResponse(true,"User Already Registered");
		}
		else {
		
		
		sql = String.format( "INSERT INTO user(name, email, password, gender, age, image) VALUES('%s', '%s', '%s', '%s', %s,'%s')",
				name,email,password,gender,age,image);
		session.createSQLQuery(sql).executeUpdate();
		session.getTransaction().commit();

		
		 sql = String.format("SELECT id, name, email, password, if(isAdmin=1,  'true', 'false') as isAdmin FROM user WHERE email = '%s'",email);
		
		 query = entityManager.createNativeQuery(sql);
		
		 Object rows1=query.getSingleResult();	
		 
		 
		 Object[] r=(Object[])rows1;
			User u= new User(
					Integer.parseInt(r[0].toString()),
					r[2].toString(),
					r[3].toString(),
					r[1].toString(),
					Boolean.parseBoolean(r[4].toString()));
		 registerApiResponse=new RegisterApiResponse(false,"Registration done",u);
		}
		 
		 
		session.close();
		
		
		return registerApiResponse;
	}

	@Override
	public LoginApiResponse login(String email, String password) {
		
		LoginApiResponse loginApiResponse=null;
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		String sql = String.format("SELECT id, password, name, email, if(isAdmin=1,  'true', 'false') as isAdmin FROM user WHERE email = '%s'",email);
		
		Query query = entityManager.createNativeQuery(sql);
		
		Object rows1=query.getSingleResult();	
		
		if(query.getResultList().size()==0)
		{
			 loginApiResponse= new LoginApiResponse(true,"Account does not exist");
		}
		else {
		
			Object[] r=(Object[])rows1;
			User u= new User(
					Integer.parseInt(r[0].toString()),
					r[2].toString(),
					r[3].toString(),
					r[1].toString(),
					Boolean.parseBoolean(r[4].toString()));
			
			if(u.getPassword().equals(password)) {
				loginApiResponse=new LoginApiResponse(u.getId(),u.getName(),u.getEmail(),false,"Successful login",u.getPassword(),u.isAdmin());
			}
			else
			{
				loginApiResponse=new LoginApiResponse(true, "Invalid Password");
			}
		 
		}
		session.getTransaction().commit();
		session.close();
				
		return loginApiResponse;
	}

	@Override
	public void deleteUser(int userId) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		String sql = String.format("DELETE FROM user WHERE id = %s",userId);
		session.createSQLQuery(sql).executeUpdate();
		session.getTransaction().commit();
		session.close();		
	}

	@Override
	public void updatePassword(int userId, String password) {
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		String sql = String.format("UPDATE user SET password = '%s' WHERE id = %s",password,userId);
		session.createSQLQuery(sql).executeUpdate();
		session.getTransaction().commit();
		session.close();
		
	}

	@Override
	public void updateImage(int userId, String image) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		String sql = String.format("UPDATE user SET image = '%s' WHERE id = %s",image,userId);
		session.createSQLQuery(sql).executeUpdate();
		session.getTransaction().commit();
		session.close();
		
	}

	@Override
	public Image getImage(int userId) {
		String result="";
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		String sql = String.format("SELECT image FROM user WHERE id = %s",userId);
		
		Query query = entityManager.createNativeQuery(sql);
		
		Object row=query.getSingleResult();
		Object[] r=(Object[])row;
		result=r[0].toString();
		
		
		
		session.getTransaction().commit();
		session.close();
		return new Image(false,"Setting Image",result);
	}

}
