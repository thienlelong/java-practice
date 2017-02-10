package com.agilityio.todo;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.env.Environment;

import java.io.PrintStream;

@SpringBootApplication
public class TodoAppApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(TodoAppApplication.class)
				.banner(new Banner() {
					@Override
					public void printBanner(Environment environment, Class<?> sourceClass,
											PrintStream out) {
						out.print("\n\t Todo App".toUpperCase());
					}
				})
				.run(args);
	}
}
