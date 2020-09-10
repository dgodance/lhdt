package lhdt.admin.svc.lhdt.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import lhdt.admin.svc.lhdt.domain.Menu;
import lhdt.ds.common.config.LhdtConnMapper;


/**
 * 메뉴 처리
 * @author jeongdae
 *
 */
@LhdtConnMapper
public interface MenuMapper {

	/**
	 * 메뉴 목록
	 * @param menu
	 * @return
	 */
	@Select("/* getListMenu */ WITH RECURSIVE menu_tree ( menu_id, menu_type, menu_target, name, name_en, ancestor, parent, depth, view_order, url, url_alias, alias_menu_id, html_id, html_content_id, image, image_alt, css_class, default_yn, use_yn, display_yn, description, insert_date, path, cycle ) AS ( SELECT A.menu_id, A.menu_type, A.menu_target, A.name, A.name_en, A.ancestor, A.parent, A.depth, A.view_order, A.url, A.url_alias, A.alias_menu_id, A.html_id, A.html_content_id, A.image, A.image_alt, A.css_class, A.default_yn, A.use_yn, A.display_yn, A.description, A.insert_date, ARRAY[A.view_order], false FROM menu A WHERE A.menu_target = #{menuTarget} AND A.parent = 0 UNION ALL SELECT A.menu_id, A.menu_type, A.menu_target, A.name, A.name_en, A.ancestor, A.parent, A.depth, A.view_order, A.url, A.url_alias, A.alias_menu_id, A.html_id, A.html_content_id, A.image, A.image_alt, A.css_class, A.default_yn, A.use_yn, A.display_yn, A.description, A.insert_date, path || A.view_order, A.menu_id = ANY(path) FROM menu A, menu_tree B WHERE A.menu_target = #{menuTarget} AND A.parent = B.menu_id AND not cycle ) SELECT menu_id, menu_type, menu_target, name, name_en, ancestor, parent, depth, view_order, url, url_alias, alias_menu_id, html_id, html_content_id, image, image_alt, css_class, default_yn, use_yn, display_yn, description, insert_date, path FROM menu_tree ORDER BY path ")
	List<Menu> getListMenu(Menu menu);

	/**
	 * 메뉴 조회
	 * @param menuId
	 * @return
	 */
	Menu getMenu(Integer menuId);
	
	/**
	 * 부모와 표시 순서로 메뉴 조회
	 * @param menu
	 * @return
	 */
	Menu getMenuByParentAndViewOrder(Menu menu);

	/**
	 * 자식 메뉴 중에 순서가 최대인 메뉴를 검색
	 * @param menu
	 * @return
	 */
	Menu getMaxViewOrderChildMenu(Menu menu);
	
/**
	 * 자식 메뉴 목록
	 * @param parent
	 * @return
	 */
	List<Integer> getListChildMenuId(Integer parent);
	
	/**
	 * 메뉴 등록
	 * @param menu
	 * @return
	 */
	int insertMenu(Menu menu);
	
	/**
	 * 메뉴 수정
	 * @param menu
	 * @return
	 */
	int updateMenu(Menu menu);
	
	/**
	 * 메뉴 위로/아래로 수정
	 * @param menu
	 * @return
	 */
	int updateViewOrderMenu(Menu menu);
	
	/**
	 * 자식 메뉴 일괄 수정
	 * @param parent
	 * @return
	 */
	int updateChildMenu(Integer parent);
	
	/**
	 * 메뉴 삭제
	 * @param menuId
	 * @return
	 */
	int deleteMenu(Integer menuId);
}
