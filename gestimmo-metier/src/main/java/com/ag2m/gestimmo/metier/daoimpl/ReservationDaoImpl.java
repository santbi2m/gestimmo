/**
 * 
 */
package com.ag2m.gestimmo.metier.daoimpl;


import org.springframework.stereotype.Repository;

import com.ag2m.gestimmo.metier.dao.ReservationDao;
import com.ag2m.gestimmo.metier.entite.Reservation;

/**
 * @author mombaye
 *
 */
@Repository
public class ReservationDaoImpl extends AbstractDao<Long, Reservation> implements ReservationDao {

}
