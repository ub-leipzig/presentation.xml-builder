<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:dv="http://dfg-viewer.de/" xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#" xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:oai="http://www.openarchives.org/OAI/2.0/" xmlns:ow="http://www.ontoweb.org/ontology/1#" xmlns:mods="http://www.loc.gov/mods/v3" xmlns:mets="http://www.loc.gov/METS/" xmlns:xlink="http://www.w3.org/1999/xlink" exclude-result-prefixes="xsl xsi oai">
    <xsl:output method="xml"/>
    <xsl:template match="/">
        <rdf:RDF>
            <xsl:apply-templates select="oai:OAI-PMH/oai:GetRecord/oai:record"/>
        </rdf:RDF>
    </xsl:template>
    <xsl:template match="oai:record" priority="1">
        <rdf:Description rdf:about="{concat('http://localhost:8080/fcrepo/rest/', tokenize(oai:header/oai:identifier, ':')[5])}">
            <xsl:apply-templates select="oai:metadata/mets:mets/mets:mets/mets:metsHdr"/>
            <xsl:apply-templates select="oai:metadata/mets:mets/mets:mets/mets:dmdSec"/>
            <xsl:apply-templates select="oai:metadata/mets:mets/mets:mets/mets:amdSec"/>
            <xsl:apply-templates select="oai:metadata/mets:mets/mets:mets/mets:fileSec"/>
            <xsl:apply-templates select="oai:metadata/mets:mets/mets:mets/mets:structMap"/>
            <xsl:apply-templates select="oai:metadata/mets:mets/mets:mets/mets:structLink"/>
        </rdf:Description>
    </xsl:template>
    <xsl:template match="mets:metsHdr">
        <mets:metsHdr rdf:parseType="Resource">
            <mets:createDate>
                <xsl:value-of select="./@CREATEDATE"/>
            </mets:createDate>
            <xsl:apply-templates select="mets:agent"/>
        </mets:metsHdr>
    </xsl:template>
    <xsl:template match="mets:name">
        <mets:name>
            <xsl:value-of select="."/>
        </mets:name>
    </xsl:template>
    <xsl:template match="mets:note">
        <mets:note>
            <xsl:value-of select="."/>
        </mets:note>
    </xsl:template>
    <!-- end metsHdr -->
    <!-- begin dmdSec -->
    <xsl:template match="mets:dmdSec">
        <mets:dmdSec rdf:parseType="Resource">
            <mets:DMDLOGID>
                <xsl:value-of select="./@ID"/>
            </mets:DMDLOGID>
            <xsl:apply-templates select="mets:mdWrap[@MDTYPE = 'MODS']/mets:xmlData/mods:mods"/>
        </mets:dmdSec>
    </xsl:template>
    <xsl:template match="mods:identifier"/>
    <xsl:template match="mods:identifier[contains(., 'http://')]">
        <dc:identifier>
            <xsl:value-of select="."/>
        </dc:identifier>
    </xsl:template>
    <xsl:template match="mods:location/mods:physicalLocation">
        <mods:physicalLocation>
            <xsl:value-of select="."/>
        </mods:physicalLocation>
    </xsl:template>
    <xsl:template match="mods:location/mods:shelfLocator">
        <mods:shelfLocator>
            <xsl:value-of select="."/>
        </mods:shelfLocator>
    </xsl:template>
    <xsl:template match="mods:recordInfo/mods:recordIdentifier">
        <mods:recordIdentifier>
            <xsl:value-of select="."/>
        </mods:recordIdentifier>
    </xsl:template>
    <xsl:template match="mods:relatedItem">
        <dc:relation>
            <xsl:value-of select="."/>
        </dc:relation>
    </xsl:template>
    <xsl:template match="mods:subject/mods:geographic">
        <dc:coverage>
            <xsl:value-of select="."/>
        </dc:coverage>
    </xsl:template>
    <xsl:template match="mods:subject/mods:geographic">
        <dc:coverage>
            <xsl:value-of select="."/>
        </dc:coverage>
    </xsl:template>
    <xsl:template match="mods:subject/mods:temporal">
        <dc:coverage>
            <xsl:value-of select="."/>
        </dc:coverage>
    </xsl:template>
    <xsl:template match="mods:name/mods:role"/>
    <xsl:template match="mods:name/mods:namePart">
        <dc:creator>
            <xsl:value-of select="."/>
        </dc:creator>
    </xsl:template>
    <xsl:template match="mods:name/mods:displayForm">
        <mods:displayForm>
            <xsl:value-of select="."/>
        </mods:displayForm>
    </xsl:template>
    <xsl:template match="mods:extension/mods:dateAccessioned">
        <dc:date>
            <xsl:value-of select="."/>
        </dc:date>
    </xsl:template>
    <xsl:template match="mods:extension/mods:dateAvailable"/>
    <xsl:template match="mods:originInfo/mods:dateIssued">
        <dc:date>
            <xsl:value-of select="."/>
        </dc:date>
    </xsl:template>
    <xsl:template match="mods:originInfo/mods:place/mods:placeTerm">
        <dc:publisher>
            <xsl:value-of select="."/>
        </dc:publisher>
    </xsl:template>
    <xsl:template match="mods:abstract">
        <dc:description>
            <xsl:value-of select="."/>
        </dc:description>
    </xsl:template>
    <xsl:template match="mods:note"/>
    <xsl:template match="mods:physicalDescription/mods:internetMediaType">
        <dc:format>
            <xsl:value-of select="."/>
        </dc:format>
    </xsl:template>
    <xsl:template match="mods:physicalDescription/mods:extent">
        <mods:extent>
            <xsl:value-of select="."/>
        </mods:extent>
    </xsl:template>
    <xsl:template match="mods:physicalDescription/mods:digitalOrigin">
        <mods:digitalOrigin>
            <xsl:value-of select="."/>
        </mods:digitalOrigin>
    </xsl:template>
    <xsl:template match="mods:language/mods:languageTerm">
        <dc:language>
            <xsl:value-of select="."/>
        </dc:language>
    </xsl:template>
    <xsl:template match="mods:originInfo/mods:publisher">
        <dc:publisher>
            <xsl:value-of select="."/>
        </dc:publisher>
    </xsl:template>
    <xsl:template match="mods:accessCondition">
        <dc:rights>
            <xsl:value-of select="."/>
        </dc:rights>
    </xsl:template>
    <xsl:template match="mods:classification">
        <dc:subject>
            <xsl:value-of select="."/>
        </dc:subject>
    </xsl:template>
    <xsl:template match="mods:titleInfo/mods:title">
        <dc:title>
            <xsl:value-of select="."/>
        </dc:title>
    </xsl:template>
    <xsl:template match="mods:titleInfo/mods:subTitle">
        <mods:subTitle>
            <xsl:value-of select="."/>
        </mods:subTitle>
    </xsl:template>
    <xsl:template match="mods:genre">
        <dc:type>
            <xsl:value-of select="."/>
        </dc:type>
    </xsl:template>
    <!-- end dmdSec -->
    <xsl:template match="mets:amdSec">
        <dv:rights rdf:parseType="Resource">
            <dv:owner>
                <xsl:value-of select="mets:rightsMD/mets:mdWrap/mets:xmlData/dv:rights/dv:owner"/>
            </dv:owner>
            <dv:ownerLogo>
                <xsl:value-of select="mets:rightsMD/mets:mdWrap/mets:xmlData/dv:rights/dv:ownerLogo"/>
            </dv:ownerLogo>
            <dv:ownerSiteURL>
                <xsl:value-of select="mets:rightsMD/mets:mdWrap/mets:xmlData/dv:rights/dv:ownerSiteURL"/>
            </dv:ownerSiteURL>
            <dv:ownerContact>
                <xsl:value-of select="mets:rightsMD/mets:mdWrap/mets:xmlData/dv:rights/dv:ownerContact"/>
            </dv:ownerContact>
        </dv:rights>
        <dv:links rdf:parseType="Resource">
            <dv:reference rdf:Resource="{mets:digiprovMD/mets:mdWrap/mets:xmlData/dv:links/dv:reference}"/>
            <dv:presentation rdf:Resource="{mets:digiprovMD/mets:mdWrap/mets:xmlData/dv:links/dv:presentation}"/>
        </dv:links>
    </xsl:template>
    <xsl:template match="mets:fileSec">
        <xsl:apply-templates select="mets:fileGrp"/>
    </xsl:template>
    <xsl:template match="mets:fileGrp">
        <mets:fileGrp rdf:parseType="Resource">
            <xsl:apply-templates select="mets:file"/>
        </mets:fileGrp>
    </xsl:template>
    <xsl:template match="mets:file">
        <mets:file rdf:parseType="Resource">
            <mets:fileID>
                <xsl:value-of select="./@ID"/>
            </mets:fileID>
            <mets:FLocat rdf:resource="{mets:FLocat/@xlink:href}"/>
            <mets:mimetype>
                <xsl:value-of select="./@MIMETYPE"/>
            </mets:mimetype>
        </mets:file>
    </xsl:template>
    <xsl:template match="mets:structMap">
        <mets:structMap rdf:parseType="Resource">
            <rdf:type>
                <xsl:value-of select="./@TYPE"/>
            </rdf:type>
            <xsl:apply-templates select="mets:div"/>
        </mets:structMap>
    </xsl:template>
    <xsl:template match="mets:structLink">
        <mets:structLink rdf:parseType="Resource">
            <xsl:apply-templates select="mets:smLink"/>
        </mets:structLink>
    </xsl:template>
    <xsl:template match="mets:div">
        <mets:div rdf:parseType="Resource">
            <mets:DIVID><xsl:value-of select="./@ID"/></mets:DIVID>
            <rdf:type rdf:resource="{concat('http://www.loc.gov/METS/structMap#', ./@TYPE)}"/>
            <xsl:choose>
                <xsl:when test="ancestor::mets:structMap/@TYPE = 'LOGICAL'">
                    <rdfs:label>
                        <xsl:value-of select="./@LABEL"/>
                    </rdfs:label>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:for-each select="mets:fptr">
                        <mets:fptr>
                            <xsl:value-of select="./@FILEID"/>
                        </mets:fptr>
                    </xsl:for-each>
                </xsl:otherwise>
            </xsl:choose>
            <xsl:apply-templates select="mets:div"/>
        </mets:div>
    </xsl:template>
    <xsl:template match="mets:smLink">
        <mets:smLink rdf:parseType="Resource">
            <xlink:to>
                <xsl:value-of select="./@xlink:to"/>
            </xlink:to>
            <xlink:from>
                <xsl:value-of select="./@xlink:from"/>
            </xlink:from>
        </mets:smLink>
    </xsl:template>
        <xsl:template match="mods:extension"/>
</xsl:stylesheet>
