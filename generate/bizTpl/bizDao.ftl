package ${packageName};

import ${packageName?remove_ending(".dao")}.entity.${upperCamel};
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * ${comment!}-dao
 *
 * @author ${author!}
 */
public interface ${upperCamel}Dao extends BaseMapper<${upperCamel}> {

}
