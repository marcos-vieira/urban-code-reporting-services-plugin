package com.urbancode.filepattern

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegularExpressionFileFilter implements FileFilter {
	
	private Pattern pattern;
	
	public RegularExpressionFileFilter(String patternStr) {
		pattern = Pattern.compile(patternStr);
	}
	public boolean accept(File file) {
		// if it is a directory, return true (it is a way to look for files in a recursive way)
		if(file.isDirectory())
			return true;
		
		String absolutePath = file.getAbsolutePath();
		Matcher matcher = pattern.matcher(absolutePath);
		return matcher.find();				
		
	}
}