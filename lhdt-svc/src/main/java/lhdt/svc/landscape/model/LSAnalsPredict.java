package lhdt.svc.landscape.model;

import lhdt.svc.landscape.types.LSAnalsPredictType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LSAnalsPredict {
    private String output_img;
    private Long shielding_rate;
    private LSAnalsPredictType lsAnalsPredictType;
}
