package lhdt.admin.svc.landscape.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import lhdt.admin.svc.cityplanning.domain.CPDistricInfo;
import lhdt.ds.common.domain.DsDomain;
import lhdt.ds.common.misc.DsField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="ls_diff_group")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LandScapeDiffGroup extends DsDomain {
    @NotNull
    @DsField(bizKey = true, order = 0)
    @Column(name = "ls_diff_group_name")
    private String lsDiffGrupName;


    @JsonManagedReference
    @OneToMany(mappedBy = "landScapeDiffGroup", fetch= FetchType.LAZY, cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<LandScapeDiff> lsDiffs = new ArrayList<>();

    public void addLandScapeDiff(LandScapeDiff landscapeDiff) {
        if(landscapeDiff.getLandScapeDiffGroup() != this)
            landscapeDiff.setLandScapeDiffGroup(this);
        this.lsDiffs.add(landscapeDiff);
    }
    public void addLandScapeDiffs(List<LandScapeDiff> landscapeDiffs) {
        landscapeDiffs.forEach(p -> {
            if(p.getLandScapeDiffGroup() != this)
                p.setLandScapeDiffGroup(this);
            this.lsDiffs.add(p);
        });
    }
}