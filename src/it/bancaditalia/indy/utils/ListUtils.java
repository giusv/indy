package it.bancaditalia.indy.utils;

import java.util.List;

public class ListUtils {

	public static <T> T car(List<T> list) {
		return list.get(0);
	}

	public static <T> List<T> cdr(List<T> list) {
		return list.subList(1, list.size());
	}

	public static <T> List<T> cons(T elem, List<T> list) {
		list.add(0,elem);
		return list;
	}
}
