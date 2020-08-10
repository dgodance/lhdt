package lhdt.anals.lowinfo.domain;

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

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LowInfo extends Domain {
    @Column(name = "low_info_name")
    private String lowInfoName;

    @JsonManagedReference
    @OneToMany(mappedBy = "lowInfo", fetch= FetchType.EAGER, cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<LowInfoDet> lowInfoDets = new ArrayList<>();
}
