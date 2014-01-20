

Hi
<#--  
<#assign aVar = statics['java.lang.System'].getProperty("foo")>
-->

<#if statics['java.lang.System'].getProperty("foo")??>
value is[${statics['java.lang.System'].getProperty("foo")}]
<#else>
value is not set
</#if>

