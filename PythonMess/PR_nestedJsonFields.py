import json

def processNestedList(data, indent=''):
    print '%s<component>' %(indent)
    for element in data:
        if type(element) == list:
            processNestedList(element, indent + '\t')
        else:
            print '%s<element>%s</element>' %(indent + '\t', element)
    print '%s</component>' %(indent)


def processNestedFields(data, indent=''):
    ''' supports Dict with single (nested) entry which is List or Dict or standard-data-type'''
    
    if type(data) == dict:
        if len(data) != 1:
            raise ValueError('dictionary must only have one entry')
        
        print '%s<component name="%s">' %(indent, data.keys()[0])
        processNestedFields(data.values()[0], indent + '\t')
        print '%s</component>' %(indent)
        
    elif type(data) == list:
        for element in data:
            processNestedFields(element, indent)
   
    else:
        print '%s<element>%s</element>' %(indent, data)


def processNestedBuildUsingList(data, indent='', result=[], joinChar=''):
    ''' supports 'data' Dict with single (nested) entry which is List or Dict or standard-data-type'''
    if type(data) == dict:
        if len(data) != 1:
            raise ValueError('dictionary must only have one entry')
        
        result.append('%s<component name="%s">' %(indent, data.keys()[0]))
        processNestedBuildUsingList(data.values()[0], indent + '\t', result)
        result.append('%s</component>' %(indent))
        
    elif type(data) == list:
        for element in data:
            processNestedBuildUsingList(element, indent, result)
   
    else:
        result.append('%s<element>%s</element>' %(indent, data))

    return joinChar.join(result) # convert list to String using an EOL char, perhaps '' or '\n'


def processNestedReturnString(data, indent='', result=''):
    ''' supports 'data = Dict with single (nested) entry which is List or Dict or standard-data-type'''
    
    if type(data) == dict:
        if len(data) != 1:
            raise ValueError('dictionary must only have one entry')
        
        result += '%s<component name="%s">\n%s\n%s</component>\n' %(indent, data.keys()[0],
                                                  processNestedReturnString(data.values()[0], indent + '\t', result),
                                                  indent)
    elif type(data) == list:
        for element in data:
            result += processNestedReturnString(element, indent, result)
   
    else:
        result += '%s<element>%s</element>\n' %(indent, data)
        
    return result
    
#processNestedList(json.loads('["somestring1","somestring2",["nestedstring1","nestedstring2",["nestednestedstring1","nestednestedstring2"]]]'))
#processNestedFields(json.loads('{"xml":["somestring1","somestring2",{"nested":["nestedstring1","nestedstring2",{"nestedNested1":["nestedNestedstring1","nestedNestedstring2"]}]}]}'))
#print processNestedReturnString(json.loads('{"xml":["somestring1","somestring2",{"nested":["nestedstring1","nestedstring2",{"nestedNested1":["nestedNestedstring1","nestedNestedstring2"]}]}]}'))
#print processNestedBuildUsingList(json.loads('{"xml":["somestring1","somestring2",{"nested":["nestedstring1","nestedstring2",{"nestedNested1":["nestedNestedstring1","nestedNestedstring2"]}]}]}')
print processNestedBuildUsingList(json.loads('{"xml":["Impact","RcrType","RcrStyle",{"RcrFieldChange":["ChangeType","NewValue","TargetLabel"]}]}')
                                  ,joinChar='\n')
