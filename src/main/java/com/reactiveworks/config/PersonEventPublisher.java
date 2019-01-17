package com.reactiveworks.config;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import reactor.core.publisher.FluxSink;

@Component
class PersonEventPublisher implements ApplicationListener<PersonEvent>, Consumer<FluxSink<PersonEvent>> {

	private final Executor executor;
	private final BlockingQueue<PersonEvent> queue = new LinkedBlockingQueue<>();

	public PersonEventPublisher(Executor executor) {
		this.executor = executor;
	}

	@Override
	public void onApplicationEvent(PersonEvent event) {
		this.queue.offer(event);
	}

	@Override
	public void accept(FluxSink<PersonEvent> sink) {
		this.executor.execute(() -> {
			while (true)
				try {
					PersonEvent event = queue.take();
					sink.next(event);
				} catch (InterruptedException e) {
					ReflectionUtils.rethrowRuntimeException(e);
				}
		});
	}
}
