package lia;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lia.api.GameState;

public class Analyzer implements Runnable{

	private Map<String,List<Object>> vars = new ConcurrentHashMap<String, List<Object>>();
	int time = 0;
	public static Analyzer analyzer;
	
	@Override
	public void run() 
	{
		Analyzer.analyzer = this;
		while(true);
		
	}
	
	public Map<String,List<Object>> getVariables()
	{
		return this.vars;
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
	}
}
