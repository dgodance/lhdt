package lhdt.service;

import java.util.List;

import lhdt.domain.layer.Layer;

public interface LayerService {

    /**
    * layer 목록
    * @return
    */
    List<Layer> getListLayer(Layer layer);
    
}
