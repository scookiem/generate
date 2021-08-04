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
 * @author ${author!}
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("${name}")
public class ${upperCamel}Entity extends BaseEntity {

    private static final long serialVersionUID = 1L;

<#include "entityField.ftl">
}
