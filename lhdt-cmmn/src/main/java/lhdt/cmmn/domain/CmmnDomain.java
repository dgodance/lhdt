package lhdt.cmmn.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class CmmnDomain  {
    /**
     * 안녕 아이디
     * COMMENT '아이디'"
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    /**
     *  TIMESTAMP COMMENT '등록 일시'"
     */
    @CreationTimestamp
    @Column(name = "regist_dt")
    protected Date registDt;

    /**
     *  TIMESTAMP COMMENT '수정 일시'"
     */
    @UpdateTimestamp
    @Column(name = "updt_dt")
    protected Date updtDt;
}
