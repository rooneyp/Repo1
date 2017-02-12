package com.rooney.es.transportclient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.search.MultiSearchRequestBuilder;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;

public class MultiTest {
	@Parameter(names="-f", description="file containing ids")
	private String idsFile;
	
	@Parameter(names="-h", description ="host name")
	private String hostName ;
	
	@Parameter(names="-p", description ="port")
	private int port = 9300;
	
	@Parameter(names="-c", description ="cluster name")
	private String clusterName;
	
	@Parameter(names="-m", description ="multi mode: mget, search or msearch")
	private String multiMode ;
	
	@Parameter(names="-w", description ="worker count")
	private int workerCount = 4 ;
	
	@Parameter(names="-b", description ="batch size")
	private int batchSize = 250;
	
	@Parameter(names="-s", description ="how many terms per msearch search")
	private int msearchTerms;
	
	@Parameter(names="-i", description ="index name")
	private static String indexName ;
	
	@Parameter(names="-t", description ="type name")	
	private static String typeName ;
	
	@Parameter(names="-v", description ="verbose")	
	private static boolean verbose ;
	
	@Parameter(names="-l", description ="limit ids processed")	
	private static int limitIds ;

	
	
    public static void main(String[] args) throws IOException {
        new MultiTest().doMain(args);
    }

    public void doMain(String[] args) throws IOException {
    	System.out.println("called with " + Lists.newArrayList(args));
    	new JCommander(this, args);
    	//TODO logs all params
    	
    	Client client = createClient();
    	ExecutorService executor = Executors.newFixedThreadPool(workerCount);
    	List<String> ids = loadIds();
    	
    	List<Future<Result>> callFutures = Lists.newArrayList();
    	
    	Stopwatch overallSW = Stopwatch.createStarted();
    	
    	for(List<String> batch : Lists.partition(ids, batchSize)) {
    		callFutures.add(executor.submit(createLoader(batch, client)));
    	}
    	
    	List<Result> results = new ArrayList<MultiTest.Result>();
    	for (Future<Result> future : callFutures) {
    		try {
    			results.add(future.get());
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
         
    	System.out.println("Finished " + ids.size() + " in " + overallSW  + " workers=" + workerCount + " batchSize=" + batchSize);
    	
    	if(verbose) {
    		for (Result result : results) {
    			System.out.println(result);
    		}
    	}
    	
    	executor.shutdown();
    	client.close();
    }

	private Callable<Result> createLoader(List<String> ids, Client client) {
		if(multiMode.equalsIgnoreCase("mget")){
			return new MGet(ids, client);
		} else if(multiMode.equalsIgnoreCase("search")){
			return new Search(ids, client);
		} else if(multiMode.equalsIgnoreCase("msearch")){
			return new MSearch(Lists.partition(ids, msearchTerms), client);
		}
		throw new RuntimeException("unknown mode: " + multiMode);
	}

	private List<String> loadIds() throws IOException {
		List<String> allLines = Files.readAllLines(Paths.get(idsFile));
		if(limitIds > 0 && limitIds < allLines.size()) {
			allLines = allLines.subList(0, limitIds);
		}
		
		return allLines;
	}
    
    public Client createClient() throws UnknownHostException {
		Settings settings = Settings.settingsBuilder()
				.put("cluster.name", clusterName)
                .put("client.transport.sniff", true)
                .build();

		TransportClient client = TransportClient.builder().settings(settings).build()
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(hostName), port));

        List<DiscoveryNode> connectedNodes = client.connectedNodes();

        for (DiscoveryNode node : connectedNodes) {
            System.out.println("Connected to: " + node.getHostName() + " " + node.getAddress());
        }
    	
    	return client;
    }
    
    
    public static class Result {
    	private int retrievedCount;
    	private List<String> documentsRetrieved;
    	String excutionTime;
    	
		public Result(int retrievedCount, List<String> documentsRetrieved, String exceutionTime) {
			super();
			this.retrievedCount = retrievedCount;
			this.documentsRetrieved = documentsRetrieved;
			this.excutionTime = exceutionTime;
		}

		@Override
		public String toString() {
			return "Result [retrievedCount=" + retrievedCount + ", excutionTime=" + excutionTime
					+ ", documentsRetrieved=" + documentsRetrieved + "]";
		}

		
    }
    
    
    public static class MGet implements Callable<Result> {
		private List<String> ids;
		private Client client;

		public MGet(List<String> ids, Client client) {
			super();
			this.ids = ids;
			this.client = client;
		}

		public Result call() throws Exception {
			Stopwatch sw = Stopwatch.createStarted();
			
	    	MultiGetResponse multiGetItemResponses = client.prepareMultiGet()
	    		    .add(indexName, typeName, ids)
	    		    .get();
	    	
//	    	System.out.println(Thread.currentThread() + " start id " + ids.get(0) + " end id " + ids.get(ids.size()-1) + " time is " + sw.toString() + " for batch of " + ids.size());
	    	
	    	if(verbose) {
	    		for (MultiGetItemResponse multiGetItemResponse : multiGetItemResponses) {
	    			if(multiGetItemResponse.isFailed()) {
	    				System.out.println("FAILURE on " + multiGetItemResponse.getId());
	    			}
	    			System.out.println("Retrieved " + multiGetItemResponse.getResponse().getId());
	    		}
	    	}
	    	
	    	Result result = new Result(multiGetItemResponses.contextSize(), null, null);
	    	
			return result;
		}
    }
    	
    public static class Search implements Callable<Result> {
		private List<String> ids;
		private Client client;

		public Search(List<String> ids, Client client) {
			super();
			this.ids = ids;
			this.client = client;
		}

		public Result call() throws Exception {
			Stopwatch sw = Stopwatch.createStarted();
			
			//TODO use scrolls https://www.elastic.co/guide/en/elasticsearch/client/java-api/2.4/java-search-scrolling.html
			
			SearchResponse response = client.prepareSearch(indexName)
//			        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
					.addFields("_DOCUMENTS.ID_ISIN","_DOCUMENTS.ID_BB_UNIQUE_ID_BB_GLOBAL")
			        .setQuery(QueryBuilders.constantScoreQuery(QueryBuilders.termsQuery("_DOCUMENTS.ID_ISIN.raw", ids)))
			        .setSize(10000)
			        .execute()
			        .actionGet();
			
			//https://www.elastic.co/guide/en/elasticsearch/client/java-api/2.4/java-search-msearch.html

//			System.out.println(Thread.currentThread() + " start id " + ids.get(0) + " end id " + ids.get(ids.size() - 1)
//					+ " time is " + sw.toString() + " for batch of " + ids.size());

			if (verbose) {
				System.out.println("Retrieved " + response);
			}

			Result result = new Result(0, null, null);

			return result;
		}
    	
    }
    
    public static class MSearch implements Callable<Result> {
		private List<List<String>> ids;
		private Client client;

		public MSearch(List<List<String>> ids, Client client) {
			super();
			this.ids = ids;
			this.client = client;
		}

		public Result call() throws Exception {
			Stopwatch sw = Stopwatch.createStarted();
			
			//TODO use scrolls https://www.elastic.co/guide/en/elasticsearch/client/java-api/2.4/java-search-scrolling.html
			
			MultiSearchRequestBuilder multiSearch = client.prepareMultiSearch();
			
			for (List<String> singleSearchIds : ids) {
				SearchRequestBuilder srb = client.prepareSearch()
					.setIndices(indexName)
					.addFields("_DOCUMENTS.ID_ISIN","_DOCUMENTS.ID_BB_UNIQUE_ID_BB_GLOBAL")
//						.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
					.setQuery(
						QueryBuilders.constantScoreQuery(
								QueryBuilders.termsQuery("_DOCUMENTS.ID_ISIN.raw", singleSearchIds)	
								)
						).setSize(10000); //set by index.max_result_window 
				multiSearch.add(srb);
				if (verbose) {
					System.out.println("Added search to msearch start id " + singleSearchIds.get(0) + " end id " + singleSearchIds.get(singleSearchIds.size() - 1));
				}
			}
			

			//https://www.elastic.co/guide/en/elasticsearch/client/java-api/2.4/java-search-msearch.html
			MultiSearchResponse sr = multiSearch
					.execute()
					.actionGet();

//			System.out.println(Thread.currentThread() + " start id " + ids.get(0).get(0) + " end id " + ids.get(ids.size() - 1).get(ids.get(ids.size() - 1).size() -1)
//					+ " time is " + sw.toString() + " for batch of " + ids.size());

			if (verbose) {
				for (MultiSearchResponse.Item item : sr.getResponses()) {
					if (item.isFailure()) {
						System.out.println("FAILURE on " + item.getFailure());
					}
//					System.out.println("Retrieved " + item.getResponse());
				}
			}

			Result result = new Result(0, null, null);

			return result;
		}
    	
    }
    
    
}
