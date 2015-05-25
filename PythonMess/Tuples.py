from collections import namedtuple



def testNamedTuple() :
    MyNamedTupleType = namedtuple('myNamedtuple', 'id name version '
                    'state')
    
    #this is probably incorrect use, but very handy :) 
    MyNamedTupleType.name = 'my name'
    MyNamedTupleType.name = 'my name is UPDATABLE'
    MyNamedTupleType.newField = 'my new field'
    print MyNamedTupleType.name, MyNamedTupleType.newField  # my name is UPDATABLE my new field
    
    MyNamedTupleType2 = namedtuple('myNamedtuple2', ['a', 'b']) # works, but doesn't show up on command completion
    MyNamedTupleType2.a = 'a is set'
    print 'MyNamedTupleType2=',MyNamedTupleType2,' MyNamedTupleType2.a=', MyNamedTupleType2.a # MyNamedTupleType2= <class '__main__.myNamedtuple2'>  MyNamedTupleType2.a= a is set

    myNamedTupleInstance2 = MyNamedTupleType2(a='aaa', b='bbb')
    print 'myNamedTupleInstance2=',myNamedTupleInstance2    #myNamedTupleInstance2= myNamedtuple2(a='aaa', b='bbb')
    
    
    # proper use
    myNamedTupleInstance = MyNamedTupleType(id=1, name='foo', version=2, state="CREATED") #needs all fields
    print myNamedTupleInstance, type(myNamedTupleInstance)  #myNamedtuple(id=1, name='foo', version=2, state='CREATED') <class '__main__.myNamedtuple'>
    
    #myNamedTupleInstance.name = 'name updated'  # FAIL!!!!!!!!!!!!
#     lazyInitNamedTupleInstance = MyNamedTupleType()  # FAIL!!!!!!!!!!!!

    
    
def testTuple():
    (x, y, z) = (42, 13, "hike") # init many local vars
    print x,y,z
    x = 100
    print x,y,z

    immTuple = (42, 13, "hike") # init many local vars
    print immTuple[1]
#     immTuple[1] = 1000   FAILS!!!!
    
        
#     print locals()


# testTuple()    
testNamedTuple()