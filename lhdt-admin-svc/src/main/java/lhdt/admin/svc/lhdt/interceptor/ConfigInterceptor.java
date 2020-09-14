package lhdt.admin.svc.lhdt.interceptor;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;

import lhdt.admin.svc.lhdt.domain.CacheManager;
import lhdt.admin.svc.lhdt.domain.Key;
import lhdt.admin.svc.lhdt.domain.Menu;
import lhdt.admin.svc.lhdt.domain.Policy;
import lhdt.admin.svc.lhdt.domain.UserGroupMenu;
import lhdt.admin.svc.lhdt.domain.UserSession;
import lhdt.admin.svc.lhdt.domain.YOrN;
import lhdt.cmmn.misc.CmmnConst;
import lhdt.cmmn.misc.CmmnUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 사이트 전체 설정 관련 처리를 담당
 *  
 * @author jeongdae
 *
 */
@Slf4j
@Component
public class ConfigInterceptor extends HandlerInterceptorAdapter {
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    	
    	String uri = request.getRequestURI();
    	HttpSession session = request.getSession();
    	
    	Policy policy = CacheManager.getPolicy();
    	
    	// TODO 너무 비 효율 적이다. 좋은 방법을 찾자.
    	// 세션이 존재하지 않는 경우
    	UserSession userSession = (UserSession)session.getAttribute(Key.USER_SESSION.name());
    	if(userSession != null && userSession.getUserId() != null && !"".equals(userSession.getUserId())) {
	    	List<UserGroupMenu> userGroupMenuList = CacheManager.getUserGroupMenuList(userSession.getUserGroupId());	    	
	    	Integer clickParentId = null;
			Integer clickMenuId = null;
			Integer clickDepth = null;

			if(null != userGroupMenuList) {	//FIXME
				for(UserGroupMenu userGroupMenu : userGroupMenuList) {
					if(uri.equals(userGroupMenu.getUrl())) {
						clickMenuId = userGroupMenu.getMenuId();
						if(userGroupMenu.getDepth() == 1) {
							clickParentId = userGroupMenu.getMenuId();
						} else {
							clickParentId = Integer.valueOf(userGroupMenu.getParent().toString());
						}
						clickDepth = userGroupMenu.getDepth();
						
						if( userGroupMenu.getDepth() == 1 && (uri.indexOf("/main/index")>=0) ) {
							break;
						} else if( userGroupMenu.getDepth() == 2) {
							break;
						} else {
							// pass
						}
					}
				}
				
			}
			
			Menu menu = CacheManager.getMenuMap().get(clickMenuId);
			Menu parentMenu = CacheManager.getMenuMap().get(clickParentId);
			if(menu != null) {
				if(CmmnConst.Y.equals(menu.getDisplayYn())) {
					menu.setAliasName(null);
					parentMenu.setAliasName(null);
				} else {
					Integer aliasMenuId = CacheManager.getMenuUrlMap().get(menu.getUrlAlias());
					Menu aliasMenu = CacheManager.getMenuMap().get(aliasMenuId);
					menu.setAliasName(aliasMenu.getName());
					parentMenu.setAliasName(aliasMenu.getName());
				}
			}
			
//			Integer contentLoadBalancingIntervalValue = policy.getContent_load_balancing_interval().intValue() * 1000;
//			request.setAttribute("contentLoadBalancingInterval", contentLoadBalancingIntervalValue);
			
			request.setAttribute("clickMenuId", clickMenuId);
//			request.setAttribute("clickParentId", clickParentId);
//			request.setAttribute("clickDepth", clickDepth);
			request.setAttribute("menu", menu);
			request.setAttribute("parentMenu", parentMenu);
			
			request.setAttribute("cacheUserGroupMenuList", userGroupMenuList);
			request.setAttribute("cacheUserGroupMenuListSize", (null != userGroupMenuList ? userGroupMenuList.size() : 0));	//FIXME
			request.setAttribute("contentCacheVersion", policy.getContentCacheVersion());
    	}
    	
        return true;
    }
}
