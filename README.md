# excel-to-json
Command line utility to convert excel files (all of them) to json. Uses Apache POI (https://poi.apache.org) to convert files. I use this tool within php, since there are only implementation which are (a) too much memory consuming and/or (b) slow. Or don't support all required excel formats. 

# usage
usage: java -jar excel-to-json.jar

 -?,--help           This help text.
 
 -df,--dateFormat    The template to use for fomatting dates into strings.
 
 -empty              Include rows with no data in it.
 
 -percent            Parse percent values as floats.
 
 -pretty             To render output as pretty formatted json.
 
 -s,--source <arg>   The source file which should be converted into json.
                          

