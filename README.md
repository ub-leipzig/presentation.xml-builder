RDFXML INGEST
===================================

A CLI tool that reads an XML file source, transforms it to RDF/XML with XSLT, and PUTs the result into a Fedora Repository.

### USAGE
    
    $ gradle installDist
    
    $ cd ./ingester-core/build/install/ingester-core/bin
    
    $ ./ingester-core -r {$filesytem path to mets source file} -x {$filesytem path to transformation xslt} -d {$output dir for transformed file}
    
    example:
     
    ./ingester-core -r ~/IdeaProjects/rdfxml-ingest/ingester-core/src/main/resources/import-data/metsmods.xml -x ~/IdeaProjects/rdfxml-ingest/ingester-core/src/main/resources/import-data/oaimets2rdf.xsl -d ~/IdeaProjects/rdfxml-ingest/ingester-core/src/main/resources/data

Note that the default repository URI (http://localhost:8080/fcrepo/rest) is hard-coded into the XSLT example.

### SPARQL
The intent of this tool is to facilitate mutation of legacy XML datasets with SPARQL.  Here is an example.

```sparql
SELECT ?manifest ?fileID ?fLocat
WHERE {values ?manifest {<http://localhost:8080/fcrepo/rest/id-263566812>} .
  ?manifest <http://www.loc.gov/METS/fileGrp> ?fileGrp .
  ?fileGrp <http://www.loc.gov/METS/file> ?file .
  ?file <http://www.loc.gov/METS/fileID> ?fileID .
  ?file <http://www.loc.gov/METS/FLocat> ?fLocat .
} ORDER BY ASC(?fileID)
```
For a Fedora API-X implementation, see [APIX-OAI](https://github.com/pan-dora/apix-oai)
