package com.rooney.solr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.request.CollectionAdminRequest;
import org.apache.solr.client.solrj.response.CollectionAdminResponse;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

/*
 * https://cwiki.apache.org/confluence/display/solr/Using+SolrJ
 */
public class TestSolrJ {

	public static void main(String[] args) throws Exception {
		// basicQuery();

		// Add Collection
		CloudSolrClient solrClient = buildClient();
		SolrQuery query = new SolrQuery();

		query.setRequestHandler("/spellCheckCompRH");

	}


	private static void basicQuery() throws SolrServerException, IOException {
		CloudSolrClient solrClient = buildClient();

		// solrClient.setDefaultCollection("gettingstarted");
		
		SolrQuery query = new SolrQuery();
		query.set("collection", "gettingstarted");
		query.set("q", "*:*");

		QueryResponse response = solrClient.query(query);

		SolrDocumentList list = response.getResults();

		System.out.println(list);
	}

	private static void addDocument(SolrClient solrClient) throws Exception {
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", "552199");
		document.addField("name", "Gouda cheese wheel");
		document.addField("price", "49.99");
		UpdateResponse response = solrClient.add(document);
		System.out.println(response);
		// Remember to commit your changes!
		 
		solrClient.commit();
	}

	private static CloudSolrClient buildClient() {
		String zkHostString = "developes10.lab.bloombergpolarlake.com:9983";
		CloudSolrClient solrClient = new CloudSolrClient.Builder().withZkHost(zkHostString).build();
		return solrClient;
	}

	
	public static void createCollection(SolrClient solrClient) throws Exception {
		CollectionAdminRequest.Create req = new CollectionAdminRequest.Create(); 
		CollectionAdminResponse response = req.setCollectionName("foo") 
		                .setReplicationFactor(1) 
		                .setConfigName("bar") 
				.process(solrClient);
	}
}
