package lhdt.svc.hello.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lhdt.svc.hello.domain.ViewAnalsLoca;

public interface ViewAnalsLocaRepository extends JpaRepository<ViewAnalsLoca, Long> {
    boolean existsByCateIdAndViewAnalsName(Long CateId, String viewAnalsName);
    ViewAnalsLoca findByCateIdAndViewAnalsName(Long CateId, String viewAnalsName);
    List<ViewAnalsLoca> findAllByCateIdAndViewAnalsName(Long CateId, String viewAnalsName);
}
