package sk.funix.userstory.config.util;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
 
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author http://community.jboss.org/wiki/AnnotationdrivenequalsandhashCode
 * @author Nicolas Milliard
 * @since 2 Add final field and comments
 */
public final class BeanUtils {

	private static Map<String, List<AccessibleObject>> cache = new Hashtable<String, List<AccessibleObject>>();

	private BeanUtils() {
	}

	/**
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static boolean equals(final Object obj1, final Object obj2) {
		if (obj1 == obj2) {
			return true;
		}

		if (obj2 == null || obj2.getClass() != obj1.getClass()) {
			return false;
		}

		EqualsBuilder builder = new EqualsBuilder();

		for (AccessibleObject ao : getAccessibleObjects(obj1, 1)) {
			try {
				if (ao instanceof Field) {
					builder.append(((Field) ao).get(obj1),
							((Field) ao).get(obj2));
				} else {
					builder.append(((java.lang.reflect.Method) ao).invoke(obj1,
							(Object[]) null), ((java.lang.reflect.Method) ao)
							.invoke(obj2, (Object[]) null));
				}
			} catch (Exception e) {
			}
		}

		return builder.isEquals();
	}

	/**
	 * @param obj
	 * @return
	 */
	public static int hashCode(final Object obj) {
		HashCodeBuilder builder = new HashCodeBuilder();

		for (AccessibleObject ao : getAccessibleObjects(obj, 2)) {
			try {
				if (ao instanceof Field) {
					builder.append(((Field) ao).get(obj));
				} else {
					builder.append(((java.lang.reflect.Method) ao).invoke(obj,
							(Object[]) null));
				}
			} catch (Exception e) {
			}
		}

		return builder.toHashCode();
	}

	/**
	 * @param obj
	 * @return
	 */
	public static String toString(final Object obj) {
		ToStringBuilder builder = new ToStringBuilder(obj,
				ToStringStyle.SHORT_PREFIX_STYLE);

		for (AccessibleObject ao : getAccessibleObjects(obj, 4)) {
			try {
				if (ao instanceof Field) {
					builder.append(((Field) ao).getName(), ((Field) ao)
							.get(obj));
				} else {
					builder.append(((java.lang.reflect.Method) ao).getName(),
							((java.lang.reflect.Method) ao).invoke(obj,
									(Object[]) null));
				}
			} catch (Exception e) {
			}
		}

		return builder.toString();
	}

	private static List<AccessibleObject> getAccessibleObjects(final Object obj,
			final int filter) {
		Class<?> clazz = obj.getClass();

		String name = clazz.getName() + filter;

		if (!cache.containsKey(name)) {
			List<AccessibleObject> aos = new ArrayList<AccessibleObject>();

			do {
				Field[] fields = clazz.getDeclaredFields();

				for (Field field : fields) {
					BusinessKey bk = field.getAnnotation(BusinessKey.class);
					if (bk != null && (filter(bk) & filter) == filter) {
						field.setAccessible(true);
						aos.add(field);
					}
				}

				java.lang.reflect.Method[] methods = clazz.getDeclaredMethods();

				for (java.lang.reflect.Method method : methods) {
					BusinessKey bk = method.getAnnotation(BusinessKey.class);
					if (bk != null && (filter(bk) & filter) == filter) {
						method.setAccessible(true);
						aos.add(method);
					}
				}

				clazz = clazz.getSuperclass();
			} while (clazz != null);

			Collections.sort(aos, new AccessibleObjectComparator());

			cache.put(name, aos);
		}

		return cache.get(name);
	}

	private static int filter(final BusinessKey bk) {
		int filter = 0;

		for (Method method : bk.include()) {
			switch (method) {
			case ALL:
				filter = filter | 7;
				break;
			case EQUALS:
				filter = filter | 1;
				break;
			case HASH_CODE:
				filter = filter | 2;
				break;
			case TO_STRING:
				filter = filter | 4;
				break;
			}
		}

		for (Method method : bk.exclude()) {
			switch (method) {
			case ALL:
				filter -= filter & 7;
				break;
			case EQUALS:
				filter -= filter & 1;
				break;
			case HASH_CODE:
				filter -= filter & 2;
				break;
			case TO_STRING:
				filter -= filter & 4;
				break;
			}
		}

		return filter;
	}

	private static class AccessibleObjectComparator implements
			Comparator<AccessibleObject> {
		public int compare(AccessibleObject o1, AccessibleObject o2) {
			boolean o1IsField = o1 instanceof Field;
			boolean o2IsField = o2 instanceof Field;

			if (!o1IsField && o2IsField) {
				return 1;
			} else if (o1IsField && !o2IsField) {
				return -1;
			}

			if (o1IsField) {
				return ((Field) o1).getName().compareTo(((Field) o2).getName());
			} else {
				return ((java.lang.reflect.Method) o1).getName().compareTo(
						((java.lang.reflect.Method) o2).getName());
			}
		}
	}
}
