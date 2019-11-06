/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mattjtodd;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.rabbitmq.RMQSink;
import org.apache.flink.streaming.connectors.rabbitmq.common.RMQConnectionConfig;

import java.util.Objects;
import java.util.Properties;

/**
 * Skeleton for a Flink Streaming Job.
 *
 * <p>For a tutorial how to write a Flink streaming application, check the
 * tutorials and examples on the <a href="http://flink.apache.org/docs/stable/">Flink Website</a>.
 *
 * <p>To package your application into a JAR file for execution, run
 * 'mvn clean package' on the command line.
 *
 * <p>If you change the name of the main class (with the public static void main(String[] args))
 * method, change the respective entry in the POM.xml file (simply search for 'mainClass').
 */
public class StreamingJob {

	public static void main(String[] args) throws Exception {
		// set up the streaming execution environment
		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

		kafka(env);
	}

	private static void kafka(StreamExecutionEnvironment env) throws Exception {
		env.setParallelism(4);

		Properties properties = new Properties();
		properties.setProperty("bootstrap.servers", "kafka:9094");
		FlinkKafkaConsumer<String> consumer = new FlinkKafkaConsumer<>("messages", new SimpleStringSchema(), properties);
		consumer.setStartFromLatest();
		DataStream<String> stream = env.addSource(consumer);

		ObjectMapper objectMapper = new ObjectMapper();

		final RMQConnectionConfig connectionConfig = new RMQConnectionConfig.Builder()
				.setHost("rabbit")
				.setVirtualHost("/")
				.setPort(5672)
				.setUserName("guest")
				.setPassword("guest")
				.build();

		stream
				.map(objectMapper::readTree)
				.filter(Objects::nonNull)
				.map(JsonNode::toString)
				.addSink(new RMQSink<>(
					connectionConfig,
					"queueName",
					new SimpleStringSchema()
				));

		env.execute("Test Job");
	}
}
