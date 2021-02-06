package net.feedthemadness.glib.command.executor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ExecutorReference {
	
	private final String id;
	
	private final ICommandExecutor executor;
	private final Method method;
	private final Class<?>[] parameterTypes;
	
	public ExecutorReference(ICommandExecutor executor, Method method, String id) {
		this.id = id;
		
		this.executor = executor;
		this.method = method;
		
		Class<?>[] parameterTypes = method.getParameterTypes();
		for(int i = 0; i < parameterTypes.length; i++) {
			if(parameterTypes[i] == boolean.class) {
				parameterTypes[i] = Boolean.class;
			} else if(parameterTypes[i] == byte.class) {
				parameterTypes[i] = Byte.class;
			} else if(parameterTypes[i] == char.class) {
				parameterTypes[i] = Character.class;
			} else if(parameterTypes[i] == short.class) {
				parameterTypes[i] = Short.class;
			} else if(parameterTypes[i] == int.class) {
				parameterTypes[i] = Integer.class;
			} else if(parameterTypes[i] == long.class) {
				parameterTypes[i] = Long.class;
			} else if(parameterTypes[i] == float.class) {
				parameterTypes[i] = Float.class;
			} else if(parameterTypes[i] == double.class) {
				parameterTypes[i] = Double.class;
			}
		}
		this.parameterTypes = parameterTypes;
	}
	
	public String getId() {
		return id;
	}
	
	public void dispatch(Object[] args) {
		
		try {
			method.invoke(executor, args);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public boolean validateType(Object[] args) {
		
		if(parameterTypes.length != args.length) return false;
		
		for(int i = 0; i < parameterTypes.length; i++) {
			if(!parameterTypes[i].isAssignableFrom(args[i].getClass())) return false;
		}
		
		return true;
	}
	
}
