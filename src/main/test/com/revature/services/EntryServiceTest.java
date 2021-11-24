package com.revature.services;

import com.revature.JSONServlet;
import com.revature.Worm;
import com.revature.models.Entry;
import javafx.beans.binding.When;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.servlet.ServletException;
import javax.servlet.http.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EntryServiceTest {
	private EntryService entryService;
	private Worm worm;
	HttpServletRequest request;
	HttpServletResponse response;
	String create;
	String update;
	@BeforeEach
	public void setup(){
		EntryService.startEntryService();
		request = Mockito.mock(HttpServletRequest.class);
		response = Mockito.mock(HttpServletResponse.class);
		create = 			"{\n"+
			"\"firstName\":\"Ryan\",\n"+
			"\"lastName\":\"A\",\n"+
			"\"streetName\":\"Street3\",\n"+
			"\"cityName\":\"City1\",\n"+
			"\"stateName\":\"NM\",\n"+
			"\"zipCode\":540000,\n"+
			"\"phoneNumber\":\"1 (812) 251 0000\",\n"+
			"\"birthDay\":[\n" +
			"1998,\n" +
			"9,\n" +
			"1\n" +
			"]\n" +
			"}";
		update = 			"{\n"+
			"\"firstName\":\"Bryan\",\n"+
			"\"lastName\":\"A\",\n"+
			"\"streetName\":\"Street3\",\n"+
			"\"cityName\":\"City1\",\n"+
			"\"stateName\":\"NM\",\n"+
			"\"zipCode\":540000,\n"+
			"\"phoneNumber\":\"1 (812) 251 0000\",\n"+
			"\"birthDay\":[\n" +
			"1998,\n" +
			"9,\n" +
			"1\n" +
			"]\n" +
			"}";
	}
	@Test
	public void addEntryTest() throws IOException, ServletException {

		BufferedReader bufferedReader = mock(BufferedReader.class);
		when(request.getReader()).thenReturn(bufferedReader);
		when(bufferedReader.lines()).thenReturn(Arrays.stream(create.split("\n")));
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		when(response.getWriter()).thenReturn(writer);
		new JSONServlet().doPost(request,response);
		writer.flush();
		assert(stringWriter.toString().contains(Arrays.stream(create.split("\n")).collect(Collectors.joining())));

	}
	@Test
	public void getEntryTest() throws IOException, ServletException {
		when(request.getParameterMap()).thenReturn(new HashMap<String,String[]>(){{put("firstName",new String[]{"Ryan"});}});
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		when(response.getWriter()).thenReturn(writer);
		new JSONServlet().doGet(request,response);
		assert(stringWriter.toString().contains(Arrays.stream(create.split("\n")).collect(Collectors.joining())));
	}
	@Test
	public void updateEntryTest() throws IOException, ServletException {
		when(request.getParameterMap()).thenReturn(new HashMap<String,String[]>(){{put("firstName",new String[]{"Ryan"});}});
		BufferedReader bufferedReader = mock(BufferedReader.class);
		when(request.getReader()).thenReturn(bufferedReader);
		when(bufferedReader.lines()).thenReturn(Arrays.stream(update.split("\n")));
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		when(response.getWriter()).thenReturn(writer);
		new JSONServlet().doPut(request,response);
		assert(stringWriter.toString().contains(Arrays.stream(update.split("\n")).collect(Collectors.joining())));
	}
	@Test
	public void deleteEntry() throws ServletException, IOException {
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		when(response.getWriter()).thenReturn(writer);
		when(request.getParameterMap()).thenReturn(new HashMap<String,String[]>(){{put("firstName",new String[]{"Ryan"});}});
		new JSONServlet().doDelete(request,response);
		writer.flush();
		assert(Integer.parseInt(stringWriter.toString().trim()) >= 0);
	}

}
