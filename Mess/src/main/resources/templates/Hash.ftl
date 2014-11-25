Retrieving from a Map/Hash

Value found is ${data["key1"]}
Value found is ${data.key1}

<#assign myKey = "key2">
Value found is ${data[myKey]}

Value found is ${data.nestedMap.foo}

Value found is ${data.nestedMap.nestedNestedMap.apple}


Value found is ${data.myBean.name}

Value found is ${data.myBeanInner.name}

Iterate through Hash
<#assign h = {"name":"mouse", "price":50}>
<#list h?keys as key>${key} = ${h[key]}; </#list>


Do something value if not present

<#assign h = {"one":""}>

<#assign x = ["red", 16, "blue", "cyan"]>
"blue": ${x?seq_contains("blue")?string("yes", "no")}
"blue": ${x?seq_contains("blue")?string}

<#if x?seq_contains("blue")>
  blue is present
<#else>
  blue is not present
</#if>

<#-- add a value to sequence -->
<#assign x = x + ["green"]]>
"green": ${x?seq_contains("blue")?string("yes", "no")}

<#-- conditional negation -->
<#if ! x?seq_contains("blue")>
  blue is not present
<#else>
  blue is present
</#if>
