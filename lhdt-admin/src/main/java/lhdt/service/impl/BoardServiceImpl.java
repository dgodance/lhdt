package lhdt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lhdt.domain.board.BoardNotice;
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
	 * @param boardNotice
	 * @return
	 */
	@Transactional(readOnly = true)
	public Long getBoardTotalCount(BoardNotice boardNotice) {
		return boardMapper.getBoardTotalCount(boardNotice);
	}

	/**
	 * 게시물 목록
	 * 
	 * @param boardNotice
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<BoardNotice> getListBoard(BoardNotice boardNotice) {
		return boardMapper.getListBoard(boardNotice);
	}

	/**
	 * 게시물 Comment 목록
	 * 
	 * @param boardNoticeId
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<BoardNoticeComment> getListBoardNoticeComment(Long boardNoticeId) {
		return boardMapper.getListBoardNoticeComment(boardNoticeId);
	}

	/**
	 * 게시물 Comment 목록
	 * 
	 * @param boardNoticeComment
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<BoardNoticeComment> getListBoardNoticeCommentByDepth(BoardNoticeComment boardNoticeComment) {
		return boardMapper.getListBoardNoticeCommentByDepth(boardNoticeComment);
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
	 * @param boardNoticeId
	 * @return
	 */
	@Transactional
	public BoardNotice getBoard(Long boardNoticeId) {
		BoardNotice board = boardMapper.getBoard(boardNoticeId);
		boardMapper.updateBoardViewCount(boardNoticeId);
		return board;
	}

	/**
	 * 게시물 정보(수정용)
	 * 
	 * @param boardNoticeId
	 * @return
	 */
	@Transactional
	public BoardNotice getBoardForModify(Long boardNoticeId) {
		BoardNotice boardNotice = boardMapper.getBoard(boardNoticeId);
		return boardNotice;
	}

	/**
	 * 게시물 Comment 정보
	 * 
	 * @param boardNoticeCommentId
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
	public int insertBoard(BoardNotice boardNotice, List<BoardNoticeFile> boardNoticeFileList, Boolean fileExist) {

		log.info("board =============== {} ", boardNotice);
		int result = boardMapper.insertBoard(boardNotice);

		if (fileExist) {
			Long boardNoticeId = boardNotice.getBoardNoticeId();
			String userId = boardNotice.getUserId();
			for (BoardNoticeFile boardNoticeFile : boardNoticeFileList) {
				boardNoticeFile.setBoardNoticeId(boardNoticeId);
				boardNoticeFile.setUserId(userId);
				boardMapper.insertFile(boardNoticeFile);
				result++;
			}
		}
		return boardMapper.insertBoardDetail(boardNotice);
	}

	/**
	 * 게시물 Comment 등록
	 * 
	 * @param boardNoticeComment
	 * @return
	 */
	@Transactional
	public int insertBoardNoticeComment(BoardNoticeComment boardNoticeComment) {
		return boardMapper.insertBoardNoticeComment(boardNoticeComment);
	}

	/**
	 * 게시물 more Comment 등록
	 * 
	 * @param boardNoticeComment
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
	public int updateBoard(BoardNotice boardNotice, List<BoardNoticeFile> boardNoticeFileList, Boolean fileExist) {

		int result = boardMapper.updateBoard(boardNotice);

		log.info("board =============== {} ", boardNotice);
		if (fileExist) {
			Long boardNoticeId = boardNotice.getBoardNoticeId();
			String userId = boardNotice.getUserId();
			for (BoardNoticeFile boardNoticeFile : boardNoticeFileList) {
				boardNoticeFile.setBoardNoticeId(boardNoticeId);
				boardNoticeFile.setUserId(userId);
				boardMapper.insertFile(boardNoticeFile);
				result++;
			}
		}
		return boardMapper.updateBoardDetail(boardNotice);
	}

	/**
	 * 게시물 Comment 수정
	 * 
	 * @param boardNoticeComment
	 * @return
	 */
	@Transactional
	public int updateBoardComment(BoardNoticeComment boardNoticeComment) {
		return boardMapper.updateBoardComment(boardNoticeComment);
	}

	/**
	 * 게시물 삭제
	 * 
	 * @param boardNoticeId
	 * @return
	 */
	@Transactional
	public int deleteBoard(Long boardNoticeId) {
		return boardMapper.deleteBoard(boardNoticeId);
	}

	/**
	 * 게시물 파일 삭제
	 * 
	 * @param boardNoticeFileId
	 * @return
	 */
	@Transactional
	public int deleteBoardNoticeFile(Long boardNoticeFileId) {
		BoardNoticeFile boardNoticeFile = boardMapper.getBoardNoticeFile(boardNoticeFileId);
		log.info(boardNoticeFile.getFilePath() + boardNoticeFile.getFileRealName());
		FileUtils.deleteFileReculsive(boardNoticeFile.getFilePath() + boardNoticeFile.getFileRealName());
		return boardMapper.deleteBoardNoticeFile(boardNoticeFileId);
	}

	/**
	 * 게시물 Comment 삭제
	 * 
	 * @param boardNoticeCommentId
	 * @return
	 */
	@Transactional
	public int deleteBoardComment(Long boardNoticeCommentId) {
		return boardMapper.deleteBoardComment(boardNoticeCommentId);
	}

	/**
	 * 게시물 Comment 일괄 삭제
	 * 
	 * @param boardNoticeId
	 * @return
	 */
	@Transactional
	public int deleteBoardCommentByBoardId(Long boardNoticeId) {
		return boardMapper.deleteBoardCommentByBoardId(boardNoticeId);
	}

	/**
	 * 게시물 file 불러오기
	 * 
	 * @param boardNoticeFileId
	 * @return
	 */
	@Transactional
	public BoardNoticeFile getBoardNoticeFile(Long boardNoticeFileId) {

		return boardMapper.getBoardNoticeFile(boardNoticeFileId);
	}

	/**
	 * 게시물 fileList 불러오기
	 * 
	 * @param boardNoticeId
	 * @return
	 */
	@Transactional
	public List<BoardNoticeFile> getBoardNoticeFiles(Long boardNoticeId) {

		return boardMapper.getBoardNoticeFiles(boardNoticeId);
	}
}
