package lhdt.svc.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 기본 CRUD 인터페이스
 * @author break8524
 * @param <T>
 */
public interface CRUDInterface<T> {
    /**
     * VO를 입력합니다
     * @param vo
     * @return
     */
    T save(T vo);

    /**
     * 모든 VO를 획득합니다
     * @return
     */
    List<T> findAll();

    /**
     * Id를 통해 모든 VO를 가져옵니다
     * @param id
     * @return
     */
    List<T> findAllById(Long id);

    /**
     * 해당 페이지에 부합하는 리스트를 리턴합니다
     * @param pageable
     * @return
     */
    Page<T> findAllByPage(Pageable pageable);

    /**
     * 식별자에 대한 VO가 있는지 찾습니다
     * @param vo
     * @return
     */
    boolean existVoByUk(T vo);

    /**
     * UK에 대한 데이터를 찾습니다
     * @param vo
     * @return
     */
    T findByUk(T vo);

    /**
     * UK에 대한 모든 데이터를 찾습니다
     * @param vo
     * @return
     */
    List<T> findAllByUk(T vo);

    /**
     * id를 통해 VO를 삭제합니다
     * @param vo
     */
    void deleteByVo(T vo);
}