RDFXML INGEST
===================================

A CLI tool that reads an XML file source, transforms it to RDF/XML with XSLT, and PUTs the result into a Fedora Repository.

### USAGE
    
    $ gradle installDist
    
    $ cd ./ingester-core/build/install/ingester-core/bin
    
    $ ./ingester-core -r {$filesytem path to XML source file} -x {$filesytem path to transformation xslt} -d {$output dir for transformed file} -b {$base URI of repository}
    
    example:
     
    $ ./ingester-core -r ~/IdeaProjects/rdfxml-ingest/ingester-core/src/main/resources/import-data/metsmods.xml -x ~/IdeaProjects/rdfxml-ingest/ingester-core/src/main/resources/cool.pandora.ingester/oaimets2rdf.xsl -d /tmp/output-data -b http://localhost:8080/fcrepo/rest



The resource identifier is extracted from the XML source and appended onto the repository base URI.

### SPARQL
The intent of this tool is to facilitate mutation of legacy XML datasets with SPARQL.  Here is an example.

```sparql
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
CONSTRUCT {  ?manifest <http://www.loc.gov/METS/FLocat> ?fLocat .
  ?fLocat <http://www.loc.gov/METS/hasPhysDivOrder> ?divOrder .
  ?fLocat <http://www.loc.gov/mods/v3title> ?title .
  ?fLocat rdf:type ?type .
}
WHERE {values ?manifest {<http://localhost:8080/fcrepo/rest/db/id-263566837>} .
  ?manifest <http://www.loc.gov/METS/fileGrp> ?fileGrp .
  ?fileGrp <http://www.loc.gov/METS/file> ?file .
  ?file <http://www.loc.gov/METS/fileID> ?fileID .
  ?file <http://www.loc.gov/METS/FLocat> ?fLocat .
  ?file <http://www.loc.gov/METS/hasPhysDivOrder> ?divOrder .
  ?file <http://www.loc.gov/METS/hasLogDiv> ?logDivId .
  ?logDivId <http://www.loc.gov/mods/v3title> ?title .
  ?logDivId rdf:type ?type .
} 
```
This can produce JSON-LD nodes that look like this:
```json
{
    "@id" : "http://digital.slub-dresden.de/fileadmin/data/263566811/263566811_tif/jpegs/00000001.tif.medium.jpg",
    "@type" : "http://www.loc.gov/METS/structMap#monograph",
    "hasPhysDivOrder" : "1",
    "v3title" : "Der furnembsten, notwendigsten, der gantzen Architectur angehoerigen Mathematischen und Mechanischen kuenst eygentlicher bericht und vast klare, verstendliche unterrichtung"
  }
```  

Also, in conjunction with the fedora-import-export utility, resources can be round-tripped in the BagIt format.

* See [output bag example](https://github.com/ubleipzig/rdfxml-ingest/tree/master/ingester-core/src/main/resources/output-data/example_bag)

For a easy to setup test Fedora API-X implementation, see [APIX-OAI](https://github.com/pan-dora/apix-oai)
