package kr.ccfc.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ChartsVo {

    private List<String> columns;

    private List<Map<String, Object>> rows;

}
