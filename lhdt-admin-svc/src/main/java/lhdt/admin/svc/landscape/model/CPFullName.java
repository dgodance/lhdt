package lhdt.admin.svc.landscape.model;

import lombok.*;
import org.springframework.stereotype.Service;

/**
 * 지구계획 전체 명을 정의합니다
 */
@Getter
@Service
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CPFullName {
    private String localName;
    private String districName;

    /**
     * 지구계획명을 생성합니다
     * @return
     */
    @Override
    public String toString() {
        return localName + "-" + districName;
    }
}
