package net.nullpunkt.exceljson.convert;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.commons.cli.CommandLine;

public class ExcelToJsonConverterConfig {

	private String sourceFile;
	private boolean parsePercentAsFloats = false;
	private boolean omitEmpty = false;
	private boolean pretty = false;
	private DateFormat formatDate = null;
	
	public static ExcelToJsonConverterConfig create(CommandLine cmd) {
		ExcelToJsonConverterConfig config = new ExcelToJsonConverterConfig();

		if(cmd.hasOption("s")) {
			config.sourceFile = cmd.getOptionValue("s");
		}

		if(cmd.hasOption("df")) {
			config.formatDate = new SimpleDateFormat(cmd.getOptionValue("df"));
		}
		
		config.parsePercentAsFloats = cmd.hasOption("percent");
		config.omitEmpty = !cmd.hasOption("empty");
		config.pretty = cmd.hasOption("pretty");
		
		return config;
	}
	
	public String valid() {
		if(sourceFile==null) {
			return "Source file may not be empty.";
		}
		File file = new File(sourceFile);
		if(!file.exists()) {
			return "Source file does not exist.";
		}
		if(!file.canRead()) {
			return "Source file is not readable.";
		}
		
		return null;
	}
	
	// GET/SET
	
	public String getSourceFile() {
		return sourceFile;
	}

	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}

	public boolean isParsePercentAsFloats() {
		return parsePercentAsFloats;
	}

	public void setParsePercentAsFloats(boolean parsePercentAsFloats) {
		this.parsePercentAsFloats = parsePercentAsFloats;
	}

	public boolean isOmitEmpty() {
		return omitEmpty;
	}

	public void setOmitEmpty(boolean omitEmpty) {
		this.omitEmpty = omitEmpty;
	}

	public DateFormat getFormatDate() {
		return formatDate;
	}

	public void setFormatDate(DateFormat formatDate) {
		this.formatDate = formatDate;
	}

	public boolean isPretty() {
		return pretty;
	}

	public void setPretty(boolean pretty) {
		this.pretty = pretty;
	}
}
