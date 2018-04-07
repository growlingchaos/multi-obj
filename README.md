# Multiple objects proxy

### The problem

When you build a DSL and distribute it to clients, over time things get more complicated as you add
new features and adjust existing ones. At some point some refactoring is in order but it's better
to keep the DSL stable without breaking changes.

In this simple example the DSL is just an SPEL expression that perform calls on a single object 
instance but we want to decouple the two methods of this object. 

### The solution

The idea is to use a proxy object to be able to decouple the methods of the DSL in their own 
classes (and libraries).

### The code

_DISCLAIMER_: The implementation is just a proof of concept for the proposed solution and in no way
is meant to be product ready.

Start the code exploration at 
[`OperationEngine`](src/main/java/org/damage/multiobj/OperationEngine.java).
