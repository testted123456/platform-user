package com.nonobank.platformuser.service;

import java.util.List;
import java.util.Map;

import com.nonobank.platformuser.entity.mysqlEntity.Role;

public interface RoleService {
	
	Role save(Role role);
	
	Role delete(Role role);
	
	List<Role> getAll();
	
	public Map<String, Object> getAll(int pageIndex, int pageSize);
}
