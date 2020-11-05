package ${Package}.pojo;

${imports}

/**
 *
 * @author ${author}
<#if version??>
 * @version ${version}
</#if>
 * @date ${creatortime}
 */
@Data
@Accessors(chain = true)
public class ${fileName} {
<#list newMember as m>

    /**${m.commentName}**/
    <#if underline==true>
    private ${m.javaType} ${m.humpConlumName ? uncap_first};
    <#else>
    private ${m.javaType} ${m.conlumName ? uncap_first};
    </#if>
</#list>
}