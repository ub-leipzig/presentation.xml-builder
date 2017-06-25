RDFXML INGEST
===================================

A CLI tool that reads an XML file source, transforms it to RDF/XML with XSLT, and PUTs the result into a Fedora Repository.

### USAGE
    
    $ gradle installDist
    
    $ cd ./ingester-core/build/install/ingester-core/bin
    
    $ ./ingester-core -r {$filesytem path to mets source file} -x {$filesytem path to transformation xslt} -d {$output dir for transformed file}
    
    example:
     
    $ ./ingester-core/build/install/ingester-core/bin/ingester-core -r ~/IdeaProjects/rdfxml-ingest/ingester-core/src/main/resources/import-data/metsmods.xml -x ~/IdeaProjects/rdfxml-ingest/ingester-core/src/main/resources/cool.pandora.ingester/oaimets2rdf.xsl -d /tmp/output-data


Note that the default repository URI (http://localhost:8080/fcrepo/rest) is hard-coded into the XSLT example.
The resource identifier is extracted from the XML source and appended onto the repository base URI.

### SPARQL
The intent of this tool is to facilitate mutation of legacy XML datasets with SPARQL.  Here is an example.

```sparql
CONSTRUCT {  ?manifest <http://www.loc.gov/METS/FLocat> ?fLocat .
  ?fLocat <http://www.loc.gov/METS/hasDivOrder> ?divOrder .
  ?fLocat <http://www.loc.gov/METS/hasLogDivTitle> ?title .  
}
WHERE {values ?manifest {<http://localhost:8080/fcrepo/rest/id-263566823>} .
  ?manifest <http://www.loc.gov/METS/fileGrp> ?fileGrp .
  ?fileGrp <http://www.loc.gov/METS/file> ?file .
  ?file <http://www.loc.gov/METS/fileID> ?fileID .
  ?file <http://www.loc.gov/METS/FLocat> ?fLocat .
  ?file <http://www.loc.gov/METS/hasDivOrder> ?divOrder .
  ?file <http://www.loc.gov/METS/hasLogDiv> ?logDivId .
  ?logDivId <http://www.loc.gov/mods/v3title> ?title .
} 
```
Also, in conjunction with the fedora-import-export utility, resources can be round-tripped in the BagIt format.

* See [output bag example](https://github.com/pan-dora/rdfxml-ingest/tree/master/ingester-core/src/main/resources/output-data/example_bag)

For a easy to setup test Fedora API-X implementation, see [APIX-OAI](https://github.com/pan-dora/apix-oai)
