#!/usr/bin/env sh

docker config create metricbeat.yml ./metricbeat/metricbeat.yml
docker config create metricbeat-services.yml ./metricbeat/services.yml
docker config create logstash.conf ./logstash/logstash.conf
docker config create datasources ./grafana/provisioning/datasources/grafana-datasources.yaml
docker config create dashboards ./grafana/provisioning/dashboards/dashboards.yaml
docker config create metricbeat ./grafana/provisioning/dashboards/dashboard.json
