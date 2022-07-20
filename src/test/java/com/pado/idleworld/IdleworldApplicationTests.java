package com.pado.idleworld;

import com.pado.idleworld.domain.AccountRole;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IdleworldApplicationTests {

	@Test
	void contextLoads() {
		String s = AccountRole.ADMIN.toString();
		System.out.println(s);
		System.out.println(s.getClass());
	}

}
