package com.rooney.es.transportclient;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

public class ExtractDataInBulkFormat {

    public static void main(String[] args) throws IOException {
        String index = "bloomberg_instrument_xref_f7c906c8823b8232bb3efb16484aaf87";
        String type = "ID_ISIN";
        String clusterName = "stable";
        String hostName = "stablees01.dev2.bloombergpolarlake.com";
        int port = 9300;
        int timeValueMillis = 60000;
        int dataSizePerShard = 1000;
        int maxData = 100000;
        File outputFile = new File("/bloomberg_instrument.isins");
        
		Settings settings = Settings.settingsBuilder()
				.put("cluster.name", clusterName)
                .put("client.transport.sniff", true)
                .build();

		TransportClient client = TransportClient.builder().settings(settings).build()
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(hostName), port));
        
        QueryBuilder queryBuilder = buildQuery();
        
        SearchResponse scrollResp = client.prepareSearch(index)
        		.setTypes(type)
//                .addSort(SortParseElement.DOC_FIELD_NAME, SortOrder.ASC)
                .setScroll(new TimeValue(timeValueMillis))
                .setQuery(queryBuilder)
                .setSize(dataSizePerShard).execute().actionGet(); 
        
        //Scroll until no hits are returned
        int hitCount = 0;
        List<String> output = new ArrayList<String>();
        
        while (true) {
            for (SearchHit hit : scrollResp.getHits().getHits()) {
				hitCount++;
				if (hitCount > maxData) {
	                break;
	            }
                output.add(hit.getId());
            }
            scrollResp = client.prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(60000)).execute().actionGet();
            //Break condition: No hits are returned
            if (scrollResp.getHits().getHits().length == 0 || hitCount >= maxData) {
                break;
            }
        }
        
        FileUtils.writeLines(outputFile, output);
        
        client.close();
        
        System.out.println("finished writing " + hitCount + " to " + outputFile);
    }

	private static MatchAllQueryBuilder buildQuery() {
		return QueryBuilders.matchAllQuery();
	}
  
    
}
