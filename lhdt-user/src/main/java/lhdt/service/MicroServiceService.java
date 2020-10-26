package lhdt.service;

import lhdt.domain.microservice.MicroService;

import java.util.List;

public interface MicroServiceService {

    /**
     * 마이크로 서비스 목록
     * @param microService
     * @return
     */
    List<MicroService> getListMicroService(MicroService microService);
}
