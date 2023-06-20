package ${packageName};

import com.rexyn.rent.frame.business.service.impl.BizBaseServiceImpl;
import ${packageName?remove_ending(".service.impl")}.entity.${upperCamel};
import ${packageName?remove_ending(".service.impl")}.dao.${upperCamel}Dao;
import ${packageName?remove_ending(".service.impl")}.service.I${upperCamel}Service;
import org.springframework.stereotype.Service;

/**
 * ${comment!}-service
 *
 * @author ${author!}
 */
@Service
public class ${upperCamel}ServiceImpl extends BizBaseServiceImpl<${upperCamel}Dao, ${upperCamel}> implements I${upperCamel}Service {
    public ${upperCamel}ServiceImpl() {
        super(processKey);
    }
}
