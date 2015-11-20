package util;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

public class ConversorDeDataTest {
	
	@Test
	public void converterData() throws Exception {
		LocalDate data = ConversorDeData.getInstance().converterData("17/09/1972");
		assertEquals("1972-09-17", data.toString());
	}
}
