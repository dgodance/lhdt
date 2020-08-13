package lhdt.svc.hello.domain;
import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubType1 extends SuperType {
    /**
     * 내용
     */
    @Column(name = "cn2")
    private String cn2;
}