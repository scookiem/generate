package ${packages};

import com.baomidou.mybatisplus.annotation.TableName;
import com.pkh.application.storage.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
<#list importSet as import>
import ${import};
</#list>

/**
 * ${comment}
 *
 * @author ${author!"author"}
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("${name}")
public class ${upperCamel}Entity extends BaseEntity {

    private static final long serialVersionUID = 1L;

<#list fieldInfoList as fieldInfo>
    /**
     * ${fieldInfo.originType}
     * ${fieldInfo.comment!"无备注"}
     */
    private ${fieldInfo.type.type} ${fieldInfo.lowerCamel};
</#list>
}
