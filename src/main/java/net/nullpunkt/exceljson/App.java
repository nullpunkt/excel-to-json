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

/**
 * Hello world!
 *
 */
public class App {
	
	public static void main( String[] args ) throws Exception
    {
		Options options = new Options();
		options.addOption("s", "source", true, "The source file which should be converted into json.");
		options.addOption("df", "dateFormat", true, "The template to use for fomatting dates into strings.");
		options.addOption("?", "help", true, "This help text.");
		options.addOption(new Option("percent", "Parse percent values as floats."));
		options.addOption(new Option("empty", "Include rows with no data in it."));
		options.addOption(new Option("pretty", "To render output as pretty formatted json."));
		
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
		
//		if(config.)
		
		String json = book.toJson(config.isPretty());
		System.out.println(json);
    }
	
	private static void help(Options options) {
		HelpFormatter formater = new HelpFormatter();
		formater.printHelp("java -jar excel-to-json.jar", options);
	}
}
