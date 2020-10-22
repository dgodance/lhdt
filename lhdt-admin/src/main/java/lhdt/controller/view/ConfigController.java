package lhdt.controller.view;

import lhdt.controller.AuthorizationController;
import lhdt.domain.menu.Menu;
import lhdt.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/config")
public class ConfigController implements AuthorizationController {

	
	@Autowired
	private MenuService menuService;
	
    /**
     * menu-icon
     */
    @GetMapping(value = "/menu/icon")
    public String list(HttpServletRequest request, Model model) {
    	
    	List<Menu> menuList = menuService.getMenuIcon();
    	model.addAttribute("menuList", menuList);

        return "/config/menu-icon";
    }

    
}
