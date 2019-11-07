#!/usr/bin/env sh

docker config rm metricbeat.yml
docker config rm metricbeat-services.yml
docker config rm logstash.conf
docker config rm datasources
docker config rm dashboards
docker config rm metricbeat
