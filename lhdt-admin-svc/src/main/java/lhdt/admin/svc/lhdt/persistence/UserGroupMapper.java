package lhdt.admin.svc.lhdt.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import lhdt.admin.svc.lhdt.domain.UserGroup;
import lhdt.admin.svc.lhdt.domain.UserGroupMenu;
import lhdt.admin.svc.lhdt.domain.UserGroupRole;
import lhdt.ds.common.config.LhdtConnMapper;

@LhdtConnMapper
public interface UserGroupMapper {

	/**
     * 사용자 그룹 목록
     * TODO API
     * @return
     */
	@Select("/* getListUserGroup */ WITH RECURSIVE user_group_tree ( user_group_id, user_group_key, user_group_name, ancestor, parent, depth, view_order, basic, available, children, description, insert_date, path, cycle ) AS ( SELECT A.user_group_id, A.user_group_key, A.user_group_name, A.ancestor, A.parent, A.depth, A.view_order, A.basic, A.available, A.children, A.description, A.insert_date, ARRAY[A.view_order], false FROM user_group A WHERE A.parent = 0 UNION ALL SELECT A.user_group_id, A.user_group_key, A.user_group_name, A.ancestor, A.parent, A.depth, A.view_order, A.basic, A.available, A.children, A.description, A.insert_date, path || A.view_order, A.user_group_id = ANY(path) FROM user_group A, user_group_tree B WHERE A.parent = B.user_group_id AND not cycle ) SELECT user_group_id, user_group_key, user_group_name, ancestor, parent, depth, view_order, basic, available, children, description, insert_date, path FROM user_group_tree ORDER BY path ")
    List<UserGroup> getListUserGroup();

    /**
     * 사용자 그룹 정보 조회 조회
     * @param userGroup
     * @return
     */
    UserGroup getUserGroup(UserGroup userGroup);

    /**
     * 기본 사용자 그룹 정보 조회
     * @return
     */
    UserGroup getBasicUserGroup();

    /**
     * 부모와 표시 순서로 메뉴 조회
     * @param userGroup
     * @return
     */
    UserGroup getUserGroupByParentAndViewOrder(UserGroup userGroup);
    
    /**
     * 조상이 같은 사용자 그룹 아이디 목록
     * @param userGroupId
     * @return
     */
    List<Integer> getUserGroupIdByAncestor(Integer userGroupId);
    
    /**
     * 부모가 같은 사용자 그룹 아이디 목록
     * @param userGroupId
     * @return
     */
    List<Integer> getUserGroupIdByParent(Integer userGroupId);

    /**
     * 사용자 그룹 Key 중복 확인
     * @param userGroup
     * @return
     */
    Boolean isUserGroupKeyDuplication(UserGroup userGroup);

	/**
	 * 사용자 그룹 메뉴 권한 목록
	 * TODO API
	 * @param userGroupMenu
	 * @return
	 */
    @Select({"/* getListUserGroupMenu */ WITH RECURSIVE menu_tree (user_group_menu_id, user_group_id, all_yn, read_yn, write_yn, update_yn, delete_yn, menu_id, menu_type, menu_target, name, name_en, ancestor, parent, depth, view_order, url, url_alias, html_id, html_content_id, image, image_alt, css_class, default_yn, use_yn, display_yn, description, insert_date, PATH, cycle) AS (\r\n" + 
    		"SELECT X.user_group_menu_id, X.user_group_id, X.all_yn, X.read_yn, X.write_yn, X.update_yn, X.delete_yn, X.menu_id, X.menu_type, X.menu_target, X.name, X.name_en, X.ancestor, X.parent, X.depth, X.view_order, X.url, X.url_alias, X.html_id, X.html_content_id, X.image, X.image_alt, X.css_class, X.default_yn, X.use_yn, X.display_yn, X.description, X.insert_date, ARRAY[X.view_order], FALSE\r\n" + 
    		"FROM (\r\n" + 
    		"SELECT A.user_group_menu_id, A.user_group_id, COALESCE(A.all_yn, 'N') AS all_yn, COALESCE(A.read_yn, 'N') AS read_yn, COALESCE(A.write_yn, 'N') AS write_yn, COALESCE(A.update_yn, 'N') AS update_yn, COALESCE(A.delete_yn, 'N') AS delete_yn, B.menu_id, B.menu_type, B.menu_target, B.name, B.name_en, B.ancestor, B.parent, B.depth, B.view_order, B.url, B.url_alias, B.html_id, B.html_content_id, B.image, B.image_alt, B.css_class, B.default_yn, B.use_yn, B.display_yn, B.description, B.insert_date\r\n" + 
    		"FROM user_group_menu A, menu B\r\n" + 
    		"WHERE user_group_id = #{userGroupId} AND A.menu_id = B.menu_id AND B.parent = 0 AND B.menu_target = #{menuTarget} AND B.use_yn = #{useYn}) X UNION ALL\r\n" + 
    		"SELECT X.user_group_menu_id, X.user_group_id, X.all_yn, X.read_yn, X.write_yn, X.update_yn, X.delete_yn, X.menu_id, X.menu_type, X.menu_target, X.name, X.name_en, X.ancestor, X.parent, X.depth, X.view_order, X.url, X.url_alias, X.html_id, X.html_content_id, X.image, X.image_alt, X.css_class, X.default_yn, X.use_yn, X.display_yn, X.description, X.insert_date, PATH || X.view_order, X.menu_id = ANY(PATH)\r\n" + 
    		"FROM (\r\n" + 
    		"SELECT A.user_group_menu_id, A.user_group_id, COALESCE(A.all_yn, 'N') AS all_yn, COALESCE(A.read_yn, 'N') AS read_yn, COALESCE(A.write_yn, 'N') AS write_yn, COALESCE(A.update_yn, 'N') AS update_yn, COALESCE(A.delete_yn, 'N') AS delete_yn, B.menu_id, B.menu_type, B.menu_target, B.name, B.name_en, B.ancestor, B.parent, B.depth, B.view_order, B.url, B.url_alias, B.html_id, B.html_content_id, B.image, B.image_alt, B.css_class, B.default_yn, B.use_yn, B.display_yn, B.description, B.insert_date\r\n" + 
    		"FROM user_group_menu A, menu B\r\n" + 
    		"WHERE user_group_id = #{userGroupId} AND A.menu_id = B.menu_id AND B.menu_target = #{menuTarget} AND B.use_yn = #{useYn}) X, menu_tree Y\r\n" + 
    		"WHERE X.parent = Y.menu_id AND NOT cycle)\r\n" + 
    		"SELECT user_group_menu_id, user_group_id, all_yn, read_yn, write_yn, update_yn, delete_yn, menu_id, menu_type, menu_target, name, name_en, ancestor, parent, depth, view_order, url, url_alias, html_id, html_content_id, image, image_alt, css_class, default_yn, use_yn, display_yn, description, insert_date\r\n" + 
    		"FROM menu_tree\r\n" + 
    		"ORDER BY PATH"})
	List<UserGroupMenu> getListUserGroupMenu(UserGroupMenu userGroupMenu);

	/**
	 * 사용자 그룹 Role 목록
	 * @param userGroupRole
	 * @return
	 */
	List<UserGroupRole> getListUserGroupRole(UserGroupRole userGroupRole);

	/**
	 * 사용자 그룹 Role Key 목록
	 * @param userGroupRole
	 * @return
	 */
	@Select({"select * from user_group_role"})
	List<String> getListUserGroupRoleKey(UserGroupRole userGroupRole);

    /**
     * 사용자 그룹 등록
     * @param userGroup
     * @return
     */
    int insertUserGroup(UserGroup userGroup);

	/**
	 * 사용자 그룹 메뉴 등록
	 * @param userGroupMenu
	 * @return
	 */
	int insertUserGroupMenu(UserGroupMenu userGroupMenu);

	/**
	 * 사용자 그룹 Role 등록
	 * @param userGroupRole
	 * @return
	 */
	int insertUserGroupRole(UserGroupRole userGroupRole);

	/**
	 * 사용자 그룹 수정
	 * @param userGroup
	 * @return
	 */
	int updateUserGroup(UserGroup userGroup);

	/**
	 * 사용자 그룹 표시 순서 수정 (up/down)
	 * @param userGroup
	 * @return
	 */
	int updateUserGroupViewOrder(UserGroup userGroup);

	/**
	 * 사용자 그룹 삭제
	 * @param userGroup
	 * @return
	 */
	int deleteUserGroup(UserGroup userGroup);

	/**
	 * ancestor를 이용하여 데이터 그룹 삭제
	 * @param userGroup
	 * @return
	 */
	int deleteUserGroupByAncestor(UserGroup userGroup);

	/**
	 * parent를 이용하여 데이터 그룹 삭제
	 * @param userGroup
	 * @return
	 */
	int deleteUserGroupByParent(UserGroup userGroup);

	/**
	 * 사용자 그룹 메뉴 삭제
	 * @param userGroupId
	 * @return
	 */
	int deleteUserGroupMenu(Integer userGroupId);

	/**
	 * 사용자 그룹 Role 삭제
	 * @param userGroupId
	 * @return
	 */
	int deleteUserGroupRole(Integer userGroupId);
}
