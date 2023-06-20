package ${packageName};

import com.baomidou.mybatisplus.annotation.TableName;
<#if hasDate>
import com.fasterxml.jackson.annotation.JsonFormat;
</#if>
import com.rexyn.platform.frame.basic.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import lombok.NoArgsConstructor;
<#if hasDate>
import org.springframework.format.annotation.DateTimeFormat;
</#if>
<#list importSet as import>
import ${import};
</#list>

/**
 * ${comment!}-entity
 *
 * @author ${author!}
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@TableName("${originName}")
public class ${upperCamel} extends BaseEntity {
<#list fieldInfoList as fieldInfo>
    /**
     * ${fieldInfo.originType}
     * ${fieldInfo.comment!"无备注"}
     */
    <#if fieldInfo.type=="Date">
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    </#if>
    private ${fieldInfo.type.type} ${fieldInfo.lowerCamel};
</#list>
    ${delimiter!}
<#if customize??>
${customize}
</#if>
    ${delimiter!}
}
