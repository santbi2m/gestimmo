package com.ag2m.gestimmo.metier.daoimpl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import com.ag2m.gestimmo.metier.entite.Identifiant;

@Repository
@CacheConfig(cacheNames={"bien"})
public abstract class AbstractDao<ID extends Serializable, T extends Identifiant<ID>>{

	@Autowired
	SessionFactory session;
	
	public T findById(Class<T> clazz, ID id) {
		return session.getCurrentSession().find(clazz, id);
	}

	@SuppressWarnings("unchecked")
	@Cacheable
	public List<T> findAll(Class<T> clazz) {
		return session.getCurrentSession().createQuery("from "+ clazz.getName()).list();
	}

	@CachePut
	public boolean saveOrUpdate(T t) {
		 session.getCurrentSession().saveOrUpdate(t);
		 return true;
	}

	@CacheEvict
	public boolean delete(T t) {
		session.getCurrentSession().delete(t);
		return true;
	}

}
