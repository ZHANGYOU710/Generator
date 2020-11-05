package ${Package}.mapper;

import ${Package}.pojo.${T};
import java.util.List;

/**
 *
 * @author ${author}
 <#if version??>
 * @version ${version}
 </#if>
 * @date ${creatortime}
 */
public interface ${fileName} {

    int insert(${T} record);

    int update(${T} record);

    int del(<#list newMember as m><#if m.key==true>${m.javaType} ${m.conlumName}</#if></#list>);

    ${T} selectByPrimaryKey(<#list newMember as m><#if m.key==true>${m.javaType} ${m.conlumName}</#if></#list>);

    List<${T}> selectByRecord(${T} record);
}