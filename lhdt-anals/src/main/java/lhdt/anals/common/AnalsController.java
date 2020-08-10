/**
 * 
 */
package lhdt.anals.common;

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
public class AnalsController {

	/**
	 * 
	 * @param vo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected ResponseEntity<Map<String,Object>> res(Object obj){
		
		//
		if(obj.getClass() == Map.class) {
			return new ResponseEntity<>((Map)obj, HttpStatus.OK);
		}
		
		//
		Map<String,Object> map = new HashMap<>();
		map.put(AnalsConst.DATA, obj);
		
		//
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param vo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected ResponseEntity<Map<String,Object>> res(Object obj, HttpStatus hs){
		
		//
		if(obj.getClass() == Map.class) {
			return new ResponseEntity<>((Map)obj, hs);
		}
		
		//
		Map<String,Object> map = new HashMap<>();
		map.put(AnalsConst.DATA, obj);
		
		//
		return new ResponseEntity<>(map, hs);
	}
	
	
	/**
	 * 
	 * @param vo
	 * @return
	 */
	protected ResponseEntity<Map<String,Object>> res(List<Object> list){
		
		Map<String,Object> map = new HashMap<>();
		map.put(AnalsConst.DATA, list);
		
		//
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param vo
	 * @return
	 */
	protected ResponseEntity<Map<String,Object>> res(List<Object> list, HttpStatus hs){
		
		Map<String,Object> map = new HashMap<>();
		map.put(AnalsConst.DATA, list);
		
		//
		return new ResponseEntity<>(map, hs);
	}
}
