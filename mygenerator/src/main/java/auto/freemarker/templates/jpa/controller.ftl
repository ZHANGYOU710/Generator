<#if classPath??>
package ${classPath};
</#if>

${imports}
import ${Package}.pojo.${T};
import ${Package}.service.${Service};
import com.common.ReturnResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ${describe}
 *
 * @author ${author}
<#if version??>
 * @version ${version}
</#if>
 * @date ${creatortime}
 */
@RestController
@RequestMapping(value = "/${Controller}")
public class ${fileName} {
    @Autowired
    private ${Service} ${Service ? uncap_first};

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ReturnResult save(HttpServletRequest request, HttpServletResponse response,${T} record) {
        if (record.get<#list newMember as m><#if m.key==true><#if underline==true>${m.humpConlumName ? cap_first}<#else>${m.conlumName ? cap_first}</#if></#if></#list>()==null) {
            ${Service ? uncap_first}.insert(record);
        } else {
            ${Service ? uncap_first}.update(record);
        }
        return ReturnResult.ok();
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ReturnResult del(HttpServletRequest request, HttpServletResponse response,<#list newMember as m><#if m.key==true>${m.javaType} id</#if></#list>) {
        if (id == null) {
            return ReturnResult.build("id不能为空");
        }

        ${Service ? uncap_first}.del(id);
        return ReturnResult.ok();
    }

    @RequestMapping(value = "/selectByPrimaryKey", method = RequestMethod.POST)
    public ReturnResult selectByPrimaryKey(HttpServletRequest request, HttpServletResponse response,<#list newMember as m><#if m.key==true>${m.javaType} id</#if></#list>) {
        if (id == null) {
            return ReturnResult.build("id不能为空");
        }

        ${T} info = ${Service ? uncap_first}.selectByPrimaryKey(id);
        return ReturnResult.ok(info);
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ReturnResult getList(HttpServletRequest request, HttpServletResponse response,${T} record) {
        /**条件判断**/


        List<${T}> list = ${Service ? uncap_first}.getList(record);
        return ReturnResult.ok(list);
    }

    @RequestMapping(value = "/pageList", method = RequestMethod.POST)
    public ReturnResult pageList(HttpServletRequest request, HttpServletResponse response,${T} record,
                                 @RequestParam(value="pageNo",defaultValue = "1")Integer pageNo,
                                 @RequestParam(value="pageSize",defaultValue = "20")Integer pageSize) {
        /**条件判断**/


        PageInfo<${T}> info = ${Service ? uncap_first}.pageList(record,pageNo,pageSize);
        return ReturnResult.ok(info);
    }
}