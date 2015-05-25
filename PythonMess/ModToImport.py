#global

strAssignedVar = "SET on GLOBAL decl"
noneAssignedVar = None

def setNoneAssignedVar(valToUse):
    global noneAssignedVar 
    noneAssignedVar = valToUse

def setStrAssignedVar(valToUse):
    global strAssignedVar 
    strAssignedVar = valToUse
    
    
def getNoneAssignedVar():
    return noneAssignedVar

def getStrAssignedVar():
    return strAssignedVar    