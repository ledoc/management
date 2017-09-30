package fr.treeptik.test;

import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import fr.treeptik.conf.ApplicationConfiguration;
import fr.treeptik.service.AlimentService;
import fr.treeptik.service.impl.AlimentServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ApplicationConfiguration.class })
@WebAppConfiguration
public class FirstTest {

	@Inject
	AlimentService alimentServiceImpl;

	@Test
	public void test_ml_always_return_true() {

		// assert correct type/impl
		assertTrue(alimentServiceImpl instanceof AlimentService);

	}
}
