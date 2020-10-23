package lhdt.controller.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import lhdt.controller.AuthorizationController;
import lhdt.domain.*;
import lhdt.domain.board.Board;
import lhdt.domain.board.BoardFileInfo;
import lhdt.domain.board.BoardNoticeFile;
import lhdt.domain.common.Pagination;
import lhdt.domain.policy.Policy;
import lhdt.domain.role.RoleKey;
import lhdt.domain.user.UserSession;
import lhdt.service.*;
import lhdt.support.SQLInjectSupport;
import lhdt.utils.DateUtils;
import lhdt.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController implements AuthorizationController {

	
	@Autowired
	private BoardService boardService;

    @Autowired
    private PolicyService policyService;

    // 파일 copy 시 버퍼 사이즈
    public static final int BUFFER_SIZE = 8192;

    /**
     * board 목록
     */
    @GetMapping(value = "/list")
    public String list(HttpServletRequest request, Board board, @RequestParam(defaultValue="1") String pageNo, Model model) {
    	board.setSearchWord(SQLInjectSupport.replaceSqlInection(board.getSearchWord()));
    	board.setOrderWord(SQLInjectSupport.replaceSqlInection(board.getOrderWord()));

    	log.info("@@ board = {}", board);

    	if(StringUtil.isNotEmpty(board.getStartDate())) {
			board.setStartDate(board.getStartDate().substring(0, 8) + DateUtils.START_TIME);
		}
		if(StringUtil.isNotEmpty(board.getEndDate())) {
			board.setEndDate(board.getEndDate().substring(0, 8) + DateUtils.END_TIME);
		}
		long totalCount = boardService.getBoardTotalCount(board);
		
		Pagination pagination = new Pagination(request.getRequestURI(), board.getParameters(),
                totalCount, Long.parseLong(pageNo), board.getListCounter());
		log.info("@@ pagination = {}", pagination);
		
		board.setOffset(pagination.getOffset());
		board.setLimit(pagination.getPageRows());
		List<Board> boardList = new ArrayList<Board>();
		if(totalCount > 0l) {
			boardList = boardService.getListBoard(board);
		}
		
		log.info("@@ boardList = {}", boardList);
		
		model.addAttribute(pagination);
        model.addAttribute("totalCount", totalCount);
		model.addAttribute("boardList", boardList);

        return "/board/list";
    }

    /**
     * board 등록
     *
     * @param model
     * @return
     */
    @GetMapping(value = "/input")
    public String input(HttpServletRequest request, Model model) {
        String roleCheckResult = roleValidate(request);
        if(roleCheckResult != null) return roleCheckResult;

        Policy policy = policyService.getPolicy();

        model.addAttribute("policy", policy);
        model.addAttribute("board", new Board());

        return "/board/input";
    }

    /**
     * board 수정
     *
     * @param model
     * @return
     */
    @GetMapping(value = "/modify")
    public String modify(HttpServletRequest request, @RequestParam Integer boardId, Model model) {
    	Long boardNoticeId = Long.valueOf(boardId);
    	String roleCheckResult = roleValidate(request);
        if(roleCheckResult != null) return roleCheckResult;

        Policy policy = policyService.getPolicy();
        Board board = boardService.getBoard(boardNoticeId);

        Timestamp timestamp_start = java.sql.Timestamp.valueOf(board.getStartDate().substring(0, 19));
        Timestamp timestamp_end = java.sql.Timestamp.valueOf(board.getEndDate().substring(0, 19));
        
        String startDate = new SimpleDateFormat("yyyyMMddHHmmss").format(timestamp_start);
        String endDate = new SimpleDateFormat("yyyyMMddHHmmss").format(timestamp_end);
        
        board.setStart_day(startDate.substring(0, 8));
        board.setEnd_day(endDate.substring(0, 8));
        board.setStart_hour(startDate.substring(8, 10));
        board.setEnd_hour(endDate.substring(8, 10));
        board.setStart_minute(startDate.substring(10, 12));
        board.setEnd_minute(endDate.substring(10, 12));
        
        List<BoardNoticeFile> boardNoticeFileList = boardService.getBoardNoticeFiles(boardNoticeId);

        log.info("@@ board = {}", board);
        model.addAttribute("policy", policy);
        model.addAttribute("board", board);
        model.addAttribute("boardNoticeFileList", boardNoticeFileList);

        // 파일업로드로 레이어를 등록한 경우
		/*
		 * if (LayerInsertType.UPLOAD ==
		 * LayerInsertType.valueOf(layer.getLayerInsertType().toUpperCase())) {
		 * List<LayerFileInfo> layerFileInfoList =
		 * layerFileInfoService.getListLayerFileInfo(layerId); LayerFileInfo
		 * layerFileInfo = new LayerFileInfo(); for (LayerFileInfo fileInfo :
		 * layerFileInfoList) { if (ShapeFileExt.SHP ==
		 * ShapeFileExt.valueOf(fileInfo.getFileExt().toUpperCase())) { layerFileInfo =
		 * fileInfo; } } model.addAttribute("layerFileInfo", layerFileInfo);
		 * model.addAttribute("layerFileInfoList", layerFileInfoList);
		 * model.addAttribute("layerFileInfoListSize", layerFileInfoList.size());
		 * 
		 * return "/board/modify-upload"; } else { //geoserver 레이어를 등록한 경우 return
		 * "/board/modify-geoserver"; }
		 */
        
        return "/board/modify";
    }

    /**
     * layer 지도 보기
     *
     * @param model
     * @return
     */
    @GetMapping(value = "/{boardId}")
    public String viewLayerMap(HttpServletRequest request, @PathVariable Long boardId, Model model) {
        log.info("@@ boardId = {}", boardId);

        Board board = boardService.getBoard(boardId);
        List<BoardNoticeFile> boardNoticeFileList = boardService.getBoardNoticeFiles(boardId);

        model.addAttribute("board", board);
        model.addAttribute("boardNoticeFileList", boardNoticeFileList);

        return "/board/popup-board";
    }

    private String roleValidate(HttpServletRequest request) {
        UserSession userSession = (UserSession) request.getSession().getAttribute(Key.USER_SESSION.name());
        int httpStatusCode = getRoleStatusCode(userSession.getUserGroupId(), RoleKey.ADMIN_LAYER_MANAGE.name());
        if (httpStatusCode > 200) {
            log.info("@@ httpStatusCode = {}", httpStatusCode);
            request.setAttribute("httpStatusCode", httpStatusCode);
            return "/error/error";
        }

        return null;
    }
}
