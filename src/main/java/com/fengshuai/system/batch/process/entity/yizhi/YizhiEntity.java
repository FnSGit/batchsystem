package com.fengshuai.system.batch.process.entity.yizhi;

import com.fengshuai.util.date.DateUtil;
import com.fengshuai.util.middleware.GeodeUtil;
import com.fengshuai.util.objectClass.ObjectUtil;
import com.fengshuai.util.parse.JsonSerializable;
import lombok.Data;
import org.apache.geode.DataSerializable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public abstract class YizhiEntity extends JsonSerializable implements DataSerializable {

    public YizhiEntity() {
        this(false);
    }

    public YizhiEntity(boolean isEmpty) {
        if (!isEmpty) {
            init();
            defaultValueInit();
        }
    }



    protected String farendma;
    protected String fenhbios;
    protected String weihguiy;
    protected String weihjigo;
    protected String weihriqi;
    protected String weihshij;
    protected String shijchuo;
    protected String jiluztai;
    protected String yzhiztai;
    protected String yizhztai;

    abstract void defaultValueInit();

    protected void init() {
        farendma = "8801";
        fenhbios = "99";
        weihguiy = "88010000";
        weihjigo = "9901";
        weihriqi = DateUtil.getSystemDate();
        weihshij = weihriqi;
        shijchuo = "0";
        jiluztai = "0";
        yzhiztai = "0";
        yizhztai = "0";
    }


    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        List<Field> fields = ObjectUtil.getFields(this.getClass());
        for (Field field : fields) {
            Object object = ObjectUtil.getFieldValue(this, field);
            if (object==null)
                continue;
            if (object instanceof String) {
                map.put(field.getName(), (String) object);
            } else throw new RuntimeException("不支持的数据解析类型 "+object.getClass().getTypeName());
        }
        return map;
    }

    @Override
    public void toData(DataOutput out) throws IOException {

        GeodeUtil.toData(out,this);
    }

    @Override
    public void fromData(DataInput in) throws IOException, ClassNotFoundException {
        GeodeUtil.fromData(in, this);
    }
}
