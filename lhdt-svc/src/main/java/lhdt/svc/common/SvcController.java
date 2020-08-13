/**
 * 
 */
package lhdt.svc.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 모든 controller  의 부모
 * @author gravity
 *
 */
public class SvcController {

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
		map.put(SvcConst.DATA, t);
		
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
		map.put(SvcConst.DATA, t);
		
		//
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	
	/**
	 * ResponseEntity 생성&리턴
	 * @param vo
	 * @return
	 */
	protected <T extends Domain> ResponseEntity<Map<String,Object>> res(List<T> list){
		
		Map<String,Object> map = new HashMap<>();
		map.put(SvcConst.DATA, list);
		
		//
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	/**
	 * ResponseEntity 생성&리턴
	 * @param vo
	 * @return
	 */
	protected <T extends Domain> ResponseEntity<Map<String,Object>> res(List<T> list, HttpStatus hs){
		
		Map<String,Object> map = new HashMap<>();
		map.put(SvcConst.DATA, list);
		
		//
		return new ResponseEntity<>(map, hs);
	}

	/**
	 * 파일을 Object 형태로 리턴
	 * @param 
	 * @return
	 */
	protected Object file2Object(String filePath) {
		File fi = new File(filePath);
		try {
			ObjectMapper mapper = new ObjectMapper();
			InputStream targetStream = new FileInputStream(fi);
			return mapper.readValue(targetStream, Object.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
