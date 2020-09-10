package lhdt.admin.svc.lhdt.service;

import java.util.List;

import lhdt.admin.svc.lhdt.domain.Issue;

/**
 * issue
 * @author jeongdae
 *
 */
public interface IssueService {

	/**
	 * 최근 이슈 목록
	 * @return
	 */
	List<Issue> getListRecentIssue();
}
