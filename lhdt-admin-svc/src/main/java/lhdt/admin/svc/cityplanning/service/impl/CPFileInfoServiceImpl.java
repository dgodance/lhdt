package lhdt.admin.svc.cityplanning.service.impl;

import lhdt.admin.svc.cityplanning.domain.CPFileInfo;
import lhdt.admin.svc.cityplanning.persistence.CPFileInfoRepository;
import lhdt.admin.svc.cityplanning.persistence.CPFileInfoMapper;
import lhdt.admin.svc.cityplanning.service.CPFileInfoService;
import lhdt.admin.svc.common.AdminSvcServiceImpl;
import lhdt.admin.svc.lhdt.config.PropertiesConfig;
import lhdt.ds.common.misc.DsFile;
import lhdt.ds.common.misc.DsFileMaster;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CPFileInfoServiceImpl
        extends AdminSvcServiceImpl<CPFileInfoRepository, CPFileInfoMapper, CPFileInfo, Long>
        implements CPFileInfoService {
    private final CPFileInfoRepository jpaRepo;
    private final CPFileInfoMapper mapper;
    private final PropertiesConfig propertiesConfig;

    @PostConstruct
    private void init() {
        super.set(jpaRepo, mapper, new CPFileInfo());
    }

    public List<CPFileInfo> getCPFilesByMultipart(MultipartFile[] files) {
        String Path = "";
        List<CPFileInfo> result = new ArrayList<>();
        for(MultipartFile mtf : files) {
            String Name = mtf.getOriginalFilename();
            result.add(this.multipart2CPFileInfo(mtf));
        }
        return result;
    }

    private CPFileInfo multipart2CPFileInfo(MultipartFile multipartFile) {
        CPFileInfo fi = new CPFileInfo();
            // 파일 정보
            String uploadDir = propertiesConfig.getCpFileUploadDir();
            String originFilename = multipartFile.getOriginalFilename().split(".")[0];
            String extName = originFilename.substring(originFilename.lastIndexOf("."), originFilename.length());
            Long size = multipartFile.getSize();

            // 서버에서 저장 할 파일 이름
            String saveFileName = DsFileMaster.genFileNameByFullTime();

            System.out.println("originFilename : " + originFilename);
            System.out.println("extensionName : " + extName);
            System.out.println("saveFileName : " + saveFileName);

            fi.setFileName(saveFileName);
            fi.setFilePath(uploadDir);
            fi.setOriginFileName(originFilename);
            fi.setFileExtention(extName);
        return fi;
    }

    // 파일을 실제로 write 하는 메서드
    private void writeFileByNameAndPath(MultipartFile multipartFile, String fullPath) throws IOException{
        byte[] data = multipartFile.getBytes();
        FileOutputStream fos = new FileOutputStream(fullPath);
        fos.write(data);
        fos.close();
    }

    /**
     *  Multipart Stream 2 all of File
     * @param multipartFiles
     * @return
     */
    @Override
    public List<CPFileInfo> procCPFiles(MultipartFile[] multipartFiles) {
        var files = getCPFilesByMultipart(multipartFiles);
        for(int i = 0; i < multipartFiles.length; i++) {
            var file = files.get(i);
            try {
                writeFileByNameAndPath(multipartFiles[i], file.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return files;
    }
}
