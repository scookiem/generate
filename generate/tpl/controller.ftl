package ${packages};

<#list importList as import>
import ${import}
</#list>

/**
 * ${comment}
 */
@RestController()
@RequestMapping("${name}")
public class ${upperCamel}Controller {

}
