package code;

import java.util.SortedMap;
import java.util.TreeMap;

public class ConsitentHashNoVirtural {
    public static void main(String[] args) {
        //step1 初始化：把服务器节点IP的哈希值对应到哈希环上
        // 定义服务器ip
        String[] tomcatServers = new String[]{"123.111.0.240","123.101.3.1","192.20.35.2","10.18.200.1"};
        //对服务端hash

        Integer hash = Integer.MAX_VALUE;

        SortedMap<Integer,String> serversHashMap = new TreeMap<>();

        for (String tomcatServer : tomcatServers) {
            int hashCode = Math.abs(tomcatServer.hashCode());
            int index = hashCode%hash;
            serversHashMap.put(index, tomcatServer);
        }
        System.err.println(serversHashMap);
        //step2 针对客户端IP求出hash值
        // 定义客户端IP
        String[] clients = new String[]{"10.78.12.3","113.25.63.1","126.12.3.8","10.18.200.149"};

        for (String client : clients) {

            int hashCode = Math.abs(client.hashCode());
            int clientIndex = hashCode%hash;


            SortedMap<Integer, String> tailMap = serversHashMap.tailMap(clientIndex);

            if(tailMap.isEmpty()){
                //取哈希环上的顺时针第一台服务器
                Integer integer = serversHashMap.firstKey();
                String serverIndex = serversHashMap.get(integer);
                System.out.println("tailMap null  "+client+ " 获取第一个元素 "+ integer+"  服务器: "+serverIndex);
            }else{

                Integer integer = tailMap.firstKey();
                String serverIndex = serversHashMap.get(integer);

                System.out.println("tailMap 不是空  "+client+ " 获取元素 "+ integer+"  服务器: "+serverIndex);
            }


        }
    }




}
