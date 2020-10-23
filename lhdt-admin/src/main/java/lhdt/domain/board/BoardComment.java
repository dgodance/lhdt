package lhdt.domain.board;

import java.time.LocalDateTime;

import lhdt.domain.board.Board.BoardBuilder;
import lhdt.utils.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 게시판 댓글(Comment)
 * @author hansang
 *
 */
@ToString(callSuper = true)
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardComment {
	
	// 사용자 이름
	private String user_name;
	
	// 고유번호
	private Long board_notice_comment_id;
	// 게시판 고유번호
	private Long board_notice_id;
	// 사용자 아이디
	private String user_id;
	// 댓글(Comment)
	private String comment;
	// 요청 IP
	private String client_ip;
	// 등록일
	private String register_date;
	
	public String getViewComment() {
		if(this.comment == null || "".equals(this.comment)) {
			return "";
		}
		
		String str = StringUtil.getStringConvertForHtml(this.comment);
		str = StringUtil.getStringNewLineConvertForHtml(str);
		
		return str;
	}
	
	public String getViewRegisterDate() {
		if(this.register_date == null || "".equals( register_date)) {
			return "";
		}
		return register_date.substring(0, 19);
	}
}
