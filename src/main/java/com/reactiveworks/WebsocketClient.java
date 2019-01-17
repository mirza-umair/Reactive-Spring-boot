package com.reactiveworks;

import java.net.URI;
import java.time.Duration;

import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;

public class WebsocketClient {

	public static void main(String[] args) {
		WebSocketClient client = new ReactorNettyWebSocketClient();
		client.execute(URI.create("ws://localhost:8080/ws/person"),
				session -> session.receive().map(WebSocketMessage::getPayloadAsText).log().then())
				.block(Duration.ofSeconds(60L));
	}
}
