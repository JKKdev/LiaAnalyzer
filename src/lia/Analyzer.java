package lia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lia.api.GameState;

public class Analyzer implements Runnable{

	public Map<String,List<Object>> vars = new HashMap<String, List<Object>>();
	int time = 0;
	
	@Override
	public void run() {
		
		rendering.analyzer = this;
		while(true);
		
	}
	
	public void push(String varName, Object var)
	{
		if(var instanceof GameState)	
		{
			this.time = Math.round(((GameState) var).time*10);
			if(!vars.containsKey(varName))	vars.put(varName, new ArrayList<Object>());
			vars.get(varName).add(var);
		}
		else
		{
			if(!vars.containsKey(varName + (char)0 + time))	vars.put(varName + (char)0 + time, new ArrayList<Object>());
			vars.get(varName + (char)0 + time).add(var);			
		}		
		//System.out.println("Name: " + varName + (char)0 + time + " Value: " + var);
		
	}
}
