package ${packageName};

import com.rexyn.rent.frame.common.BaseQuery;
import com.rexyn.rent.frame.business.controller.BizBaseController;
import ${packageName?remove_ending(".controller")}.entity.${upperCamel};
import ${packageName?remove_ending(".controller")}.service.I${upperCamel}Service;
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
public class ${upperCamel}Controller extends BizBaseController<${upperCamel}, I${upperCamel}Service, BaseQuery<${upperCamel}>> {
    @Autowired
    public ${upperCamel}Controller(I${upperCamel}Service service) {
        super(service);
    }
}
