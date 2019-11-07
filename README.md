### Overview

The following stack provides a sample data used to demonstrate the capabilities of the CNAB standard for packing Data 
tools into a repeatable unit which can be shared using a compatible docker registry such as Docker Hub.

More info about Docker App features can be found [here](https://docs.docker.com/app/working-with-app/).
More info about CNAB features can be found [here](https://cnab.io/).

#### Requirements

* A Docker engine with swarm and experimental features enabled
* A solid amount of memory and compute. I can run this with 5 cpu core and 5G of memory without issues on a 2019 MBP.

#### Running the app

To install and run the application only:

`sh < create-configs.sh`
`docker app install dockerbirmingham/data-pipeline`

This will install the latest version of the application pointed to by the latest tag.  It may take some time to download 
all of the images as they are pretty big.  The Jupyter notebook weighs in at about 8G!

To see what ports, volumes, network etc will be exposed on the network interface and general metdata about the bundle 
then use:

`docker app inspect dockerbirmingham/data-pipeline`

```
docker app inspect dockerbirmingham/data-pipeline
data-pipeline 0.6.0

Maintained by: matttodd

Services (16)       Replicas Ports     Image
-------------       -------- -----     -----
agent               1                  dockerbirmingham/data-pipeline@sha256:88989d2dd0783524ab5af4bb8f4f4be2ac82c10188e17c6c6bda862bb7a5676d
elasticsearch       1        9200      dockerbirmingham/data-pipeline@sha256:ef0cdf17f8d00d8d90a7872b6672bc44283c6204e86bdf0067f93e9f637aad2a
grafana             1        3000      dockerbirmingham/data-pipeline@sha256:29c47dca382edcf80bf17a5485b7a3ded0a90781148f5db371d0f129dca08d9a
jobmanager          1        8081      dockerbirmingham/data-pipeline@sha256:f6dd4ed1c326fcf237a76bd13c5d493ab1f40d90301376edd2a91aefd24f64ec
kafka               1                  dockerbirmingham/data-pipeline@sha256:e8bf797a9091bf669b3195ad9d1ff8473cccbbdc9cef9be8cd215afd512e743f
kibana              1        5601      dockerbirmingham/data-pipeline@sha256:d91852c480e53b3649731416bc425fa114013a3d3da2002440b4de0030f6ad47
metricbeat-host     1                  dockerbirmingham/data-pipeline@sha256:f612700dadb9c6397d67c9d5eabafb0d2e2b19cbbc36eeb5aca9ae19741a8a0b
metricbeat-services 1                  dockerbirmingham/data-pipeline@sha256:f612700dadb9c6397d67c9d5eabafb0d2e2b19cbbc36eeb5aca9ae19741a8a0b
nifi                1        8077      dockerbirmingham/data-pipeline@sha256:3a4cc9b4a93e40fa1f1976b33ce9b945f032f3302826b53061983cfc4498fb7b
notebook            1        4040,8888 dockerbirmingham/data-pipeline@sha256:1c10f355f0272b4e398bba1d8012907d02084f12ed78054458075d3990a2ecb1
portainer           1        9000      dockerbirmingham/data-pipeline@sha256:6e20dfbee714d294c3d4cefee9d2472724250af0074a0491b5638d06bb40d1cb
rabbit              1        15672     dockerbirmingham/data-pipeline@sha256:170ebed32a4a66404b1c2828e8c512af4d4f5f509eb11d6618c30b10235dbd92
spark-master        1        7077,8060 dockerbirmingham/data-pipeline@sha256:5a032a3bbe09c879a34e099b31e1dc9bb01c52533277807eeeef086869d5654a
spark-worker        1        8061      dockerbirmingham/data-pipeline@sha256:f615a5ed0b430c023ddf9d07e38ceb61b5aa695746b5e5ed49149179eea15ed7
taskmanager         1                  dockerbirmingham/data-pipeline@sha256:f6dd4ed1c326fcf237a76bd13c5d493ab1f40d90301376edd2a91aefd24f64ec
zookeeper           1        2181      dockerbirmingham/data-pipeline@sha256:7a7fd44a72104bfbd24a77844bad5fabc86485b036f988ea927d1780782a6680

Networks (2)
------------
data-pipeline
portainer

Volumes (10)
------------
nifi_content
nifi_data
nifi_db
nifi_flowfile
nifi_provenance
nifi_state
notebook_data
portainer_data
rabbit_data
```

To tear this all down use:

`docker app uninstall data-pipeline`
`sh < delete-configs.sh`
