package ${packages};

<#list importSet as import>
import ${import}
</#list>

/**
 * ${comment}
 */
@RestController()
@RequestMapping("${name}")
public class ${upperCamel}Controller {

}
