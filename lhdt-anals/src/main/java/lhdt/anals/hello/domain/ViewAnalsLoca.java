package lhdt.anals.hello.domain;

import dev.hyunlab.core.vo.PpVO;
import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.data.geo.Point;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.net.InterfaceAddress;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ViewAnalsLoca extends Domain {
    @Column(name = "category_id")
    private Long cateId;

    @Column(name = "view_anals_name_id")
    private String viewAnalsName;


    @Column(name = "view_anals_point")
    private Point point;

}
