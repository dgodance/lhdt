/**
 * 
 */
package lhdt.ds.common.misc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.hyunlab.web.util.PpWebSessionUtil;
import lhdt.ds.common.domain.DsDomain;
import lombok.extern.slf4j.Slf4j;

/**
 * 모든 controller  의 부모
 * @author gravity
 *
 */
@Slf4j
public class DsController {
	

	/**
	 * ResponseEntity 생성&리턴
	 * 2020/08/12 Modified - <T extend Domain> -> <T>
	 *
	 * @param t Domain의 자식
	 * @return
	 */
	protected <T> ResponseEntity<Map<String,Object>> res(T t){
		
		//
		Map<String,Object> map = new HashMap<>();
		map.put(DsConst.DATA, t);
		
		//
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	/**
	 * ResponseEntity 생성&리턴
	 * @param t Map의 자식
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected <T extends Map> ResponseEntity<Map<String,Object>> res(T t){
		
		//
		Map<String,Object> map = new HashMap<>();
		map.put(DsConst.DATA, t);
		
		//
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	
	/**
	 * ResponseEntity 생성&리턴
	 * @param vo
	 * @return
	 */
	protected <T extends DsDomain> ResponseEntity<Map<String,Object>> res(List<T> list){
		
		Map<String,Object> map = new HashMap<>();
		map.put(DsConst.DATA, list);
		
		//
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	/**
	 * ResponseEntity 생성&리턴
	 * @param vo
	 * @return
	 */
	protected <T extends DsDomain> ResponseEntity<Map<String,Object>> res(List<T> list, HttpStatus hs){
		
		Map<String,Object> map = new HashMap<>();
		map.put(DsConst.DATA, list);
		
		//
		return new ResponseEntity<>(map, hs);
	}

	/**
	 * 파일을 Object 형태로 리턴
	 * @param 
	 * @return
	 */
	protected Object file2Object(String filePath) {
		return file2Object(new File(filePath));		
	}
	
	
	/**
	 * @param path 파일의  fullpath
	 * @return
	 */
	protected Object file2Object(Path path) {
		return file2Object(path.toFile());
	}
	
	/**
	 * object로 변환
	 * @param path 파일 경로
	 * @param filename 파일명
	 * @return
	 */
	protected Object file2Object(Path path, String filename) {
		return file2Object(path.resolve(filename));
	}
	
	/**
	 * file을 Object로 변환
	 * @param file 파일
	 * @return
	 */
	protected Object file2Object(File file) {
		try {
			try(InputStream targetStream = new FileInputStream(file)){
				return new ObjectMapper().readValue(targetStream, Object.class);
			}
		} catch (IOException e) {
			log.error("{}",e);
		}
		
		//
		return null;
	}
}
