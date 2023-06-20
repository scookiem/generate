package ${packageName};

import com.rexyn.platform.frame.basic.BaseQuery;
import com.rexyn.platform.frame.core.base.BaseController;
import ${packageName?remove_ending(".controller")}.entity.${upperCamel};
import ${packageName?remove_ending(".controller")}.service.${upperCamel}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ${comment!}-controller
 *
 * @author ${author!}
 */
@RestController()
@RequestMapping("${name}")
public class ${upperCamel}Controller extends BaseController<${upperCamel}, ${upperCamel}Service, ${upperCamel}Query> {
    @Autowired
    public ${upperCamel}Controller(${upperCamel}Service service) {
        super(service);
    }
}
