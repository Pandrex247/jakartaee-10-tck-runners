# jakartaee-10-tck-runner Summarizer

### Description

The Summarizer takes the output files from the various test suites and summarize them in a simpler file.

The output looks like this: 

>\#\#\# <test suite name\>
>
>Completed running 1713 tests
>
>Number of tests failed 9
>
>Number of tests with errors 2
>
>Number of tests skipped 1


### Summarizer Executions

Run maven test from summarizer
    
    cd summarizer
    mvn exec:java -Dexec.arguments=[<tck report file>,<format:jUnitReport|summaryTxt|testSet|failsafeSummary>,[testSuiteName],[<output report path>]]


The command takes up to 4 arguments:
* tck report file: path and filename for the file to summarize. Note: the path needs to be written from the current directory. Glob syntax is supported, but only the format testSet can handle multiple files
* format: 4 formats are supported. See the examples in src/test/sample to see what is supported.
* test suite name: optional. If missing, the default test suite name is "unknown suite"
* output report path: optional. Path where to create the summary. If missing, the default output is in ./summarizer/results

Example:
>    mvn exec:java -Dexec.arguments=[**/*junit-report.xml,jUnitReport,testSuiteName]