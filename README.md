AndroidInfrared
===============

Android Infrared is a infrared library drives IR LED built in Samsung devices, such as S4, Note 3.

Protocol Supported
-------------------

Android Infrared supports most of popular protocols avaiable nowadays, including:

* NEC
* Sony
* RC5
* RC6
* DISH
* Sharp
* Panasonic
* JVC


Code Sample
------------

```java
  IrdaManager manager = IrdaManager.getIrdaManager(getContext());
  
  String necCode = IrdaProtocols.NEC.buildNEC(32, 0xFFA05F);
  manager.sendSequence(necCode);

  String rc6Code = IrdaProtocols.RC6.buildRC6(36, 2148541964L)
  manager.sendSequence(rc6Code);
```

Sister Project
---------------

To decode and record the ir code from a existing remote control, such as TV remote or AirCon Remote, you can try [IRRecorder](https://github.com/timnew/IRRecorder). 

Known Issue
---------------

Android Infrared uses the Samsung customized IR Blast API, and doesn't compatible with Android 4.4 KitKat standard `ConsumerIrManager`. 
The Adpoting to support KitKat standard API process is already in progress.

Road Map
-----------

* Support Android 4.4 KitKat `ConsumerIrManager` API
* Auto adpative between KitKat API and Samsung API
* Support Proto code
