# nattgtuld-identity
An advanced library to generate and manage virtual identities.

## Dependencies
This library uses the following dependencies:  
**nattguld-http:** Used for HTTP communication. https://github.com/nattguld/nattguld-http  
**nattguld-util:** Contains various helper methods. https://github.com/nattguld/nattguld-util  
**generex:** Used for generating output using regex.  https://github.com/mifmif/Generex  

## About
This library is used to generate all kinds of virtual person identities. Be aware that this library depends heavily on 3rd party services to gather the identity details and could stop working at any time if a 3rd party service gets disbanned and no alternative is available.

## Examples
```java
Person person = IdentityHandler.generatePerson(Sex.EXAMPLE, new Range(minAge, maxAge), Country.EXAMPLE);
person.getCreds().getUsername();
person.getFirstName();
person.getBirthDay();
person.getStreet();
...
```
