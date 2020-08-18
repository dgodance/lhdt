package lhdt.admin.svc.lhdt.persistence;

import java.util.List;

import lhdt.admin.svc.lhdt.domain.Issue;
import lhdt.ds.common.config.LhdtConnMapper;

/**
 * 이슈
 * @author jeongdae
 *
 */
@LhdtConnMapper
public interface IssueMapper {
	
	/**
	 * 최근 이슈 목록
	 * @return
	 */
	List<Issue> getListRecentIssue();
}
