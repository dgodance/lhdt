package lhdt.ds.common.misc;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

/**
 * 파일 처리를 위한 공통 클래스
 * @author break8524
 */
public class DsFileMaster extends File {
    public DsFileMaster(String pathname) {
        super(pathname);
    }

    public static String genFileNameByFullTime() {
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

    public static void deleteFolderByPath(String path) {
        File folder = new File(path);
        try {
            if(folder.exists()){
                File[] folder_list = folder.listFiles(); //파일리스트 얻어오기

                for (int i = 0; i < folder_list.length; i++) {
                    if(folder_list[i].isFile()) {
                        folder_list[i].delete();
                    }else {
                        deleteFolderByPath(folder_list[i].getPath()); //재귀함수호출
                    }
                    folder_list[i].delete();
                }
                folder.delete(); //폴더 삭제
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
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
