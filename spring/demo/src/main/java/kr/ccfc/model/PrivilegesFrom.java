package kr.ccfc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrivilegesFrom {

    private Long list;
    private Long read;
    private Long write;
    private Long replay;
    private Long comment;
    private Long url;
    private Long upload;
    private Long download;
}
