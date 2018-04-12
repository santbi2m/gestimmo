package com.ag2m.gestimmo.metier.daoimpl;

import org.springframework.stereotype.Repository;

import com.ag2m.gestimmo.metier.dao.BienDao;
import com.ag2m.gestimmo.metier.entite.Bien;

/**
 * @author mombaye
 *
 */
@Repository
public class BienDaoImpl extends AbstractDao<Long, Bien> implements BienDao {

}
