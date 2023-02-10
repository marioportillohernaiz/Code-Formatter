# Code-Formatter

This java program allows an input of unformatted code and outputs the same code with the correct format depending on how the user wants it. For exmaple, the number of indents such as 2 or 4 spaces per indent, brace location on the same line or own line or parameter spacing on brakets. Mainly the program formats programming languages such as Java or C++ or programming languages similar to those.

For example indent:
```
if (param == 0) {
    return true;
}
```

Or:
```
if (param == 0) {
  return true;
}
```

Example of paramater spacing On:
```
if ( param == 0 ) {
```

Or Off:
```
if (param == 0) {
```

Exmaple of brace location inline:
```
if (param == 0) {
    return true;
}
```

Or ownline:
```
if (param == 0) 
{
    return true;
}
```



Exmaple of input:
```
private bool isPositive(int param){
if (param == 0) {return true;
	}
	else if (param == 1
3) {
callParameter();return false;}else {
	return null;
	}
}
```

Then output with 4 indents, parameter spacing on and owline braces:
```
private bool isPositive( int param ){
    if ( param == 0 ) {
        return true;
    }
    else if ( param == 13 ) {
        callParameter();
        return false;
    }
    else {
        return null;
    }
}
```
