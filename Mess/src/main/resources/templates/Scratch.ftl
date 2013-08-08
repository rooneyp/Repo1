<#assign method="endsWithIgnoresCase">
<#assign param1Name="str">
<#assign param2Name="suffix">

<#list ["String", "int", "long", "float", "double", "boolean", "char", "byte"] as columnType>
    <#list ["String", "int", "long", "float", "double", "boolean", "char", "byte"] as columnType2>

    public boolean ${method}(${columnType} ${param1Name}, ${columnType2} ${param2Name}) {
        <#if columnType == "String">
            <#assign param1 = param1Name>
        <#else>
            <#assign param1 = "typeConv.stringValueOf(${param1Name})">
        </#if>  
        <#if columnType2 == "String">
            <#assign param2 = param2Name>
        <#else>
            <#assign param2 = "typeConv.stringValueOf(${param2Name})">
        </#if>     
        return ${method}(${param1}, ${param2});
    }    
    </#list>    
</#list>
<#--
-->