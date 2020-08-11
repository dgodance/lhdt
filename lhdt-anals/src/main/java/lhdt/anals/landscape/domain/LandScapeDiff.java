package lhdt.anals.landscape.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lhdt.anals.hello.domain.Child;
import lhdt.anals.hello.domain.Domain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 경관비교마스터
 * UK
 * landScapeDiffName
 */
@Entity
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
