package com.nonobank.platformuser.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.nonobank.platformuser.entity.mysqlEntity.Role;
import com.nonobank.platformuser.repository.mysqlRepository.RoleRepository;
import com.nonobank.platformuser.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	RoleRepository roleRepository;

	@Override
	public Role save(Role role) {
		// TODO Auto-generated method stub
		String name = role.getRoleName();
		Role r = roleRepository.findByRoleName(name);
		
		if(role.getId() == null){//新增
			if(null == r){
				role.setOptstatus((short)0);
				role = roleRepository.save(role);
				return role;
			}
		}else{//更新
			if(r == null || r.getId().equals(role.getId())){
				role = roleRepository.save(role);
				return role;
			}
		}
		
		return null;
	}

	@Override
	public Role delete(Role role) {
		// TODO Auto-generated method stub
		role.setOptstatus((short)2);
		return roleRepository.save(role);
	}

	@Override
	public List<Role> getAll() {
		// TODO Auto-generated method stub
		return roleRepository.findByOptstatusNot((short)2);
	}

	@Override
	public Map<String, Object> getAll(int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		Page<Role> page = roleRepository.findByOptstatusNot((short)2, pageable);
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(page.hasContent()){
			map.put("list", page.getContent());
		}
		
		map.put("count", page.getTotalElements());
		return map;
	}

}
