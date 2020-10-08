package lhdt.admin.svc.file.persistence;

import lhdt.admin.svc.common.AdminSvcMapper;
import lhdt.cmmn.config.AnalsConnMapper;

/**
 * 파일인포 테이블에 대한 Mybatis 연결 클래스
 */
@AnalsConnMapper
public interface FileInfoMapper extends AdminSvcMapper<FileInfoMapper, Long> {
}
