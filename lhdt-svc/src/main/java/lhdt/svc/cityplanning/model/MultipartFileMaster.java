package lhdt.svc.cityplanning.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MultipartFileMaster {
    private MultipartFile[] files;

    private String originFileName;
    private String saveFileName;
    private String saveFilePath;

    public static List<MultipartFileMaster> saveMultiFile(MultipartFile[] files, String uploadDir, String movDir) {
        String originFileName = "";
        String saveFileName = "";
        delete(movDir);

        // write files in uploadDir
        List<MultipartFileMaster> lsfm = new ArrayList<>();
        for(MultipartFile mtf : files) {
            String fileName = mtf.getOriginalFilename();
            String extName = fileName.substring(fileName.lastIndexOf("."), fileName.length());
            originFileName = fileName;
            saveFileName = genSaveFileName(extName);
            try{
                // Normal file saved uploadDir But IFC or Model Files is upload F4D InputFolder
                // Normal file => saved
                makeDir(uploadDir);
                writeFile(mtf, saveFileName, uploadDir);

                Files.copy(new File(uploadDir + saveFileName).toPath(), new File(movDir + originFileName).toPath());

                lsfm.add(MultipartFileMaster.builder().originFileName(originFileName).saveFilePath(uploadDir).saveFileName(saveFileName).build());
            } catch(Exception e) {
                delete(uploadDir);
                delete(movDir);
                e.printStackTrace();
            }
        }
        return lsfm;
    }

    private static String genSaveFileName(String extName) {
        String fileName = "";

        Calendar calendar = Calendar.getInstance();
        fileName += calendar.get(Calendar.YEAR);
        fileName += calendar.get(Calendar.MONTH);
        fileName += calendar.get(Calendar.DATE);
        fileName += calendar.get(Calendar.HOUR);
        fileName += calendar.get(Calendar.MINUTE);
        fileName += calendar.get(Calendar.SECOND);
        fileName += calendar.get(Calendar.MILLISECOND);
        fileName += extName;

        return fileName;
    }

    private static void writeFile(MultipartFile multipartFile, String saveFileName, String SAVE_PATH) throws IOException {
        genSaveFileName(SAVE_PATH);

        byte[] data = multipartFile.getBytes();
        FileOutputStream fos = new FileOutputStream(SAVE_PATH + "/" + saveFileName);
        fos.write(data);
        fos.close();
    }

    private static boolean makeDir(String source) throws IOException {
        File dir = new File(source);
        if(!(dir.isDirectory())){
            dir.mkdirs();
            return false;
        } else {
            return true;
        }
    }

    public static void move(File sourceF, File targetF) {
        copy(sourceF, targetF);
        delete(sourceF.getPath());
    }

    public static void copy(File sourceF, File targetF){
        File[] target_file = sourceF.listFiles();
        for (File file : target_file) {
            File temp = new File(targetF.getAbsolutePath() + File.separator + file.getName());
            if(file.isDirectory()){
                temp.mkdirs();
                copy(file, temp);
            } else {
                FileInputStream fis = null;
                FileOutputStream fos = null;
                try {
                    fis = new FileInputStream(file);
                    fos = new FileOutputStream(temp) ;
                    byte[] b = new byte[4096];
                    int cnt = 0;
                    while((cnt=fis.read(b)) != -1){
                        fos.write(b, 0, cnt);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally{
                    try {
                        fis.close();
                        fos.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    public static void delete(String path) {
        File folder = new File(path);
        try {
            if(folder.exists()){
                File[] folder_list = folder.listFiles();

                for (int i = 0; i < folder_list.length; i++) {
                    if(folder_list[i].isFile()) {
                        folder_list[i].delete();
                    }else {
                        delete(folder_list[i].getPath());
                    }
                    folder_list[i].delete();
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}
