
package com.afilm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing //Base_Entity에 있는 날짜 자동 입력 활성화
@SpringBootApplication//(exclude = { SecurityConfig.class })
public class AfilmApplication {

	public static void main(String[] args) {
		SpringApplication.run(AfilmApplication.class, args);
	}

}
