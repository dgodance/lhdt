package lhdt.svc.hello.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.geo.Point;

import lhdt.svc.common.Domain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="view_anals_loca")
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
