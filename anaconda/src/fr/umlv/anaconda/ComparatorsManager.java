package fr.umlv.anaconda;

import java.io.*;
import java.util.*;

public class ComparatorsManager {
	public static Comparator sortedByName = new Comparator() {
		public int compare(Object o1, Object o2) {
			if(o1 == null) return -1;
			if(o2 == null) return 1;
			File file1 = (File)o1;
			File file2 = (File)o2;
			if((file1.isDirectory() && file2.isDirectory()) || (!file1.isDirectory() && !file2.isDirectory()))
				return file1.getAbsolutePath().compareTo(file2.getAbsolutePath());
			if(file1.isDirectory()) return -1;
			return 1;
		}
	};
	public static Comparator sortedByType = new Comparator() {
		public int compare(Object o1, Object o2) {
			if(o1 == null) return -1;
			if(o2 == null) return 1;
			File file1 = (File)o1;
			File file2 = (File)o2;
			if(file1.isDirectory() && file2.isDirectory())
				return file1.getAbsolutePath().compareTo(file2.getAbsolutePath());
			if(!file1.isDirectory() && !file2.isDirectory()) {
				String name1 = file1.getAbsolutePath();
				String name2 = file2.getAbsolutePath();
				String extension1 = name1.substring(name1.lastIndexOf('.') + 1);
				String extension2 = name2.substring(name2.lastIndexOf('.') + 1);
				int cmp = extension1.compareTo(extension2);
				return (cmp == 0)? name1.compareTo(name2): cmp;
			}
			if(file1.isDirectory()) return -1;
			return 1;
		}
	};
	public static Comparator sortedBySize = new Comparator() {
		public int compare(Object o1, Object o2) {
			if(o1 == null) return -1;
			if(o2 == null) return 1;
			File file1 = (File)o1;
			File file2 = (File)o2;
			return (int)(file1.length() - file2.length());
		}
	};
	public static Comparator sortedByDate = new Comparator() {
		public int compare(Object o1, Object o2) {
			if(o1 == null) return -1;
			if(o2 == null) return 1;
			File file1 = (File)o1;
			File file2 = (File)o2;
			return (int)(file1.lastModified() - file2.lastModified());
		}
	};
	/****************************************************/
	/* pour trier par repertoire puis fichier ensuite le nom */
	final public static Comparator cmp = new Comparator() {
		public int compare(Object o1, Object o2) {
			if (o1 == null)
				return -1;
			if (o2 == null)
				return 1;
			File file1 = (File) o1;
			File file2 = (File) o2;
			if ((file1.isDirectory() && file2.isDirectory())
				|| (!file1.isDirectory() && !file2.isDirectory()))
				return file1.getAbsolutePath().compareTo(
					file2.getAbsolutePath());
			if (file1.isDirectory())
				return -1;
			return 1;
		}
		public boolean equals(Object obj) {
			return this == obj;
		}
	};
	/* pour trier par taille */
	final public static Comparator cmp_by_size = new Comparator() {
		public int compare(Object o1, Object o2) {
			if (o1 == null)
				return -1;
			if (o2 == null)
				return 1;
			File file1 = (File) o1;
			File file2 = (File) o2;

			return (int) ((file1.length() - file2.length()));
		}
		public boolean equals(Object obj) {
			return this == obj;
		}
	};

	final public static Comparator cmp_by_date = new Comparator() {
		public int compare(Object o1, Object o2) {
			if (o1 == null)
				return -1;
			if (o2 == null)
				return 1;
			File file1 = (File) o1;
			File file2 = (File) o2;
			Date date1 = new Date(file1.lastModified());
			Date date2 = new Date(file2.lastModified());
			return date1.compareTo(date2);
		}
		public boolean equals(Object obj) {
			return this == obj;
		}
	};

	final public static Comparator cmp_by_type = new Comparator() {
		public int compare(Object o1, Object o2) {
			if (o1 == null)
				return -1;
			if (o2 == null)
				return 1;
			File file1 = (File) o1;
			File file2 = (File) o2;
			String s1 = file1.getName();
			String s2 = file2.getName();
			int extIndex1 = s1.lastIndexOf('.');
			int extIndex2 = s2.lastIndexOf('.');

			if (extIndex1 != -1)
				s1 = s1.substring(extIndex1);
			if (extIndex2 != -1)
				s2 = s2.substring(extIndex2);
			return s1.compareTo(s2);
		}
		public boolean equals(Object obj) {
			return this == obj;
		}
	};

	private static ArrayList comp = new ArrayList();
	public static void addCmp(String s) {
		if (s.equalsIgnoreCase("by_name")) {
			if (comp.contains(cmp))
				comp.remove(cmp);
			else
				comp.add(cmp);
		} else if (s.equalsIgnoreCase("by_size")) {
			if (comp.contains(cmp_by_size))
				comp.remove(cmp_by_size);
			else
				comp.add(cmp_by_size);
		} else if (s.equalsIgnoreCase("by_date")) {
			if (comp.contains(cmp_by_date))
				comp.remove(cmp_by_date);
			else
				comp.add(cmp_by_date);
		} else if (s.equalsIgnoreCase("by_type")) {
			if (comp.contains(cmp_by_type))
				comp.remove(cmp_by_type);
			else
				comp.add(cmp_by_type);
		}
	}
}
