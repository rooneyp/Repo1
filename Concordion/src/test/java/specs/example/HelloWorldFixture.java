package specs.example;

import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.ParsingException;
import nu.xom.XPathContext;

import org.concordion.api.Command;
import org.concordion.api.CommandCall;
import org.concordion.api.Element;
import org.concordion.api.Evaluator;
import org.concordion.api.ResultRecorder;
import org.concordion.api.extension.ConcordionExtender;
import org.concordion.api.extension.ConcordionExtension;
import org.concordion.api.extension.Extension;
import org.concordion.api.listener.ExecuteEvent;
import org.concordion.api.listener.ExecuteListener;
import org.concordion.api.listener.SpecificationProcessingEvent;
import org.concordion.api.listener.SpecificationProcessingListener;
import org.concordion.integration.junit4.ConcordionRunner;
import org.concordion.internal.Row;
import org.concordion.internal.TableSupport;
import org.concordion.internal.command.ExecuteCommand;
import org.junit.runner.RunWith;

/* Run this class as a JUnit test. */

@RunWith(ConcordionRunner.class)
public class HelloWorldFixture {
    
	//only triggered after processing of element and its children
	public class ExecuteListenerExtension implements ConcordionExtension {

		public void addTo(ConcordionExtender concordionExtender) {
			System.out.println("concordionExtender" + concordionExtender);
			
			concordionExtender.withExecuteListener(new ExecuteListener() {
				public void executeCompleted(ExecuteEvent e) {
					System.out.println("executeCompleted: "
							+ e.getElement().getText());
				}
			});
			
			concordionExtender.withSpecificationProcessingListener(new SpecificationProcessingListener() {
				
				public void beforeProcessingSpecification(SpecificationProcessingEvent event) {
					getNumberOfRowsForVerifyRowsTable(event);
				}

				private void getNumberOfRowsForVerifyRowsTable(
						SpecificationProcessingEvent event) {
					try {
						Builder parser = new Builder();
						Document doc = parser.build(new StringReader(event.getRootElement().toXML()));
						XPathContext context = new XPathContext("concordion", "http://www.concordion.org/2007/concordion");
						System.out.println(doc.toXML());
						
						int numberOfTableRows = doc.query("//table/@concordion:verifyRows/../tr", context).size();
						
					} catch (ParsingException ex) {
						System.err.println("Cafe con Leche is malformed today. How embarrassing!");
					} catch (IOException ex) {
						System.err.println("Could not connect to Cafe con Leche. The site may be down.");
					}
				}
				
				public void afterProcessingSpecification(SpecificationProcessingEvent event) {
					// TODO Auto-generated method stub
					
				}
			});			
		}

	}
	
	//weird as it does not trigger child elements execution
	public class LogCommandExtension implements ConcordionExtension {

	    private PrintStream stream;
	    
	    public LogCommandExtension withStream(PrintStream stream) {
	        this.stream = stream;
	        return this;
	    }

	    public void addTo(ConcordionExtender concordionExtender) {
	        concordionExtender.withCommand("http://myorg.org/my/extension", "log", new Command() {
	            
	            public void verify(CommandCall commandCall, Evaluator evaluator, ResultRecorder resultRecorder) {
	            }
	            
	            public void setUp(CommandCall commandCall, Evaluator evaluator, ResultRecorder resultRecorder) {
	            }
	            
	            public void execute(CommandCall commandCall, Evaluator evaluator, ResultRecorder resultRecorder) {
	                stream.println(commandCall.getElement().getText());
	            }
	        });
	    }
	}	
	
	public class MyExecuteCommandExtension implements ConcordionExtension {
		MyExecuteCommand command = new MyExecuteCommand();
		
		public void addTo(ConcordionExtender concordionExtender) {
			concordionExtender.withCommand("http://myorg.org/my/extension", "myexecute", command);
		}
		
		public List<String> getLastHeaderRow() {
			List<String> headerRowList = new ArrayList<String>();
			
			Row headerRow = command.getHeaderRow();
			
			if(headerRow != null) {
				for (Element cell : headerRow.getCells()) {
					headerRowList.add(cell.getText());
				}
			}
			
			return headerRowList;
		}
		
	    private final class MyExecuteCommand extends ExecuteCommand {
			private Row headerRow;

			/**
			 * records header row if 'execute' it called on a 'table' element. 
			 */
			public void execute(CommandCall commandCall, Evaluator evaluator, ResultRecorder resultRecorder) {
				if("table".equals(commandCall.getElement().getLocalName())) {
					System.out.println("MyExecute");
					TableSupport tableSupport = new TableSupport(commandCall);
					headerRow = tableSupport.getLastHeaderRow();
				}
				
				super.execute(commandCall, evaluator, resultRecorder);
			}
			
			
			public Row getHeaderRow() {
				return headerRow;
			}
		}

	}	
	

	@Extension
    public ConcordionExtension listenExtension = new ExecuteListenerExtension();	
	
//	@Extension
    public ConcordionExtension logCmdExtension = new LogCommandExtension().withStream(System.out);
    
//	@Extension
    public MyExecuteCommandExtension myExecuteCommandExtension = new MyExecuteCommandExtension();
    
    
	public void logLastHeaderRow() {
		System.out.println("logLastHeaderRow: " + myExecuteCommandExtension.getLastHeaderRow());
	}
	
    public void addRowCloneFromBaseRecord() {
        System.out.println("addRowCloneFromBaseRecord");
    }
    
    public void addRowCloneFromBaseRecord(String text) {
    	System.out.println("addRowCloneFromBaseRecord: " + text);
    }
    
    public void updateCell(String text) {
    	System.out.println("updateCell: " + text);
    }
    
    public void updateCell(String text, Object o) {
    	System.out.println("updateCell: " + text + ", " + o);
    }
    
    
    public Iterable<String> getSearchResultsFor(String searchString) {
    	Set<String> usernamesInSystem = new HashSet<String>();
    	usernamesInSystem.addAll(Arrays.asList(new String[] {"john.lennon", "ringo.starr", "george.harrison", "paul.mccartney"}));
    	
        SortedSet<String> matches = new TreeSet<String>();
        for (String username : usernamesInSystem) {
            if (username.contains(searchString)) {
                matches.add(username);
            }
        }
        return matches;
    }    
}
