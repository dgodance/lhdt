package lhdt.service;

import java.util.List;

import lhdt.domain.board.Board;
import lhdt.domain.board.BoardNoticeComment;
import lhdt.domain.board.BoardNoticeFile;

/**
 * 게시판
 * @author jeongdae
 *
 */
public interface BoardService {
	
	/**
	 * 게시물 총 건수
	 * @param board
	 * @return
	 */
	Long getBoardTotalCount(Board board);
	
	/**
	 * 게시물 목록
	 * @param board
	 * @return
	 */
	List<Board> getListBoard(Board board);
	
	/**
	 * 게시물 Comment 목록
	 * @param boardId
	 * @return
	 */
	List<BoardNoticeComment> getListBoardNoticeComment(Long boardId);
	
	/**
	 * 게시물 Comment 목록
	 * @param boardNoticeCommentId
	 * @return
	 */
	List<BoardNoticeComment> getListBoardNoticeCommentByParent(Long boardNoticeCommentId);
	
	/**
	 * 게시물 정보
	 * @param board_id
	 * @return
	 */
	Board getBoard(Long board_id);
	
	/**
	 * 게시물 정보(수정용)
	 * @param board_id
	 * @return
	 */
	Board getBoardForModify(Long board_id);
	
	
	/**
	 * 게시물 Comment 정보
	 * @param board_comment_id
	 * @return
	 */
	BoardNoticeComment getBoardNoticeComment(Long boardCommentNoticeId);
	
	/**
	 * 게시물 등록
	 * @param board
	 * @return
	 */
	int insertBoard(Board board, List<BoardNoticeFile> boardNoticeFileList, Boolean fileExist);
	
	/**
	 * 게시물 Comment 등록
	 * @param boardComment
	 * @return
	 */
	int insertBoardNoticeComment(BoardNoticeComment boardNoticeComment);
	
	/**
	 * 게시물 more Comment 등록
	 * @param boardComment
	 * @return
	 */
	int insertBoardNoticeMoreComment(BoardNoticeComment boardNoticeComment);
	
	/**
	 * 게시물 수정
	 * @param board
	 * @return
	 */
	int updateBoard(Board board, List<BoardNoticeFile> boardNoticeFileList, Boolean fileExist);
	
	/**
	 * 게시물 Comment 수정
	 * @param boardComment
	 * @return
	 */
	int updateBoardComment(BoardNoticeComment boardNoticeComment);
	
	/**
	 * 게시물 삭제
	 * @param board_id
	 * @return
	 */
	int deleteBoard(Long board_id);
	
	/**
	 * 게시물 파일 삭제
	 * @param board_notice_file_id
	 * @return
	 */
	int deleteBoardNoticeFile(Long board_notice_file_id);
	
	/**
	 * 게시물 Comment 삭제
	 * @param board_comment_id
	 * @return
	 */
	int deleteBoardComment(Long board_comment_id);
	
	/**
	 * 게시물 Comment 일괄 삭제
	 * @param board_id
	 * @return
	 */
	int deleteBoardCommentByBoardId(Long board_id);
	
	/**
	 * 게시물 file 불러오기
	 * @param board_id
	 * @return
	 */
	BoardNoticeFile getBoardNoticeFile(Long boardNoticeFileId);
	
	/**
	 * 게시물 fileList 불러오기
	 * @param board_id
	 * @return
	 */
	List<BoardNoticeFile> getBoardNoticeFiles(Long board_id);
	
	
}
