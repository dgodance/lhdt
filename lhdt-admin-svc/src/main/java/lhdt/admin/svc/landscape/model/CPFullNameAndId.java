package lhdt.admin.svc.landscape.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CPFullNameAndId {
    private Long id;
    private CPFullName cpFullName;
}
