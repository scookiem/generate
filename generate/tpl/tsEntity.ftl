import BaseEntity from './BaseEntity';

export default class ${upperCamel}Entity extends BaseEntity {
<#list fieldInfoList as fieldInfo>
  /**
  * ${fieldInfo.comment!"无备注"}
  */
  ${fieldInfo.lowerCamel}?: ${fieldInfo.type.tsType};
</#list>
${delimiter!}
<#if customize??>
  ${customize}
</#if>
${delimiter!}
}
