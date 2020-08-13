package lhdt.svc.landscape.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lhdt.svc.common.Domain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 경관비교마스터
 * UK
 * landScapeDiffName
 */
@Entity
@Table(name="landscape_diff")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LandScapeDiff extends Domain {
    @Column(name = "land_scape_diff_name")
    private String landScapeDiffName;

    @JsonManagedReference
    @OneToMany(mappedBy = "landScapeDiff", fetch= FetchType.EAGER, cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<LandScapeDiffDet> landScapeDiffDets = new ArrayList<>();

    public void addChild(LandScapeDiffDet c) {
        this.landScapeDiffDets.add(c);
        if(c.getLandScapeDiff() != this)
            c.setLandScapeDiff(this);
    }
    public void addChilds(List<LandScapeDiffDet> c) {
        c.forEach(p -> {
            this.landScapeDiffDets.add(p);
            if(p.getLandScapeDiff() != this)
                p.setLandScapeDiff(this);
        });
    }
}
