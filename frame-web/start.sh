cd ./target
docker-compose -p webtest stop
cd ../

# 定义一个名称变量
network_name="mynetwork"

filterName=$(docker network ls | grep $network_name | awk '{ print $2 }')
if [ "$filterName" == "" ]; then
    # 不存在就创建
    docker network create $network_name
    echo "Created network $network_name success!!"
fi

#清理
../../maven/bin/mvn clean package docker:build

#拷贝Dockerfile
cp ./src/main/docker/Dockerfile ./target/Dockerfile

#拷贝compose.yml
cp ./src/main/docker/compose.yml ./target/compose.yml

#启动
cd ./target
docker-compose -p webtest up &
