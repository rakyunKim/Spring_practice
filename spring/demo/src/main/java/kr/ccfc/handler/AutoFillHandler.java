package kr.ccfc.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import kr.ccfc.base.BaseUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AutoFillHandler implements MetaObjectHandler {

    /**
     * 插入元对象字段填充（用于插入时对公共字段的填充）
     * 新增数据时要添加的为：
     * 1 创建人
     * 2 创建时间
     * 3 lastupdatetime
     *
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {

        Long userId = BaseUtil.getUserId();
        this.strictInsertFill(metaObject, "createBy", Long.class, userId); // 创建人
        this.strictInsertFill(metaObject, "created", Date.class, new Date()); // 创建时间
        this.strictInsertFill(metaObject, "lastUpdateTime", Date.class, new Date()); // 修改时间
    }


    /**
     * 更新元对象字段填充（用于更新时对公共字段的填充）
     * 1 修改人
     * 2 修改时间
     *
     * @param metaObject 元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        Long userId = BaseUtil.getUserId();

        this.strictUpdateFill(metaObject, "modifyBy", Long.class, userId); // 修改人
        this.strictUpdateFill(metaObject, "lastUpdateTime", Date.class, new Date()); // 修改时间
    }

}
