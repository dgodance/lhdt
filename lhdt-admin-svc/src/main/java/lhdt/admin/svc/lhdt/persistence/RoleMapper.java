package lhdt.admin.svc.lhdt.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import lhdt.admin.svc.lhdt.domain.Role;
import lhdt.cmmn.config.LhdtConnMapper;

/**
 * Role
 * @author jeongdae
 *
 */
@LhdtConnMapper
public interface RoleMapper {

	/**
	 * role 수
	 * @param role
	 * @return
	 */
	@Select({"SELECT COUNT(role_id) \r\n" + 
			"		FROM role"})
	Long getRoleTotalCount(Role role);

	/**
	 * Role 목록
	 * @param role
	 * @return
	 */
	@Select({"/* getListRole */\r\n" + 
			"		SELECT *\r\n" + 
			"		FROM role "})
	List<Role> getListRole(Role role);

	/**
	 * Role 정보
	 * @param roleId
	 * @return
	 */
	Role getRole(Integer roleId);
	
	/**
	 * Role 등록
	 * @param role
	 * @return
	 */
	int insertRole(Role role);
	
	/**
	 * Role 정보 수정
	 * @param role
	 * @return
	 */
	int updateRole(Role role);
	
	/**
	 * Role 삭제
	 * @param roleId
	 * @return
	 */
	int deleteRole(Integer roleId);
	
}
