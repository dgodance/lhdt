package lhdt.anals.hello.persistence;

import lhdt.anals.hello.domain.SubType0;
import lhdt.anals.hello.domain.ViewAnalsLoca;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ViewAnalsLocaRepository extends CrudRepository<ViewAnalsLoca, Long> {
    boolean existsByCateIdAndViewAnalsName(Long CateId, String viewAnalsName);
    ViewAnalsLoca findByCateIdAndViewAnalsName(Long CateId, String viewAnalsName);
    List<ViewAnalsLoca> findAllByCateIdAndViewAnalsName(Long CateId, String viewAnalsName);
}
