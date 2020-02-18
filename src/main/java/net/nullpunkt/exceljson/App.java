package net.nullpunkt.exceljson;

import net.nullpunkt.exceljson.convert.ExcelToJsonConverter;
import net.nullpunkt.exceljson.convert.ExcelToJsonConverterConfig;
import net.nullpunkt.exceljson.pojo.ExcelWorkbook;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App {
	
	public static void main( String[] args ) throws Exception
    {
		Options options = new Options();
		options.addOption("s", "source", true, "The source file which should be converted into json.");
		options.addOption("df", "dateFormat", true, "The template to use for formatting dates into strings.");
		options.addOption("?", "help", true, "This help text.");
		options.addOption("n", "maxSheets", true, "Limit the max number of sheets to read.");
		options.addOption("l", "rowLimit", true, "Limit the max number of rows to read.");
		options.addOption("o", "rowOffset", true, "Set the offset for begin to read.");
		options.addOption("d", "destination", true, "The destination directory where the output.json should be created.");
		options.addOption(new Option("percent", "Parse percent values as floats."));
		options.addOption(new Option("empty", "Include rows with no data in it."));
		options.addOption(new Option("pretty", "To render output as pretty formatted json."));
		options.addOption(new Option("fillColumns", "To fill rows with null values until they all have the same size."));
		
		CommandLineParser parser = new BasicParser();
		CommandLine cmd = null;
		try {
			cmd = parser.parse(options, args);
		} catch(ParseException e) {
			help(options);
			return; 
		}
		
		if(cmd.hasOption("?")) {
			help(options);
			return;
		}
		
		ExcelToJsonConverterConfig config = ExcelToJsonConverterConfig.create(cmd);
		String valid = config.valid();
		if(valid!=null) {
			System.out.println(valid);
			help(options);
			return;
		}
		
		ExcelWorkbook book = ExcelToJsonConverter.convert(config);
		String json = book.toJson(config.isPretty(), config.isWriteToFile());
		if(config.isWriteToFile()) {
			writeToFile(json, config.getDestinationFile());
		} else {
			System.out.println(json);
		}
    }
	
	private static void help(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("java -jar excel-to-json.jar", options);
	}

	private static void writeToFile(String json, String filename) throws IOException {
		FileOutputStream outputStream = new FileOutputStream(filename);
		byte[] strToBytes = json.getBytes();
		outputStream.write(strToBytes);

		outputStream.close();
	}
}
