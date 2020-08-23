package lhdt.admin.svc.cityplanning.service.impl;

import lhdt.admin.svc.cityplanning.domain.CPFileInfo;
import lhdt.admin.svc.cityplanning.persistence.CPFileInfoRepository;
import lhdt.admin.svc.cityplanning.persistence.CPFileInfoMapper;
import lhdt.admin.svc.cityplanning.service.CPFileInfoService;
import lhdt.admin.svc.common.AdminSvcServiceImpl;
import lhdt.admin.svc.lhdt.config.PropertiesConfig;
import lhdt.admin.svc.lhdt.domain.FileInfo;
import lhdt.ds.common.misc.DsUtils;
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

    @Transactional
    public List<String> procCPFiles(MultipartFile[] files) {
        String Path = "";
        List<String> result = new ArrayList<String>();
        for(MultipartFile mtf : files) {
            String Name = mtf.getOriginalFilename();
            result.add(this.restoreByExcel(mtf));
        }
        return result;
    }

    private CPFileInfo restoreByExcel(MultipartFile multipartFile) {
        String url = null;
        CPFileInfo fi = new CPFileInfo();
        try {
            // 파일 정보
            String uploadDir = propertiesConfig.getCpFileUploadDir();
            String originFilename = multipartFile.getOriginalFilename();
            String extName = originFilename.substring(originFilename.lastIndexOf("."), originFilename.length());
            Long size = multipartFile.getSize();

            // 서버에서 저장 할 파일 이름
            String saveFileName = genSaveFileName();

            System.out.println("originFilename : " + originFilename);
            System.out.println("extensionName : " + extName);
            System.out.println("saveFileName : " + saveFileName);

            url = uploadDir + saveFileName;

//            if(ft == FileType.ECHODELTASHP) {
//                this.writeFile(multipartFile, saveFileName, uploadDir);
//                SimFileMaster sfm = SimFileMaster.builder()
//                        .originFileName(originFilename)
//                        .saveFileName(saveFileName)
//                        .saveFilePath(uploadDir)
//                        .saveFileType(ft)
//                        .build();
//                int result = simuMapper.insertSimCityPlanFile(sfm);
//            }
//            url = uploadDir + saveFileName;
            fi.setFileName(saveFileName);
            fi.setFilePath(uploadDir);
            fi.setOriginFileName(originFilename);
            fi.setFileExtention(extName);
        }
        catch (IOException e) {
            // 원래라면 RuntimeException 을 상속받은 예외가 처리되어야 하지만
            // 편의상 RuntimeException을 던진다.
            // throw new FileUploadException();
            throw new RuntimeException(e);
        }
        return fi;
    }


    private void cleanUploadFolder(String f4dInputDir) {
        for( SimFileMaster obj : getFileListInFolder(f4dInputDir) ) {
            new File(obj.getSaveFilePath() + "/" + obj.getSaveFileName()).delete();
        }
    }
    // 파일을 실제로 write 하는 메서드
    private boolean writeFile(MultipartFile multipartFile, String saveFileName, String SAVE_PATH) throws IOException{
        boolean result = false;

        this.genSaveFileName(SAVE_PATH);

        byte[] data = multipartFile.getBytes();
        FileOutputStream fos = new FileOutputStream(SAVE_PATH + "/" + saveFileName);
        fos.write(data);
        fos.close();
        return result;
    }


    /**
     * get FileList in Foler
     * return result this.
     * FileType => File, Directory
     * list File Path with FileName
     *
     * @param folderPath
     * @return retunr SimFileMaster FileData
     */
    private List<CPFileInfo> getFileListByFolder(String folderPath) {
        File path = new File(folderPath);
        File[] fileList = path.listFiles();
        ArrayList<CPFileInfo> resultList = new ArrayList<>();
        for(File f: fileList){
            String str = f.getName();
            if(f.isDirectory()) { // is Directory
                System.out.print(str+"\t");
                System.out.print("DIR\n");
//                new CPFileInfo().builder().saveFileType(FileType.DIRECTORY)
//            saveFilePath(folderPath).saveFileName(str).build()
//                resultList.add();
            }else { // is File
                System.out.print(str+"\t");
                System.out.print("Files\n");
//                new CPFileInfo().builder().saveFileType(FileType.FILE).
//                        saveFilePath(folderPath).saveFileName(str).build()
//                resultList.add();
            }
        }
        return resultList;
    }

    private String genSaveFileName() {
        String fileName = "";

        Calendar calendar = Calendar.getInstance();
        fileName += calendar.get(Calendar.YEAR);
        fileName += calendar.get(Calendar.MONTH);
        fileName += calendar.get(Calendar.DATE);
        fileName += calendar.get(Calendar.HOUR);
        fileName += calendar.get(Calendar.MINUTE);
        fileName += calendar.get(Calendar.SECOND);
        fileName += calendar.get(Calendar.MILLISECOND);

        return fileName;
    }
}
