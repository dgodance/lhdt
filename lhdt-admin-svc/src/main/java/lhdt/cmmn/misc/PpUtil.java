package lhdt.cmmn.misc;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;

public class PpUtil {
	
	
	

	/**
	 * reflection이용.  domain의 fieldName의 값을 value로 설정
	 * field가 없거나 오류 발생하면 아무런값도 set하지 않음
	 * @param domain 도메인
	 * @param fieldName 필드명
	 * @param value 값
	 * @since
	 * 	20200811	init
	 */
	public static void setFieldValue(Object domain, String fieldName, Object value) {
		//
		Field[] fields = domain.getClass().getDeclaredFields();

		//
		try {
			for(Field f : fields) {
				if(fieldName.equals(f.getName())) {
					f.setAccessible(true);
					f.set(domain, value);
				}
			} 
		}catch (IllegalArgumentException | IllegalAccessException e) {
		}
	}
	

	

	/**
	 * reflection이용. domain의 fieldName의 값 추출
	 * @param domain 도메일
	 * @param fieldName 필드명
	 * @return field의 값. 필드없거나 오류 발생하면 null 리턴
	 * @since
	 * 	20200811	init
	 */
	public static Object getFieldValue(Object domain, String fieldName) {
		Object value = null;
		
		try {
			//
			Field[] fields = domain.getClass().getDeclaredFields();
			
			//
			for(Field f : fields) {
				if(fieldName.equals(f.getName())) {
					f.setAccessible(true);
					value = f.get(domain);
					break;
				}
			}
		} catch (IllegalAccessException | IllegalArgumentException | SecurityException e) {
		}
		
		//
		return value;
		
	}
	
	/**
	 * reflection이용. domain의 fieldName목록 추출
	 * @param domain 도메일
	 * @return fieldName목록. 오류발생|field가 없으면 빈 목록 리턴
	 * @since
	 * 	20200811	init
	 */
	public static Set<String> getFieldNames(Object domain) {
		Set<String> fieldNames = new HashSet<>();
		
		
		try {
			Field[] fields = domain.getClass().getDeclaredFields();
			
			//
			for(Field f : fields) {
				fieldNames.add(f.getName());
			}
			
		} catch (IllegalArgumentException | SecurityException e) {
		}
		
		//
		return fieldNames;
	}
	
	
	
	
	/**
	 * 파일, 디렉터리 모두 삭제 가능
	 * @param path 경로
	 * @throws IOException  예외
	 */
	public static void forceDelete(Path path) throws IOException {
		
		if(!Files.exists(path)) {
			return;
		}
		
		//
		FileUtils.forceDelete(path.toFile());
	}

	/**
	 * 모든 필드 목록 추출
	 * 재귀호출. 부모의 필드 목록까지 추출
	 * @param currentClass 클랙스
	 * @param fields 필드 목록. 리턴값
	 * @since 20200821	init
	 */
	public static void getFieldsUpTo(Class<?> currentClass, List<Field> fields){
		
		if(null == currentClass) {
			return;
		}
		
		List<Field> list = Arrays.asList(currentClass.getDeclaredFields());
		if(isEmpty(list)) {
			return;
		}
		
		//
		fields.addAll(list);
		
		//
		Class<?> parentClass = currentClass.getSuperclass();
		if(null != parentClass) {
			getFieldsUpTo(parentClass, fields);
		}
	}
	

	/**
	 * isEmpty의 반대
	 * @param obj 문자열
	 * @return true / false
	 * 	true 조건
	 * 		문자열인 경우 공백이 아니면
	 * 		collection(Set, List,...)인 경우 0 &lt; size
	 * 		배열인 경우 0 &lt; length
	 * 		Map인 경우 0 &lt; size
	 */
	public static boolean isNotEmpty(Object obj) {
		return !isEmpty(obj);
	}
	
	
	
	
	/**
	 * !널여부
	 * @param obj 오브젝트
	 * @return 널이 아니면 true
	 */
	public static boolean isNotNull(Object obj) {
		return !isNull(obj);
	}
	

	/**
	 * 널여부 검사
	 * @param obj 오브젝트
	 * @return 널이면 true
	 */
	public static boolean isNull(Object obj){
		return (null == obj);
	}
	
	

	/**
	 * 공백 여부
	 * @param obj 오브젝트. String|Collection|Map|Set|List|배열
	 * @return 공백이면 true
	 * @since
	 * 	20180322	배열, 리스트 처리 추가
	 * 	20200221	Map관련 추가
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object obj){
		if(isNull(obj)){
			return true;
		}
		
		//문자열
		if(String.class == obj.getClass() ) {
			return (0 == obj.toString().trim().length());
		}
		
		//
		if(obj instanceof Collection) {
			return (0 ==((Collection)obj).size());
		}
		
		//
		if(obj instanceof Map) {
			return (0 == ((Map)obj).size());
		}
		
		//
		if(Set.class == obj.getClass()) {
			return (0 == ((Set)obj).size());
		}
		
		//리스트
		if(List.class == obj.getClass() || (ArrayList.class == obj.getClass())) {
			return (0 == ((List)obj).size());
		}
		
		
		//배열
//		if(obj.getClass().toString().contains("[L")) {
//			return (0 == Array.getLength(obj));
//		}
		
		//
		return (0 == obj.toString().length());
	}





	
	/**
	 * nanotime 으로 유니크한 문자열 생성
	 * @param prefix 리턴값 앞에 붙일 접두어
	 * @return 유니크한 문자열
	 * @since
	 * 	20180215	prefix 추가
	 */
	public static String createShortUid(String prefix){		
		return (isEmpty(prefix) ? "UID" : prefix) + System.nanoTime(); 
//		return (isEmpty(prefix) ? "UID" : prefix) 
//				+ (new SimpleDateFormat("yyyyMMddHHmmssSSS")).format(new Date())
//				+ (new Random()).nextInt(10);
	}
	
	

	/**
	 * 첫 글자만 대문자로 변환
	 * @param str 문자열
	 * @return 변환된 문자열
	 * @since	
	 * 	20200810	init
	 */
	public static String capitalize(String str) {
		if(isEmpty(str)) {
			return "";
		}
		
		//
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	
}
