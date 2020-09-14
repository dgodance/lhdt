/**
 * 
 */
package lhdt.svc.common;

import lhdt.cmmn.misc.CmmnServiceImpl;

/**
 * 모든 service impl 의 부모
 * @author gravity@daumsoft.com
 *
 */
public  class SvcServiceImpl<JPA, MAPPER, DOMAIN, IDTYPE> extends CmmnServiceImpl<JPA, MAPPER, DOMAIN, IDTYPE> implements SvcService<DOMAIN, IDTYPE> {
//	/**
//	 * jpa
//	 */
//	private JpaRepository<DOMAIN, IDTYPE> jpaRepo;
//	
//	/**
//	 * mybatis mapper
//	 */
//	private SvcMapper<DOMAIN, IDTYPE> mapper;
//	/**
//	 * domain
//	 */
//	@SuppressWarnings("unused")
//	private DOMAIN domain;
//
//	@Override
//	public void delete(IDTYPE id) {
//		//
//		this.jpaRepo.deleteById(id);
//	}
//
//	@Override
//	public void deleteAll(Iterable<IDTYPE> ids) {
//		ids.forEach(x->{
//			delete(x);
//		});
//	}
//	
//	
//	@SuppressWarnings("unchecked")
//	@Override
//	public void deleteByBizKey(DOMAIN domain) {
//		//
//		DOMAIN domain2 = findByBizKey(domain);
//		
//		delete((IDTYPE)((Domain)domain2).getId());
//	}
//	
//
//	@Override
//	public List<DOMAIN> findAll() {
//		return this.jpaRepo.findAll();
//	}
//	
//	
//	@Override 
//	public Page<DOMAIN> findAll(Pageable pageable){
//		return this.jpaRepo.findAll(pageable);
//	}
//	
//	
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public DOMAIN findByBizKey(DOMAIN domain) {
//		if(null == domain) {
//			log.warn("<<.findByBizKey - null domain");
//			return null;
//		}
//
//		//domain에 업무키 존재하지 않으면		
//		if(!existsBizKey(domain)) {
//			log.warn("<<.findByBizKey - not exists bizkey at {}", domain);
//			return null;
//		}
//
//		//
//		List<FieldAndOrder> list = getBizFields(domain);
//		Collections.sort(list, new FieldAndOrderComparator());
//		
//		
//		if(SvcUtils.isEmpty(list)) {
//			log.warn("<<.findByBizKey - empty list");
//			return null;
//		}
//		
//		//
//		String methodName = getMethodName("findBy", list);
//		log.debug(".findByBizKey - methodName:{}", methodName);
//
//		try {
//
//			//
//			Class<?>[] classes = getParamTypes(list);
//
//			//
//			Object[] objects = getParamValues(list, domain);
//
//			//메소드 실행
//			Object obj = this.jpaRepo.getClass().getMethod(methodName, classes).invoke(this.jpaRepo, objects);
//
//			//
//			log.debug("<<.findByBizKey - {}", obj);
//			return (DOMAIN)obj;
//		} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
//			log.error("{}", e);
//			throw new RuntimeException(e.getMessage());
//		}
//	}
//
//	@Override
//	public DOMAIN findById(IDTYPE id) {
//		Optional<DOMAIN> vo = this.jpaRepo.findById(id);
//
//		//
//		return vo.orElse(null);
//	}
//
//	@Override
//	public DOMAIN regist(DOMAIN domain) {
//		//domain에 업무키 존재하면
//		if(existsBizKey(domain)) {
//			//테이블에서 업무키로 조회
//			DOMAIN domain2 = findByBizKey(domain);
//			
//			//데이터 존재하면 업무키로  update
//			if(null != domain2) {
//				return updateByBizKey(domain);
//			}
//		}
//		
//		//insert
//		return this.jpaRepo.save(domain);
//	}
//
//
//	@Override
//	public DOMAIN update(IDTYPE id, DOMAIN domain) {
//		DOMAIN domain2 = findById(id);
//		if(null == domain2) {
//			return null;
//		}
//		
//		//domain의 값을 domain2에 overwrite하기, except id
//		SvcUtils.getFieldNames(domain).forEach(x->{
//			if("id".equals(x)) {
//				return;
//			}
//			
//			//
//			Object value = SvcUtils.getFieldValue(domain, x);
//			SvcUtils.setFieldValue(domain2, x, value);
//		});
//
//		//
//		return this.jpaRepo.save(domain2);
//	}
//	
//	
//	@SuppressWarnings("unchecked")
//	@Override
//	public void updateAll(Iterable<DOMAIN> domains) {
//		if(SvcUtils.isEmpty(domains)) {
//			return;
//		}
//		
//		//
//		domains.forEach(x->{
//			if(null == x) {
//				return;
//			}
//			
//			//
//			update((IDTYPE)((Domain)x).getId(), x);
//		});
//	}
//	
//	
//	@Override
//	public void updateAll(Map<IDTYPE, DOMAIN> map) {
//		if(SvcUtils.isEmpty(map)) {
//			return;
//		}
//		
//		//
//		for(Map.Entry<IDTYPE, DOMAIN> entry : map.entrySet()) {
//			update(entry.getKey(), entry.getValue());
//		}
//	}
//	
//	
//
//
//	@Override
//	public DOMAIN updateByBizKey(DOMAIN domain) {
//		//업무키 존재하지 않으면
//		if(!existsBizKey(domain)) {
//			return null;
//		}
//
//		//
//		DOMAIN domain2 = findByBizKey(domain);
//		if(null == domain2) {
//			return null;
//		}
//
//		//domain의 값을 domain2에 overwrite하기, except id
//		SvcUtils.getFieldNames(domain).forEach(x->{
//			if("id".equals(x)) {
//				return;
//			}
//			
//			//
//			Object value = SvcUtils.getFieldValue(domain, x);
//			SvcUtils.setFieldValue(domain2, x, value);
//		});
//
//		//
//		return this.jpaRepo.save(domain2);
//	}
//	
//	
//	
//
//	@Override
//	public Integer getTotcnt() {
//		return mapper.getTotcnt();
//	}
//	
//	
//
//	/**
//	 * domain에 bizkey 존재 여부
//	 * @param domain
//	 * @return true(domain에  @AnalsField(bizKey=true) 존재) / false
//	 */
//	private boolean existsBizKey(DOMAIN domain){
//		//
//		Field[] fields = domain.getClass().getDeclaredFields();
//
//		//
//		for(Field f : fields) {
//			SvcField svcField = f.getAnnotation(SvcField.class);
//
//			//
//			if(null != svcField && svcField.bizKey()) {
//				return true;
//			}
//		}
//
//		//
//		return false;
//	}
//	
//	/**
//	 * 업무키 필드 목록 조회
//	 * @param domain
//	 * @return
//	 */
//	private List<FieldAndOrder> getBizFields(DOMAIN domain){
//		//
//		List<FieldAndOrder> list = new ArrayList<>();
//		
//		//
//		if(null == domain) {
//			return list;
//		}
//
//		//
//		Field[] fields = domain.getClass().getDeclaredFields();
//
//		//
//		for(Field f : fields) {
//			SvcField svcField = f.getAnnotation(SvcField.class);
//
//			//업무키 존재하면 추가
//			if(null != svcField && svcField.bizKey()) {
//				list.add(FieldAndOrder.builder()
//						.field(f)
//						.order(svcField.order())
//						.build());
//			}
//		}
//		
//		//
//		return list;
//	}
//	
//	
//	/**
//	 * field명으로 메소드명(문자열) 생성 
//	 * 메소드명 규칙 : pre + 필드명[ + And + 필드명...] 
//	 * @param pre
//	 * @param fields
//	 * @return
//	 */
//	private String getMethodName(String pre, List<FieldAndOrder> fields) {
//		String str="";
//		for(FieldAndOrder e : fields) {
//			e.field.setAccessible(true);
//
//			if(SvcUtils.isNotEmpty(str)) {
//				str += "And";
//			}
//			str += SvcUtils.capitalize(e.field.getName());
//		}
//
//		//
//		return pre + str;		
//	}
//	/**
//	 * field 목록으로 각 field의 파라미터 타입 배열 생성
//	 * @param fields
//	 * @return
//	 */
//	private Class<?>[] getParamTypes(List<FieldAndOrder> fields){
//		if(SvcUtils.isEmpty(fields)) {
//			return new Class[] {};
//		}
//
//		//
//		Class<?>[] classes = new Class[fields.size()];
//		//
//		for(int i=0; i<fields.size(); i++) {
//			classes[i] = fields.get(i).field.getType();
//		}
//
//		//
////		log.debug("<<.getParamTypes - {}", classes);
//		return classes;
//	}
//	/**
//	 * field목록으로 파라미터 값 목록 생성
//	 * @param list
//	 * @param vo
//	 * @return
//	 * @throws IllegalArgumentException
//	 * @throws IllegalAccessException
//	 */
//	private Object[] getParamValues(List<FieldAndOrder> list, Object vo) throws IllegalArgumentException, IllegalAccessException{
//		if(SvcUtils.isEmpty(list)) {
//			return new Object[] {};
//		}
//
//		//
//		Object[] objects = new Object[list.size()];
//		for(int i=0; i<list.size(); i++) {
//			objects[i] = list.get(i).field.get(vo);
//		}
//
//
//		//
//		log.debug("<<.getParamValues - {}", objects);
//		return objects;
//	}
//
//	@SuppressWarnings("unchecked")
//	protected  void set(JPA j, MAPPER m, DOMAIN d) {
//		this.jpaRepo = (JpaRepository<DOMAIN, IDTYPE>) j;
//		this.mapper = (SvcMapper<DOMAIN, IDTYPE>) m;
//		this.domain = (DOMAIN) d;
//	}
//
//}
//
//
//@Builder
//class FieldAndOrder{
//	Field field;
//	int order;
//}
//
//
///**
// * FieldAndOrder를 order순으로 정렬
// * @author gravity@daumsoft.com
// * @since 2020. 8. 10.
// *
// */
//class FieldAndOrderComparator implements Comparator<FieldAndOrder>{
//
//	@Override
//	public int compare(FieldAndOrder o1, FieldAndOrder o2) {
//		return o1.order - o2.order;
//	}
	
}