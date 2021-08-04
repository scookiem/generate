    ${delimiter!}
<#list fieldInfoList as fieldInfo>
    /**
    * ${fieldInfo.originType}
    * ${fieldInfo.comment!"无备注"}
    */
    private ${fieldInfo.type.type} ${fieldInfo.lowerCamel};
</#list>
    ${delimiter!}
