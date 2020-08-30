# GW2MIDI  
This tool allows you to use your MIDI keyboard as input to play instruments ingame. Octave swapping and transposing is done automatically for you.

## How to Use

### Windows  
1. Make sure that your keybindings are right ingame:  
![KeyLayout](http://gw2mb.com/image/controls.png)  
2. Download the [default package](https://github.com/Crigges/GW2MIDI/releases/download/v0.1/gw2midi_v0.1_jre.zip) that comes with a JRE  
3. Extract it to any location  
4. Run the `START_GWMIDI.BAT` **you will have to start it as administrator *IF* GW2 is also running as administator**  
5. Select your Input MIDI device. Your device may appear multiple times, make sure to select the right one with a working transmitter  
6. Test if the input is actually received, it will be logged to the console window  
7. Go back to GW2 and equip your instrument, keep GW2 in the foreground  
8. Done! Notes played on your MIDI keyboard should now be translated to GW2

### Mac / Linux  
This is untested so far but in theory you just need a working JRE and execute the `gw2midi.jar`

## Usage Guidelines  
* The middle C on your Keyboard should correspond to the middle C on the instrument in GW2. According to the MIDI standard the middle C should use `48` or `0x30` as keycode  
* Select a piano scale to transpose your song on the fly. So for example, if you select `A Major` as the piano scale you can just use the corresponding keys and they will be transposed to `C Major` automatically  
![enter image description here](https://www.pianoscales.org/images/A.png)  
* All major piano scales will be transposed to `C Major`. All minor piano scales will be transposed to `A Minor`  
* The `Double Swap Delay` represents an ingame limitation. If your ping is high or your FPS are low you might need to increase the delay. A lower value is better, however if you set it to low, octave swapping will be inconsistent or stop working at all.

## Troubleshooting

If you run into any Problem please check the `Common Problems` section first. If this doesn't help, feel free to contact me directly ingame `Crigges.8735` or create a ticket here. Always try to include as much information as possible.

### Common Problems  
* Octave swapping isn't working properly:  
    * Double-check your keybindings!  
    * Try to increase the `Double Swap Delay`  
    * Swap the map instance maybe it is lagging  
    * You might play to fast for GW2 to handle, there is nothing I can do about that  
* I can't see my MIDI Keyboard:  
    * Make sure its drivers are installed properly  
    * Can you see your device in other software?  
* My Keyboard is working but GW2 isn't receiving any Input  
    * Double-check your keybindings!  
    * Even if GW2 isn't running as administrator run the `START_GWMIDI.BAT` as administrator  
    * Make sure GW2 is in the foreground and focused

### Known Issues  
* Currently there is no support for Instruments where you need to hold keys like the Horn.

## Is this legal to use

There is no official statement for this program in particular however this is the [current official stance on macros](https://en-forum.guildwars2.com/discussion/65554/policy-macros-and-macro-use) from ArenaNet: ![enter image description here](https://i.snipboard.io/L8SmYJ.jpg)Therefore ***I personally*** consider this program totally legal. However, use it on your own risk!

## Technical Background

* Double Swap Delay  
    * There is an ingame limitation that you may not swap the octave twice without a server response from the first swap this is why the delay is needed. It is only applied when necessary, it even uses a busy waiting mechanism to be as accurate as possible.  
* Don't swap If necessary  
    * The program is aware that it is possible to play the `C` from the upper octave so it only swaps if really necessary
