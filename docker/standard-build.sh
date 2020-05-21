git pull

docker_tag=$1

if [ ! -n "$docker_tag" ]; then
   docker_tag=latest
fi

mvn install -DskipTests

cp learn-service/target/*.jar docker

cd docker
docker build -t ccr.ccs.tencentyun.com/zhuiyi_docker_list/learn_yilearn-service:standard .

docker push ccr.ccs.tencentyun.com/zhuiyi_docker_list/learn_yilearn-service:standard

