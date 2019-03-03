# LiaAnalyzer
Visualizing analyzer of GameState and custom variables

![alt text](https://i.imgur.com/YYXXIaC.png)

A quick preview: https://streamable.com/k0yei

Commands:
- Left and Right Arrow to move one GameState forward/backward
- Space to pause/play 
- D to lock/unlock connection to debugger (when unlocked you can move between GameStates with the above commands up to the GameState the debugger is at)

Setup instructions:
- add "compile group: 'org.processing', name: 'core', version: '3.3.7'" to build.gradle dependencies
- add "while(Thread.activeCount()>0);" to NetworkingClient onClose function
- add Analyzer.java and rendering.java to lia package
- add to top of MyBot main function:
```java
Runnable r = new Analyzer();
Thread t = new Thread(r);
t.start();

PApplet.main("lia.rendering");
```
- add to top of MyBot update function:
```java
Analyzer.analyzer.push("GameState", state);
if(!rendering.gameRunning)	rendering.gameRunning=true;
```

Use case:
- Analyzer.analyzer.push(VariableNameAsDisplayedInDebugger, Variable);
- Analyzer.analyzer.push("Comment", "Enemy base reached!");
- Analyzer.analyzer.push("Unit" + unit.id + ": ", "Resource Spotted");
	
	If variable is parsable to text the value will be displayed in analyzer.

Initial GUI implementation provided by: https://github.com/grekiki/guiBot
