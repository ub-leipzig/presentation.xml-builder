Presentation.xml Builder
===================================

A CLI tool that reads a XML file source, transforms it with XSLT, and PUTs the result in an output directory.

### USAGE
    
    $ gradle installDist
    
    $ cd ./ingester-core/build/install/ingester-core/bin
    
    $ ./ingester-core -r {$filesytem path to XML source file} -x {$filesytem path to transformation xslt} -d {$output dir for transformed file} -b {$base URI of a manifest image service}
    
    example:
     
    $ ./ingester-core -r ~/IdeaProjects/presentation.xml-builder/ingester-core/src/main/resources/import-data/mets2IIIF.xml -x ~/IdeaProjects/presentation.xml-builder/ingester-core/src/main/resources/cool.pandora.ingester/oaimets2rdf.xsl -d /tmp/output-data -b https://iiif.ub.uni-leipzig.de/fcgi-bin/iipsrv.fcgi?iiif=/j2k/0000/0021/0000002170


### METS-MODS
The main purpose of this tool is to transform METS-MODS resource URIs to point to an IIIF image service.

Example:
Original METS-MODS node:
```xml
            <mets:file ID="FILE_0024_ORIGINAL" MIMETYPE="image/tiff">
                 <mets:FLocat LOCTYPE="URL" xlink:href="SundDie_010245146_tif/00000024.tif"
                     xmlns:xlink="http://www.w3.org/1999/xlink"/>
             </mets:file>
```
Transformed METS-MODS node:
```xml
        <mets:file ID="FILE_0024_MAX" MIMETYPE="application/vnd.kitodo.iiif">
            <mets:FLocat xmlns:xlink="http://www.w3.org/1999/xlink"
                         LOCTYPE="URL"
                         xlink:href="https://iiif.ub.uni-leipzig.de/fcgi-bin/iipsrv.fcgi?iiif=/j2k/0000/0021/0000002170/00000024.jpx"/>
         </mets:file>
```

The resource identifier ("00000024") is appended onto the base URI ("https://iiif.ub.uni-leipzig.de/fcgi-bin/iipsrv.fcgi?iiif=/j2k/0000/0021/0000002170/") which is set with the `-b` parameter.