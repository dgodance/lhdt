package lhdt.anals.landscape.persistance;

import lhdt.anals.landscape.domain.LandScapeDiff;
import lhdt.anals.landscape.domain.LandScapeDiffDet;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LandScapeDiffDetRepository extends CrudRepository<LandScapeDiffDet, Long> {
    boolean existsByFileNameAndFilePathAndLandScapeDiff(String fileName, String filePath, LandScapeDiff lsd);
    LandScapeDiffDet findByFileNameAndFilePathAndLandScapeDiff(String fileName, String filePath, LandScapeDiff lsd);
    List<LandScapeDiffDet> findAllByFileNameAndFilePathAndLandScapeDiff(String fileName, String filePath,
                                                                        LandScapeDiff lsd);
}
