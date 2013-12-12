package com.rooney.learn.antlr;

import java.io.InputStream;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.junit.Test;

public class GraphDslAntlrSampleTest {

	@Test
	public void test() {
	    //Reading the DSL script
	    InputStream is = 
	            ClassLoader.getSystemResourceAsStream("resources/graph.gr");
	    
	    //Loading the DSL script into the ANTLR stream.
	    CharStream cs = new ANTLRInputStream(is);
	    
	    //Passing the input to the lexer to create tokens
	    GraphLexer lexer = new GraphLexer(cs);
	    
	    CommonTokenStream tokens = new CommonTokenStream(lexer);
	    
	    //Passing the tokens to the parser to create the parse trea. 
	    GraphParser parser = new GraphParser(tokens);
	    
	    //Semantic model to be populated
	    Graph g = new Graph();
	    
	    //Adding the listener to facilitate walking through parse tree. 
	    parser.addParseListener(new MyGraphBaseListener(g));
	    
	    //invoking the parser. 
	    parser.graph();
	    
	    Graph.printGraph(g);
	}

}
