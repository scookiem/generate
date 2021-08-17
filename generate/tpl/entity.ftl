package org.pkh.test.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
<#list importSet as import>
import ${import};
</#list>

/**
 * ${comment}-entity
 *
 * @author ${author!}
 */
@Data
public class ${upperCamel}Entity{

    private static final long serialVersionUID = 1L;

<#list fieldInfoList as fieldInfo>
    /**
     * ${fieldInfo.originType}
     * ${fieldInfo.comment!"无备注"}
     */
    <#if fieldInfo.nullable==0>
    @NotNull
    </#if>
    private ${fieldInfo.type.type} ${fieldInfo.lowerCamel};
</#list>
    ${delimiter!}
<#if customize??>
${customize}
</#if>
    ${delimiter!}
}
