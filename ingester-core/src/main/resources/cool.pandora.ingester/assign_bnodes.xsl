<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:dv="http://dfg-viewer.de/" xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#" xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:oai="http://www.openarchives.org/OAI/2.0/" xmlns:ow="http://www.ontoweb.org/ontology/1#" xmlns:mods="http://www.loc.gov/mods/v3" xmlns:mets="http://www.loc.gov/METS/" xmlns:xlink="http://www.w3.org/1999/xlink" exclude-result-prefixes="xsl xsi oai">
    <xsl:output method="xml"/>
    <xsl:template match="node() | @*">
        <xsl:copy>
            <xsl:apply-templates select="node() | @*"/>
        </xsl:copy>
    </xsl:template>
    <xsl:template match="mets:hasPhysDiv">
        <xsl:variable name="id">
            <xsl:value-of select="."/>
        </xsl:variable>
        <mets:hasPhysDiv rdf:nodeID="{/rdf:RDF/rdf:Description[descendant::mets:DIVID = $id]/@rdf:nodeID}"> </mets:hasPhysDiv>
    </xsl:template>
    <xsl:template match="mets:hasLogDiv">
        <xsl:variable name="id">
            <xsl:value-of select="."/>
        </xsl:variable>
        <mets:hasLogDiv rdf:nodeID="{/rdf:RDF/rdf:Description[descendant::mets:DIVID = $id]/@rdf:nodeID}"> </mets:hasLogDiv>
    </xsl:template>
    <xsl:template match="mets:hasDMDSec">
        <xsl:variable name="id">
            <xsl:value-of select="."/>
        </xsl:variable>
        <mets:hasDMDSec rdf:nodeID="{/rdf:RDF/rdf:Description[descendant::mets:DMDLOGID = $id]/@rdf:nodeID}"> </mets:hasDMDSec>
    </xsl:template>
</xsl:stylesheet>
