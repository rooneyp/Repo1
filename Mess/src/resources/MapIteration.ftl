<#assign types =  {"INT":"Int", "BOOLEAN":"Boolean", "STRING":"String", "TIMESTAMP":"TimestampMillis",
                   "LONG":"Long", "FLOAT":"Float", "DOUBLE":"Double", "BYTE":"Byte", "CHAR":"Char"} >  

<#list types?keys as columnType>
    <#assign print_else = "">
    
    public long get${types[columnType]}(String name) throws RowAccessException {
        //Dimensions
        columnType is ${columnType}
        <#assign fpp = data.dimensionsByColumnType[columnType]>
        <#-- what does the above not work, when data.dimensionsByColumnType["LONG"] does >
        <#--
        <#list data.dimensionsByColumnType[columnType] as entry>
        entry is ${entry}
        </#list>
        -->
    <#--    
    <#list data.dimensionsByColumnType[columnType] as entry>
        ${print_else!}if("${entry.key.fieldName}".equals(name)) {
            return dimensionKey.${entry.value.fieldGetMethod};
        } 
        <#assign print_else = "else "/>
    </#list>  
        -->
        <#assign print_else = "">
        //Measures
        <#--
    <#list data.measuresByColumnType[columnType] as entry>
        ${print_else3!}if("${entry.key.fieldName}".equals(name)) {
            return ${entry.value.field};
        } 
        <#assign print_else = "else ">
    </#list>
-->
        throw new RowAccessException("Unknown long field, name: " + name);    
    }
</#list>