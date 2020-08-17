package lhdt.admin.svc.lhdt.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import lhdt.admin.svc.config.LhdtConnMapper;
import lhdt.admin.svc.lhdt.domain.Issue;

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
