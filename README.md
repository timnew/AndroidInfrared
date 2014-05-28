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

        // Create ConsumerIrManager instance, which provides the unified and enhanced API across Samsung private API and KitKat API.
        ConsumerIrManager manager = ConsumerIrManager.getSupportConsumerIrManager(context);

        // Check whether IrEmitter is avaiable on the device.
        if (!manager.hasIrEmitter()) {
            Log.e("AndroidInfraredDemo", "Cannot found IR Emitter on the device");
        }

        // Build IR Command with predefined schemes.
        IrCommand necCommand = IrCommand.NEC.buildNEC(32, 0x723F);
        manager.transmit(necCommand);

        // Build IR Command from Pronto code
        IrCommand prontoCommand = IrCommand.Pronto.buildPronto("0000 0067 0000 0015 0060 0018 0018 0018 0030 0018 0030 0018 0030 0018 0018 0018 0030 0018 0018 0018 0018 0018 0030 0018 0018 0018 0030 0018 0030 0018 0030 0018 0018 0018 0018 0018 0030 0018 0018 0018 0018 0018 0030 0018 0018 03f6");
        manager.transmit(prontoCommand);

        int NEC_FREQUENCY = 38000;
        int HDR_MARK = 9000;
        int HDR_SPACE = 4500;
        int BIT_MARK = 560;
        int ONE_SPACE = 1600;
        int ZERO_SPACE = 560;

        // Build IR Command with Builder
        IrCommandBuilder builder = IrCommandBuilder.irCommandBuilder(NEC_FREQUENCY); // Static factory method
        IrCommand builderCommand = builder
                .pair(HDR_MARK, HDR_SPACE)  // Lead-in sequence
                .pair(BIT_MARK, ONE_SPACE)  // 1
                .pair(BIT_MARK, ONE_SPACE)  // 1
                .pair(BIT_MARK, ONE_SPACE)  // 1
                .pair(BIT_MARK, ONE_SPACE)  // 1
                .pair(BIT_MARK, ONE_SPACE)  // 1
                .pair(BIT_MARK, ONE_SPACE)  // 1
                .pair(BIT_MARK, ZERO_SPACE) // 0
                .pair(BIT_MARK, ZERO_SPACE) // 0
                .mark(BIT_MARK)             // lead-out sequence
                .build();

        // Simplify Sequence building process by using Sqeuence Definition
        IrCommandBuilder.SequenceDefinition necSequence = IrCommandBuilder.simpleSequence(BIT_MARK, ONE_SPACE, BIT_MARK, ZERO_SPACE);
        IrCommandBuilder sequenceBuilder = IrCommandBuilder.irCommandBuilder(NEC_FREQUENCY); // Static factory method
        IrCommand sequenceCommand = builder
                .pair(HDR_MARK, HDR_SPACE)  // Lead-in sequence
                .sequence(necSequence, 4, 0xFC000000)
                .mark(BIT_MARK)             // lead-out sequence
                .build();

        // Build IR Command from Raw Data
        int[] sequence = IrCommandBuilder.buildRaw(2400, 600, 1200, 600, 1200, 600, 600, 600, 1200, 600, 600, 600);
        manager.transmit(40000, sequence);
        
```

Sister Project
---------------

To decode and record the ir code from a existing remote control, such as TV remote or AirCon Remote, you can try [IRRecorder](https://github.com/timnew/IRRecorder). 

<del>Known Issue</del>
---------------

<del>Android Infrared uses the Samsung customized IR Blast API, and doesn't compatible with Android 4.4 KitKat standard `ConsumerIrManager`. </del>
<del>The Adpoting to support KitKat standard API process is already in progress.</del>

Road Map
-----------

* <del>Support Android 4.4 KitKat `ConsumerIrManager` API</del>
* <del>Auto adpative between KitKat API and Samsung API</del>
* <del>Support Proto code</del>
