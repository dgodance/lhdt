package lhdt.admin.svc.landscape.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 지구계획전체이름과 아이디를 제공합니다
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CPFullNameAndId {
    private Long id;
    private CPFullName cpFullName;
}
