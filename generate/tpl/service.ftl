package ${packageName};

import com.rexyn.platform.frame.core.base.BaseService;
import ${packageName?remove_ending(".service")}.entity.${upperCamel};
import ${packageName?remove_ending(".service")}.dao.${upperCamel}Dao;
import org.springframework.stereotype.Service;

/**
 * ${comment!}-service
 *
 * @author ${author!}
 */
@Service
public class ${upperCamel}Service extends BaseService<${upperCamel}Dao, ${upperCamel}> {

}
