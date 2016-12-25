package com.coldface.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * 类MapUtils.java的实现描述：Map转换工具类
 * 
 * @author coldface
 * @date 2016年3月28日下午2:18:27
 */
public class MapUtils {

	/**
	 * 将map中的value数组转换成对象
	 * 
	 * @param maps
	 * @return
	 */
	public static Map<String, String> toMap(Map<String, String[]> maps) {
		Map<String, String> map = new HashMap<String, String>();
		for (String key : maps.keySet()) {
			String[] values = maps.get(key);
			for (int i = 0; i < values.length; i++) {
				String value = values[i];
				map.put(key, value);
			}
		}
		return map;
	}

	/**
	 * Map<key1_key2,List<Map<String,String;item=>countNum>>>
	 * 
	 * @param map
	 * @return
	 */
	public static Map<String, List<Map<String, String>>> list2Map(List<Map<String, Object>> list, String cepr,
			Set<String> keySet) {
		Map<String, List<Map<String, String>>> returnMap = Maps.newHashMap();
		if (list == null) {
			return returnMap;
		}
		for (Map<String, Object> map : list) {
			Map<String, String> keyMap = replaceCount(map, cepr);
			String key = keyMap.get(Constants.REPORT_KEY);
			keySet.add(key);
			List<Map<String, String>> valList = returnMap.get(key);
			if (CollectionUtils.isEmpty(valList)) { // 如果之前没有,就把该key添加进去
				valList = Lists.newArrayList();
				valList.add(keyMap);
				returnMap.put(key, valList);
			} else { // 如果之前已经有了,就把值加到里面
				valList.add(keyMap);
			}
		}
		return returnMap;
	}

	public static Map<String, String> replaceCount(Map<String, Object> map, String cepr) {
		Map<String, String> cloneMap = Maps.newHashMap();
		String returnKey = "";
		for (String key : map.keySet()) {
			String value = String.valueOf(map.get(key));
			if ("count".equals(key)) {
				cloneMap.put(cepr, value);
			} else {
				returnKey += "_" + value;
				cloneMap.put(key, value);
			}
		}
		cloneMap.put(Constants.REPORT_KEY, returnKey);
		return cloneMap;
	}

	public static void main(String[] args) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("cch", "100");
		map.put("abc", "200");
		map.put("count", "555");
		Map<String, Object> mapA = Maps.newHashMap();
		mapA.put("cch", "300");
		mapA.put("abc", "400");
		mapA.put("count", "666");
		List<Map<String, Object>> ls = Lists.newArrayList();
		ls.add(map);
		ls.add(mapA);

		Map<String, Object> mapB = Maps.newHashMap();
		mapB.put("cch", "100");
		mapB.put("abc", "200");
		mapB.put("count", "800");
		Map<String, Object> mapB2 = Maps.newHashMap();
		mapB2.put("cch", "300");
		mapB2.put("abc", "400");
		mapB2.put("count", "900");
		Map<String, Object> mapB3 = Maps.newHashMap();
		mapB3.put("cch", "500");
		mapB3.put("abc", "600");
		mapB3.put("count", "900");
		List<Map<String, Object>> lsB = Lists.newArrayList();
		lsB.add(mapB);
		lsB.add(mapB2);
		lsB.add(mapB3);
		Map<String, List<Map<String, Object>>> map2List = Maps.newHashMap();
		map2List.put("i_report_call_sum", ls);
		map2List.put("i_report_call_count", lsB);
		Set<String> keySet = Sets.newHashSet();
		List<Map<String, List<Map<String, String>>>> list = Lists.newArrayList();
		for (String key : map2List.keySet()) {
			Map<String, List<Map<String, String>>> mapList = list2Map(map2List.get(key), key, keySet);
			list.add(mapList);
		}
		List<Map<String, String>> finalList = Lists.newArrayList();
		for (String key : keySet) {
			Map<String, String> finalMap = Maps.newHashMap();
			for (Map<String, List<Map<String, String>>> map1 : list) {
				List<Map<String, String>> list1 = map1.get(key);
				if (!CollectionUtils.isEmpty(list1)) {
					for (Map<String, String> map2 : list1) {
						finalMap.putAll(map2);
					}
				}
			}
			finalList.add(finalMap);
		}
		System.out.println("final=" + finalList);
	}

	public static Map<String, String> mergeMapByKey(List<Map<String, List<Map<String, String>>>> tempList, String key) {
		Map<String, String> finalMap = Maps.newHashMap();
		for (Map<String, List<Map<String, String>>> map1 : tempList) {
			List<Map<String, String>> list1 = map1.get(key);
			if (!CollectionUtils.isEmpty(list1)) {
				for (Map<String, String> map2 : list1) {
					finalMap.putAll(map2);
				}
			}
		}
		return finalMap;
	}

}
