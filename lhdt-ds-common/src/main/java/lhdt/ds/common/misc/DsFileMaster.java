package lhdt.ds.common.misc;

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
public class DsFileMaster extends File {
    public DsFileMaster(String pathname) {
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
    	DsUtils.forceDelete(Paths.get(path));
//        File folder = new File(path);
//        try {
//            if(folder.exists()){
//                File[] folder_list = folder.listFiles(); //파일리스트 얻어오기
//
//                for (int i = 0; i < folder_list.length; i++) {
//                    if(folder_list[i].isFile()) {
//                        folder_list[i].delete();
//                    }else {
//                        deleteFolderByPath(folder_list[i].getPath()); //재귀함수호출
//                    }
//                    folder_list[i].delete();
//                }
//                folder.delete(); //폴더 삭제
//            }
//        } catch (Exception e) {
//            e.getStackTrace();
//        }
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
