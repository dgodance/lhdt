package lhdt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lhdt.domain.board.Board;
import lhdt.domain.board.BoardNoticeComment;
import lhdt.domain.board.BoardNoticeFile;
import lhdt.domain.uploaddata.UploadDataFile;
import lhdt.persistence.BoardMapper;
import lhdt.service.BoardService;
import lhdt.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 게시판
 * 
 * @author jeongdae
 *
 */
@Service
@Slf4j
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardMapper boardMapper;

	/**
	 * 게시물 총 건수
	 * 
	 * @param board
	 * @return
	 */
	@Transactional(readOnly = true)
	public Long getBoardTotalCount(Board board) {
		return boardMapper.getBoardTotalCount(board);
	}

	/**
	 * 게시물 목록
	 * 
	 * @param board
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Board> getListBoard(Board board) {
		return boardMapper.getListBoard(board);
	}

	/**
	 * 게시물 Comment 목록
	 * 
	 * @param board_id
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<BoardNoticeComment> getListBoardNoticeComment(Long boardId) {
		return boardMapper.getListBoardNoticeComment(boardId);
	}
	
	/**
	 * 게시물 Comment 목록
	 * 
	 * @param boardNoticeCommentId
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<BoardNoticeComment> getListBoardNoticeCommentByParent(Long boardNoticeCommentId) {
		return boardMapper.getListBoardNoticeCommentByParent(boardNoticeCommentId);
	}

	/**
	 * 게시물 정보
	 * 
	 * @param board_id
	 * @return
	 */
	@Transactional
	public Board getBoard(Long board_id) {
		Board board = boardMapper.getBoard(board_id);
		boardMapper.updateBoardViewCount(board_id);
		return board;
	}
	
	/**
	 * 게시물 정보(수정용)
	 * 
	 * @param board_id
	 * @return
	 */
	@Transactional
	public Board getBoardForModify(Long board_id) {
		Board board = boardMapper.getBoard(board_id);
		return board;
	}

	/**
	 * 게시물 Comment 정보
	 * 
	 * @param board_comment_id
	 * @return
	 */
	@Transactional(readOnly = true)
	public BoardNoticeComment getBoardNoticeComment(Long boardNoticeCommentId) {
		return boardMapper.getBoardNoticeComment(boardNoticeCommentId);
	}

	/**
	 * 게시물 등록
	 * 
	 * @param board
	 * @return
	 */
	@Transactional
	public int insertBoard(Board board, List<BoardNoticeFile> boardNoticeFileList, Boolean fileExist) {

		log.info("board =============== {} " , board);
		int result = boardMapper.insertBoard(board);
		
		if (fileExist) {
			Long boardNoticeId = board.getBoardNoticeId();
			String userId = board.getUserId();
			for (BoardNoticeFile boardNoticeFile : boardNoticeFileList) {
				boardNoticeFile.setBoardNoticeId(boardNoticeId);
				boardNoticeFile.setUserId(userId);
				boardMapper.insertFile(boardNoticeFile);
				result++;
			}
		}
		return boardMapper.insertBoardDetail(board);
	}

	/**
	 * 게시물 Comment 등록
	 * 
	 * @param boardComment
	 * @return
	 */
	@Transactional
	public int insertBoardNoticeComment(BoardNoticeComment boardNoticeComment) {
		return boardMapper.insertBoardNoticeComment(boardNoticeComment);
	}
	
	/**
	 * 게시물 more Comment 등록
	 * 
	 * @param boardComment
	 * @return
	 */
	@Transactional
	public int insertBoardNoticeMoreComment(BoardNoticeComment boardNoticeComment) {
		return boardMapper.insertBoardNoticeMoreComment(boardNoticeComment);
	}
	

	/**
	 * 게시물 수정
	 * 
	 * @param board
	 * @return
	 */
	@Transactional
	public int updateBoard(Board board, List<BoardNoticeFile> boardNoticeFileList, Boolean fileExist) {
					
		int result = boardMapper.updateBoard(board);
		
		log.info("board =============== {} " , board);
		if (fileExist) {
			Long boardNoticeId = board.getBoardNoticeId();
			String userId = board.getUserId();
			for (BoardNoticeFile boardNoticeFile : boardNoticeFileList) {
				boardNoticeFile.setBoardNoticeId(boardNoticeId);
				boardNoticeFile.setUserId(userId);
				boardMapper.insertFile(boardNoticeFile);
				result++;
			}
		}
		return boardMapper.updateBoardDetail(board);
	}

	/**
	 * 게시물 Comment 수정
	 * 
	 * @param boardComment
	 * @return
	 */
	@Transactional
	public int updateBoardComment(BoardNoticeComment boardComment) {
		return boardMapper.updateBoardComment(boardComment);
	}

	/**
	 * 게시물 삭제
	 * 
	 * @param board_id
	 * @return
	 */
	@Transactional
	public int deleteBoard(Long board_id) {
		return boardMapper.deleteBoard(board_id);
	}
	
	/**
	 * 게시물 파일 삭제
	 * 
	 * @param board_id
	 * @return
	 */
	@Transactional
	public int deleteBoardNoticeFile(Long boardNoticeFileId) {
		BoardNoticeFile boardNoticeFile = boardMapper.getBoardNoticeFile(boardNoticeFileId);
		log.info(boardNoticeFile.getFilePath()+boardNoticeFile.getFileRealName());
		FileUtils.deleteFileReculsive(boardNoticeFile.getFilePath()+boardNoticeFile.getFileRealName());
		return boardMapper.deleteBoardNoticeFile(boardNoticeFileId);
	}

	/**
	 * 게시물 Comment 삭제
	 * 
	 * @param board_comment_id
	 * @return
	 */
	@Transactional
	public int deleteBoardComment(Long board_comment_id) {
		return boardMapper.deleteBoardComment(board_comment_id);
	}

	/**
	 * 게시물 Comment 일괄 삭제
	 * 
	 * @param board_id
	 * @return
	 */
	@Transactional
	public int deleteBoardCommentByBoardId(Long board_id) {
		return boardMapper.deleteBoardCommentByBoardId(board_id);
	}
	
	/**
	 * 게시물 file 불러오기
	 * 
	 * @param board_id
	 * @return
	 */
	@Transactional
	public BoardNoticeFile getBoardNoticeFile(Long boardNoticeFileId) {
		
		return boardMapper.getBoardNoticeFile(boardNoticeFileId);
	}

	/**
	 * 게시물 fileList 불러오기
	 * 
	 * @param board_id
	 * @return
	 */
	@Transactional
	public List<BoardNoticeFile> getBoardNoticeFiles(Long board_id) {
		
		return boardMapper.getBoardNoticeFiles(board_id);
	}
}
