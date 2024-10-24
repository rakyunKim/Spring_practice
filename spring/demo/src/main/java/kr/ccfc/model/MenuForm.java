package kr.ccfc.model;

import kr.ccfc.domain.SysMenu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuForm {

    private SysMenu menu;

    private PrivilegesFrom privileges;

}
