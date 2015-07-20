#!/bin/bash


localname=`hostname`
result=""


result=`df | grep -v Available  | awk '{print $1}'`
result_file_system_list=($result)
result=`df | grep -v Available  | awk '{print $2}'`
result_total_size_list=($result)
result=`df | grep -v Available  | awk '{print $3}'`
result_used_size_list=($result)
result=`df | grep -v Available  | awk '{print $4}'`
result_available_size_list=($result)


for i in "${!result_file_system_list[@]}";
do
 result=`curl  --insecure -d "serverName=$localname&fileSystem=${result_file_system_list[$i]}&totalSize=${result_total_size_list[$i]}&availableSize=${result_available_size_list[$i]}&usedSize=${result_used_size_list[$i]}" https://[SERVER_IP]/hdd/heartbeat`
    
done

