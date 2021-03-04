NOTES
* https://www.elastic.co/guide/en/elasticsearch/reference/current/docker.html
  
USAGE
```
docker-compose up  --remove-orphans
curl localhost:9200
curl localhost:9400
curl localhost:9600
docker-compose stop es01
docker-compose start es01

install chrome ext https://github.com/StephaneBour/sense-chrome

#download shakespeare_7.x.json.txt from github

#load 111396 docs 19.2mb
curl -X POST "localhost:9200/_bulk?pretty" -H 'Content-Type: application/x-ndjson' --data-binary @/Users/paul/Dev/Data/shakespeare_7.x.json.txt

#disable shard allocation and flish
curl -X PUT "localhost:9200/_cluster/settings?pretty" -H 'Content-Type: application/json' -d'
{  "persistent": {    "cluster.routing.allocation.enable": "primaries"  }}'

curl -X POST "localhost:9200/_flush/synced?pretty"

#drop node with primary shard
GET _cat/shards/shakespeare?v

docker-compose stop es02

#check shards
#SUCCESS!! replica becomes primary


curl -XPUT "http://localhost:9200/_cluster/settings" -d'
{
  "persistent": {
    "cluster.routing.allocation.enable": "none"
  }
}'



```