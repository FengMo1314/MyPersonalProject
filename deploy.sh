#!/bin/bash
packageName=FangYuanAndWanHe-Back-0.0.1-SNAPSHOT.jar
packagePath=target/$packageName
projectPath=/root/MyProject/FangYuanAndWanHe-back/$packagePath
id=`ps -ef | grep $packageName | grep -v grep | awk '{print $2}'`
if ((id>0))
then
  kill -s 9 $id
fi
`nohup java -jar $projectPath > /tmp/$packageName-$id.log 2>&1 &`