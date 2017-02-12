package com.rooney.es.transportclient;

/**
 * 
 * @author paul.rooney
 *
 */
public class TransportAddress {

    public static void main(String args[]) {
        
		if (args.length != 3) {
			System.out.println("usage: TransportAddress es_node_host es_tcp_port cluster_name");
			return;
		}

		// V 1.3.6 code
//		Settings settings = ImmutableSettings.settingsBuilder()
//				.put("cluster.name", args[2])
//                .put("client.transport.sniff", true).build();
//
//        TransportClient transportClient = new TransportClient(settings);
//		transportClient.addTransportAddress(new InetSocketTransportAddress(args[0], Integer.valueOf(args[1])));
//
//        ImmutableList<DiscoveryNode> connectedNodes = transportClient.connectedNodes();
//
//        for (DiscoveryNode node : connectedNodes) {
//            System.out.println(node.getHostName() + " " + node.getAddress());
//        }

//		transportClient.close();
    }

}
