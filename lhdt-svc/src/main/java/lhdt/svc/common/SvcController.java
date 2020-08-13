/**
 * 
 */
package lhdt.svc.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
}
