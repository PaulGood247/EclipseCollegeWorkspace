package evote;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.lang.*;
import java.lang.reflect.*;

public class evoteservlet extends HttpServlet {
	
    private void processRequest(HTTPMethod method, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		PrintWriter out = response.getWriter();
		if(DAO.connect(out) && DAO.testConnection()){
			if(request.getPathInfo() != null){
				RestResolver resolver = new RestResolver(request.getPathInfo());
				if(resolver.matchFound()){
					Command com = resolver.getCommand();
					if(com != null){
						out.println(com.run(requestParameters(request)));
					}
				}else{
					response.setStatus(404);
					out.print("No path 4u.");
				}
			}else{
				// Connection test response here.
			}
			// Request routing code here.
			// Basically all the application code should be here.
			DAO.disconnect();
		}else{
			// DB connection failure, return 500 Internal Server Error.
			response.setStatus(500);
			response.resetBuffer();
		}
		out.flush();
        out.close();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(HTTPMethod.GET, request, response);
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(HTTPMethod.POST, request, response);
	}

	public void init() throws ServletException{
		
	}

	private Map<String, String[]> requestParameters(HttpServletRequest request){
		Enumeration<String> parameterNames = request.getParameterNames();
		Map<String, String[]> parameters = new HashMap<String, String[]>();
		String name;
		while (parameterNames.hasMoreElements()){
			name = parameterNames.nextElement();
			parameters.put(name, request.getParameterValues(name));
		}
		return parameters;
	}
	
	private Command createCommand(String className){
		// Null check is redundant but just in-case.
		if(className == null || className.isEmpty()){
			return null;
		}
		try {
			Class.forName("evote." + className, false, this.getClass().getClassLoader());
		}catch(ClassNotFoundException e){
			return null;
		}
		try{
			Constructor<?> ctor = Class.forName("evote." + className).getConstructor();
			return (Command)ctor.newInstance();
		}catch(InstantiationException e){
		}catch(InvocationTargetException e){
		}catch(ClassNotFoundException e){
		}catch(NoSuchMethodException e){
		}catch(IllegalAccessException e){
		}
		return null;
	}
	
	private class RestResolver {
		
		// TODO: Change Integer to Class and use it to create a new instance during runtime of said Class.
		private Map<Pattern, String> map = new HashMap<Pattern, String>();

		private Pattern matched;
		
		public RestResolver(String path){
			Matcher matcher;
			// Add more url-action -mappings here when required. Null can be used as place holder for command instance.
			map.put(Pattern.compile("/candidates"), "CandidateReadCommand");
			map.put(Pattern.compile("/vote"), "VoteCommand");
			for(Pattern pattern : map.keySet()){
				if((pattern.matcher(path)).find()){
					matched = pattern;
					break;
				}
			}
		}

		public Boolean matchFound(){
			return matched != null && map.containsKey(matched) && map.get(matched) != null;
		}
		
		public Command getCommand() {
			return createCommand(map.get(matched));
		}
	}
}