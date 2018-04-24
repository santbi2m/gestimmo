/**
 * 
 */
package com.ag2m.gestimmo.metier.serviceImpl;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ag2m.gestimmo.metier.dao.RoleDao;
import com.ag2m.gestimmo.metier.dto.RoleDto;
import com.ag2m.gestimmo.metier.entite.Role;
import com.ag2m.gestimmo.metier.mapper.Mapper;
import com.ag2m.gestimmo.metier.service.RoleService;

/**
 * @author mombaye
 *
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleDao roleDao;
	
	@Autowired
	Mapper mapper;
	
	@Transactional(readOnly = true)
	public RoleDto findById(Long id) {
		Role role =  roleDao.findById(Role.class, id);
		return mapper.roleToRoleDto(role);
	}

	@Transactional(readOnly = true)
	public List<RoleDto> findAll() {
		List<Role> results = roleDao.findAll(Role.class);
		return results.stream().map(role -> 
				mapper.roleToRoleDto(role))
				.collect(Collectors.<RoleDto> toList());
	}

	@Transactional
	public RoleDto saveOrUpdate(RoleDto roleDto) {
		Role entite = mapper.roleDtoToRole(roleDto);
		 roleDao.saveOrUpdate(entite);
		 return mapper.roleToRoleDto(entite);
	}

	@Transactional
	public boolean delete(RoleDto roleDto) {
		Role entite = mapper.roleDtoToRole(roleDto);
		return roleDao.delete(entite);
	}		
}
