package com.greatlearning.student.services;
import java.util.List;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.Transactional;

import com.greatlearning.student.models.Student;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.SharedSessionContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository
public class StudentService {
	private SessionFactory sessionFactory;
	private SessionFactory session;
	@Autowired
	public StudentService(SessionFactory sessionFactory) {
		this.sessionFactory=sessionFactory;
		try {
		this.session=(SessionFactory) sessionFactory.getCurrentSession();
		}
		catch(HibernateException e){
			this.session=(SessionFactory) sessionFactory.openSession();
		}
	}
	public List<Student> findAll(){
	
		List<Student> students=((Session) session).createQuery("from Students").list();
		return students;
	}
	public Student findById(int theId) {
		Student student = new Student();
		student = ((Session) session).get(Student.class, theId);
		return student;
	}
	@Transactional
	public void save(Student theStudent) throws SecurityException, RollbackException, HeuristicMixedException, HeuristicRollbackException, SystemException {
	Transaction tx = (Transaction) ((SharedSessionContract) session).beginTransaction();	
	((Session) session).saveOrUpdate(theStudent);
	tx.commit();
	}
	@Transactional
	public void deleteById(int theId) throws SecurityException, RollbackException, HeuristicMixedException, HeuristicRollbackException, SystemException {
		Transaction tx =(Transaction) ((SharedSessionContract) session).beginTransaction();
		Student student = ((Session) session).get(Student.class, theId);
		((Session) session).delete(student);
		tx.commit();
	}

}
