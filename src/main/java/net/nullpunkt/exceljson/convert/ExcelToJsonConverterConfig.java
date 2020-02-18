package net.nullpunkt.exceljson.convert;

import java.io.File;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.commons.cli.CommandLine;

public class ExcelToJsonConverterConfig {

	private String sourceFile;
	private boolean parsePercentAsFloats = false;
	private boolean omitEmpty = false;
	private boolean pretty = false;
	private boolean fillColumns = false;
	private int numberOfSheets = 0;
	private int rowLimit = 0; // 0 -> no limit
	private int rowOffset = 0;
	private DateFormat formatDate = null;
	private String destinationFile;
	private boolean writeToFile = false;


	public static ExcelToJsonConverterConfig create(CommandLine cmd) {
		ExcelToJsonConverterConfig config = new ExcelToJsonConverterConfig();

		if(cmd.hasOption("s")) {
			config.sourceFile = cmd.getOptionValue("s");
		}

		if(cmd.hasOption("df")) {
			config.formatDate = new SimpleDateFormat(cmd.getOptionValue("df"));
		}

		if (cmd.hasOption("n")) {
			config.setNumberOfSheets(Integer.parseInt(cmd.getOptionValue("n")));
		}

		if (cmd.hasOption("l")) {
			config.setRowLimit(Integer.parseInt(cmd.getOptionValue("l")));
		}

		if (cmd.hasOption("o")) {
			config.setRowOffset(Integer.parseInt(cmd.getOptionValue("o")));
		}

		if(cmd.hasOption("d")) {
			config.setDestinationFile(cmd.getOptionValue("d"));
			config.setWriteToFile(true);
		}
		
		config.parsePercentAsFloats = cmd.hasOption("percent");
		config.omitEmpty = !cmd.hasOption("empty");
		config.pretty = cmd.hasOption("pretty");
		config.fillColumns = cmd.hasOption("fillColumns");

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
		if(destinationFile != null) {
			file = new File(destinationFile);
			if(!file.isDirectory()) {
				return "Destination path is not a valid directory.";
			}
			this.setDestinationFile(Paths.get(this.getDestinationFile(),"output.json").toString());
		}
		return null;
	}
	
	// GET/SET

	public int getRowLimit() {
		return rowLimit;
	}

	public void setRowLimit(int rowLimit) {
		this.rowLimit = rowLimit;
	}

	public int getRowOffset() {
		return rowOffset;
	}

	public void setRowOffset(int rowOffset) {
		this.rowOffset = rowOffset;
	}

	public int getNumberOfSheets()
	{
		return numberOfSheets;
	}

	public void setNumberOfSheets(int numberOfSheets)
	{
		this.numberOfSheets = numberOfSheets;
	}

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

	public boolean isFillColumns() {
		return fillColumns;
	}

	public void setFillColumns(boolean fillColumns) {
		this.fillColumns = fillColumns;
	}

	public String getDestinationFile() {
		return destinationFile;
	}

	public void setDestinationFile(String destinationFile) {
		this.destinationFile = destinationFile;
	}

	public boolean isWriteToFile() {
		return writeToFile;
	}

	public void setWriteToFile(boolean writeToFile) {
		this.writeToFile = writeToFile;
	}
}
