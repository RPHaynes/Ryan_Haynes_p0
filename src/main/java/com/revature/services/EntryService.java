package com.revature.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.revature.Worm;
import com.revature.models.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EntryService {
	public static void startEntryService(){
		Worm.getInstance("properties.xml");
	}
	public static void addEntry(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		ObjectMapper objectMapper =new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		Entry entry = new Entry();
		try {
			entry = objectMapper.readValue(req.getReader().lines().collect(Collectors.joining()), Entry.class);
		} catch (IOException e) {
			e.printStackTrace();
			resp.setStatus(500);
			resp.getWriter().println(e);
			return;
		}
		resp.setHeader("Content-Type", "application/json");
		resp.setStatus(201);
		resp.getWriter().println(objectMapper.writeValueAsString(Worm.add(entry)));
	}
	public static void getEntry(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Map<String,String[]> Params = req.getParameterMap();
		List<Object> matchValues = new ArrayList<>();
		List<Field> matchKeys = new ArrayList<>();
		for(String key: Params.keySet()){
			System.out.println(key);
			try {
				Field field = Entry.class.getDeclaredField(key);
				matchKeys.add(field);

				if (field.getType().isPrimitive()) matchValues.add(Arrays.stream(Array.get(Array.newInstance(field.getType(),1),0).getClass().getDeclaredMethods()).filter(tp -> tp.getName().contains("parse") && tp.getParameterCount() == 1 && Arrays.stream(tp.getParameterTypes()).filter(pr -> pr.equals(String.class)).count()  == 1).findFirst().orElse(null).invoke(null,Params.get(key)[0]));
				else if (field.getType().equals(LocalDate.class)) matchValues.add(  LocalDate.parse(Params.get(key)[0]));
					else matchValues.add(   Params.get(key)[0]);
			} catch (NoSuchFieldException e) {
				System.out.println("Field not Found");
			} catch (InvocationTargetException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		System.out.println(matchKeys);
		System.out.println(matchValues);
		Field[] fields = new Field[matchKeys.size()];
		List<Entry> list =  (List<Entry>) Worm.get(Entry.class,matchValues.toArray(), matchKeys.toArray(fields));
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		resp.setHeader("Content-Type", "application/json");
		resp.setStatus(200);
		resp.getWriter().println(mapper.writeValueAsString(list));
	}
	public static void deleteEntry(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
		Map<String,String[]> Params = req.getParameterMap();
		List<Object> matchValues = new ArrayList<>();
		List<Field> matchKeys = new ArrayList<>();
		for(String key: Params.keySet()){
			System.out.println(key);
			try {
				Field field = Entry.class.getDeclaredField(key);
				matchKeys.add(field);
				if (field.getType().isPrimitive()) matchValues.add(Arrays.stream(Array.get(Array.newInstance(field.getType(),1),0).getClass().getDeclaredMethods()).filter(tp -> tp.getName().contains("parse") && tp.getParameterCount() == 1 && Arrays.stream(tp.getParameterTypes()).filter(pr -> pr.equals(String.class)).count()  == 1).findFirst().orElse(null).invoke(null,Params.get(key)[0]));
				else matchValues.add(   Params.get(key)[0]);
			} catch (NoSuchFieldException e) {
				System.out.println("Field not Found");
			}
		}
		System.out.println(matchKeys);
		System.out.println(matchValues);
		Field[] fields = new Field[matchKeys.size()];
		int out = Worm.delete(Entry.class,matchValues.toArray(), matchKeys.toArray(fields));
		if (out < 0){
			resp.setHeader("Content-Type", "application/json");
			resp.setStatus(400);
			resp.getWriter().println(out);
			return;
		} else if (out == 0) {
			resp.setHeader("Content-Type", "application/json");
			resp.setStatus(404);
			resp.getWriter().println(out);
			return;
		}
		resp.setHeader("Content-Type", "application/json");
		resp.setStatus(200);
		resp.getWriter().println(out);


	}
	public static void updateEntry(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
		Map<String,String[]> Params = req.getParameterMap();
		List<Object> matchValues = new ArrayList<>();
		List<Field> matchKeys = new ArrayList<>();
		for(String key: Params.keySet()){
			System.out.println(key);
			try {
				Field field = Entry.class.getDeclaredField(key);
				matchKeys.add(field);
				if (field.getType().isPrimitive()) matchValues.add(Arrays.stream(Array.get(Array.newInstance(field.getType(),1),0).getClass().getDeclaredMethods()).filter(tp -> tp.getName().contains("parse") && tp.getParameterCount() == 1 && Arrays.stream(tp.getParameterTypes()).filter(pr -> pr.equals(String.class)).count()  == 1).findFirst().orElse(null).invoke(null,Params.get(key)[0]));
				else matchValues.add(   Params.get(key)[0]);
			} catch (NoSuchFieldException e) {
				System.out.println("Field not Found");
			}
		}
		System.out.println(matchKeys);
		System.out.println(matchValues);
		Field[] fields = new Field[matchKeys.size()];
		ObjectMapper objectMapper =new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		Entry entry = new Entry();
		try {
			entry = objectMapper.readValue(req.getReader().lines().collect(Collectors.joining()), Entry.class);
		} catch (IOException e) {
			e.printStackTrace();
			resp.setStatus(500);
			resp.getWriter().println(e);
			return;
		}
		List<Entry> list =  (List<Entry>) Worm.update(Entry.class,entry,matchValues.toArray(), matchKeys.toArray(fields));
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		resp.setHeader("Content-Type", "application/json");
		resp.setStatus(200);
		resp.getWriter().println(mapper.writeValueAsString(list));
	}


}
