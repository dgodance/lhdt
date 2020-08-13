package lhdt.svc.landscape.persistence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import lhdt.svc.landscape.domain.LandScapeDiff;
import lhdt.svc.landscape.domain.LandScapeDiffDet;

public interface LandScapeDiffDetRepository extends CrudRepository<LandScapeDiffDet, Long> {
    boolean existsByFileNameAndFilePathAndLandScapeDiff(String fileName, String filePath, LandScapeDiff lsd);
    LandScapeDiffDet findByFileNameAndFilePathAndLandScapeDiff(String fileName, String filePath, LandScapeDiff lsd);
    List<LandScapeDiffDet> findAllByFileNameAndFilePathAndLandScapeDiff(String fileName, String filePath,
                                                                        LandScapeDiff lsd);
}
