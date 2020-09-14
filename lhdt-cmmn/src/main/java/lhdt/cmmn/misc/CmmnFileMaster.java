package lhdt.cmmn.misc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

import org.springframework.web.multipart.MultipartFile;

import dev.hyunlab.core.util.PpDateUtil;

/**
 * 파일 처리를 위한 공통 클래스
 * @author break8524
 */
@SuppressWarnings("serial")
public class CmmnFileMaster extends File {
    public CmmnFileMaster(String pathname) {
        super(pathname);
    }

    /**
     * @return
     * @since
     * 	20200824	refactoring
     */
    public static String genFileNameByFullTime() {
    	return PpDateUtil.getYmdhmssss();
//        String fileName = "";
//
//        Calendar calendar = Calendar.getInstance();
//        fileName += calendar.get(Calendar.YEAR);
//        fileName += calendar.get(Calendar.MONTH);
//        fileName += calendar.get(Calendar.DATE);
//        fileName += calendar.get(Calendar.HOUR);
//        fileName += calendar.get(Calendar.MINUTE);
//        fileName += calendar.get(Calendar.SECOND);
//        fileName += calendar.get(Calendar.MILLISECOND);
//
//        return fileName;
    }

    /**
     * @param path
     * @throws IOException
     * @since
     * 	20200824	refactoring
     */
    public static void deleteFolderByPath(String path) throws IOException {
    	CmmnUtils.forceDelete(Paths.get(path));
    }

    /**
     * multipart Stream을 파일로 저장합니다
     * @param multipartFile
     * @param fullPath
     * @throws IOException
     */
        public static void writeFileByNameAndPath(MultipartFile multipartFile, String fullPath) throws IOException{
        byte[] data = multipartFile.getBytes();
        FileOutputStream fos = new FileOutputStream(fullPath);
        fos.write(data);
        fos.close();
    }
}
