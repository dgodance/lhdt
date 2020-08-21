package lhdt.admin.svc.landscape.model;

import lombok.*;
import org.springframework.stereotype.Service;

@Getter
@Service
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CPFullName {
    private String localName;
    private String districName;

    @Override
    public String toString() {
        return localName + "-" + districName;
    }
}
