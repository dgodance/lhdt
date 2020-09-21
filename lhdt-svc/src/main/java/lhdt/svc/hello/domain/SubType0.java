package lhdt.svc.hello.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lhdt.svc.hello.types.DefaultType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 안녕
 * @author gravity
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name="subtype0")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class  SubType0 extends SuperType {
    /**
     * 안녕 명
     */
    @Column(name = "hello_name", nullable = false, length = 100)
    private String helloName;

    /**
     * 내용
     */
    @Column(name = "cn")
    private String cn;

    @Enumerated(EnumType.ORDINAL)
    private DefaultType defaultType;
    /**
     * 검색조건 -  안녕 명
     */
    private String searchHelloName;

    @JsonManagedReference
    @OneToMany(mappedBy = "helloId", fetch=FetchType.EAGER, cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Child> childs = new ArrayList<>();

    public void addChild(Child c) {
        this.childs.add(c);
        if(c.getHelloId() != this)
            c.setHelloId(this);
    }
    public void addChilds(List<Child> c) {
        c.forEach(p -> {
            this.childs.add(p);
            if(p.getHelloId() != this)
                p.setHelloId(this);
        });
    }
}