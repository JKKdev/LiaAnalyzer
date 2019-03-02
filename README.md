# LiaAnalyzer
Visualizing analyzer of GameState and custom variables

![alt text](https://i.imgur.com/hqB02ew.png)

A quick preview: https://streamable.com/k0yei

Setup instructions:
- add "compile group: 'org.processing', name: 'core', version: '3.3.7'" to build.gradle dependencies
- add cleanup function to Bot interface
- add a call to said funtion in NetworkingClient onClose funtion
- add Analyzer.java and rendering.java to lia package
- add to MyBot main function:
```java
analyzer = new Analyzer();
Runnable r = analyzer;
Thread t = new Thread(r);
t.start();
```
- add to MyBot:
```java
@Override
public void cleanup() {
	PApplet.main("lia.rendering");
	if(!rendering.gameDone)	rendering.gameDone=true;
	while(Thread.activeCount()>0);
}
```
- add "public static Analyzer analyzer;" as a global variable in MyBot

Use case:
- analyzer.push(VariableNameAsDisplayedInDebugger, Variable);
- analyzer.push("Comment", "Enemy base reached!");
- analyzer.push("Unit" + unit.id + ": ", "Resource Spotted");

Within MyBot package from any other class:
- MyBot.analyzer.push("Unit" + unit.id + ": ", "Resource Spotted");
	
	If variable is parsable to text the value will be displayed in analyzer.

Initial GUI implementation provided by: https://github.com/grekiki/guiBot
