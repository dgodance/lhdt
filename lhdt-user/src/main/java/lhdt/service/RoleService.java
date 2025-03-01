package lhdt.service;

import java.util.List;

import lhdt.domain.role.Role;

public interface RoleService {
	
	/**
	 * role 수
	 * @param role
	 * @return
	 */
	Long getRoleTotalCount(Role role);

	/**
	 * Role 목록
	 * @param role
	 * @return
	 */
	List<Role> getListRole(Role role);

	/**
	 * Role 정보
	 * @param roleId
	 * @return
	 */
	Role getRole(Integer roleId);
}
