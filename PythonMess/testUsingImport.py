#!/usr/bin/python
# Filename: mess.py

import ModToImport as x

def foo(): print 'new swapped in function'

def main():
    print x.getNoneAssignedVar()
    print x.getStrAssignedVar()
    
    x.setNoneAssignedVar("noneNEW")
    x.setStrAssignedVar("strNew")
    
    print x.getNoneAssignedVar()
    print x.getStrAssignedVar()
    
    x.noneAssignedVar = 'non set from another mod'
    print x.getNoneAssignedVar()
    
    x.getNoneAssignedVar = foo
    print x.getNoneAssignedVar()

if __name__ == '__main__':
    main()
else:
    print 'I am being imported from another module'