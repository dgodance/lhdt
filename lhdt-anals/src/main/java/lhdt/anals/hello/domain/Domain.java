package lhdt.anals.hello.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import dev.hyunlab.core.vo.PpVO;
import lombok.Data;

@SuppressWarnings("serial")
@MappedSuperclass
@Data
public abstract class Domain extends PpVO {
    /**
     * 안녕 아이디
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @CreationTimestamp
    @Column(name = "regist_datetime")
    protected Date registDt;

    @UpdateTimestamp
    @Column(name = "modified_datetime")
    protected Date updtDt;
}
