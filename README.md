# excel-to-json
Command line utility to convert excel files (all of them) to json. Uses Apache POI (https://poi.apache.org) to convert files. I use this tool within php, since there are only implementations which are (a) too much memory consuming, (b) slow, or (c) don't support all required excel formats. 

## usage
java -jar excel-to-json.jar -s sourcefile [options...]

```
-s,--source <arg>   The source file which should be converted into json.
-?,--help           This help text.
-df,--dateFormat    The template to use for fomatting dates into strings.
-l,--rowLimit <arg>      Limit the max number of rows to read.
-n,--maxSheets <arg>     Limit the max number of sheets to read.
-o,--rowOffset <arg>     Set the offset for begin to read.
-empty              Include rows with no data in it.
-percent            Parse percent values as floats.
-pretty             To render output as pretty formatted json.

```

## output
```
{
  "fileName" : "/var/foo/bar/source.xls",
  "sheets" : [ {
    "name" : "Sheet 1",
    "data" : [ [ "foo", "bar", "baz" ], [ "foo", "bar" ]],
    "maxCols" : 3,
    "maxRows" : 2
  }, {
    "name" : "Sheet 2",
    "data" : [ [ "foo", "bar" ], [ "foo", "bar" ], [ "foo" ]],
    "maxCols" : 1,
    "maxRows" : 3
  } ]
}
```

