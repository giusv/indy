package it.bancaditalia.indy.utils;

import java.util.Map;

public class MapUtils {

	public static <K,V> Map<K,V> cons(K key, V value, Map<K,V> map) {
		map.put(key,value);
		return map;
	}
}
