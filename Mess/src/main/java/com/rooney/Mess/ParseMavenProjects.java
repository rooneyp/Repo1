package com.rooney.Mess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import fr.dutra.tools.maven.deptree.core.InputType;
import fr.dutra.tools.maven.deptree.core.Node;
import fr.dutra.tools.maven.deptree.core.ParseException;
import fr.dutra.tools.maven.deptree.core.Parser;
import fr.dutra.tools.maven.deptree.core.StandardTextVisitor;
import fr.dutra.tools.maven.deptree.core.Visitor;

public class ParseMavenProjects {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
//        parseCustConfigServices("c:\\temp\\hsbc\\depends.txt");
          getVerOfSharedServices("c:\\temp\\bpllive\\devstack.txt");
    }

    private static void getVerOfSharedServices(String fileName) throws Exception {
        final List<String> urls = FileUtils.readLines(new File(fileName));
        for (String url : urls) {
            try {
                System.out.print(url + ",");
                String result = new RestTemplate().getForObject("http://" + url + "/info", String.class);
                JSONObject jsonObject = new JSONObject(result).getJSONObject("service");
              
                try {
                    Object identifier = jsonObject.get("type");
                    if(identifier.toString().equals("null")) {
                        identifier = jsonObject.get("name");
                    }
                    System.out.println(identifier + "," + jsonObject.get("version"));
                    
                } catch (org.json.JSONException e) {
                    System.out.println(new JSONObject(result).get("name"));
                }
            } catch (Exception e) {
                System.out.println("");
            }
        }
        
        
//        JSONObject jsonObject = new JSONObject(result).getJSONObject("service");
//        System.out.println(jsonObject.get("type"));
        
        
        
    }

    /**
     * mvn dependency:tree -DoutputType=dot -DoutputFile=\temp\depends.txt
     * https://github.com/adutra/maven-dependency-tree-parser
     */
    private static void parseCustConfigServices(String fileName) throws IOException, FileNotFoundException, ParseException {
        System.out.println("PROCESSING + " + fileName);
        final List<String> services = FileUtils.readLines(new File("c:\\temp\\services.txt"));
        final List<String> servicesMatched = new ArrayList<String>();
        
        InputType type = InputType.DOT;
        
        Reader r = new BufferedReader(new FileReader(fileName));
        Parser parser = type.newParser();
        Node tree = parser.parse(r);
        
//        printTree(tree);
        
        //visit all nodes but only emit where match occurs
//        new AbstractArtifactVisitor() {
//            void process(Node node) {
//                if(services.contains(node.getArtifactId())) {
//                    System.out.println(node.getArtifactId() + ":" + node.getVersion());
//                    servicesMatched.add(node.getArtifactId());
//                }
//            }
//        }.visit(tree);
        
        //search for every service, emit on match or no match
//        for (final String serviceToSearchFor : services) {
//            System.out.print("\n" + serviceToSearchFor + ", ");
//            
//            new AbstractArtifactVisitor() {
//                boolean found = false;
//              void process(Node node) {
//                  if(found == true){
//                      return;
//                  }
//                  
//                  if(node.getArtifactId().equals(serviceToSearchFor)) {
//                      System.out.print(node.getVersion());
//                      servicesMatched.add(node.getArtifactId());
//                      found = true;
//                  }
//              }
//          }.visit(tree);
//        }
        
        // Just emit on each node where classifier is svc
        final List<String> output = new ArrayList<String>();
        new AbstractArtifactVisitor() {
        void process(Node node) {
            if(node.getClassifier() != null && node.getClassifier().equals("svc")) {
                output.add(node.getArtifactId() + ":" + node.getVersion());
                    servicesMatched.add(node.getArtifactId());
                }
            }
        }.visit(tree);
        
        Collections.sort(output);
        for (String artifact : output) {
            System.out.println(artifact);
        }
        
        
        List<String> servicesNotMatched = new ArrayList<String>(services);
        servicesNotMatched.removeAll(servicesMatched);
        System.out.println("Not Matched: " + servicesNotMatched);
    }

    private static void printTree(Node tree) {
        StandardTextVisitor visitor = new StandardTextVisitor();
        visitor.visit(tree);
        System.out.println(visitor);
    }

    public static abstract class AbstractArtifactVisitor implements Visitor {

        public void visit(Node node) {
            process(node);

            for (Node child : node.getChildNodes()) {
                visit(child);
            }
        }

        abstract void process(Node node);
    }
}

