<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:dv="http://dfg-viewer.de/"
                xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
                xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#" xmlns:dc="http://purl.org/dc/elements/1.1/"
                xmlns:oai="http://www.openarchives.org/OAI/2.0/" xmlns:ow="http://www.ontoweb.org/ontology/1#"
                xmlns:mods="http://www.loc.gov/mods/v3" xmlns:mets="http://www.loc.gov/METS/"
                xmlns:xlink="http://www.w3.org/1999/xlink"
                exclude-result-prefixes="xsl xsi oai dv rdf rdfs dc ow mods xlink">
    <xsl:output indent="yes" method="xml"/>
    <xsl:param name="BASEURI"/>
    <xsl:template match="node() | @*">
        <xsl:copy copy-namespaces="no">
            <xsl:apply-templates select="node() | @*"/>
        </xsl:copy>
    </xsl:template>
    <xsl:template match="mets:fileSec/mets:fileGrp[@USE = 'ORIGINAL']">
        <mets:fileGrp USE="MAX">
            <xsl:apply-templates select="mets:file"/>
        </mets:fileGrp>
    </xsl:template>
    <xsl:template match="mets:file">
        <mets:file ID="{concat(substring-before(@ID,'_ORIGINAL'),'_MAX')}"
                   MIMETYPE="application/vnd.kitodo.iiif">
            <mets:FLocat LOCTYPE="URL"
                         xlink:href="{concat($BASEURI, '/', substring-before(substring-after(./mets:FLocat/@xlink:href, '/'),'.tif'), '.jpx')}"
                         xmlns:xlink="http://www.w3.org/1999/xlink"/>
        </mets:file>
    </xsl:template>
    <xsl:template match="mets:structMap[@TYPE = 'PHYSICAL']/mets:div">
        <mets:div DMDID="{@DMDID}" ID="{@ID}" TYPE="{@TYPE}">
            <xsl:apply-templates select="mets:div"/>
        </mets:div>
    </xsl:template>
    <xsl:template match="mets:structMap[@TYPE = 'PHYSICAL']/mets:div/mets:div">
        <mets:div ID="{@ID}" ORDER="{@ORDER}" ORDERLABEL="{@ORDERLABEL}" TYPE="{@TYPE}">
            <xsl:for-each select="mets:fptr">
                <xsl:if test="contains(./@FILEID, '_ORIGINAL')">
                    <mets:fptr FILEID="{concat(substring-before(./@FILEID,'_ORIGINAL'),'_MAX')}"/>
                </xsl:if>
            </xsl:for-each>
        </mets:div>
    </xsl:template>
    <xsl:template match="mods:title">
        <mods:title>
            <xsl:value-of select="."/>
        </mods:title>
    </xsl:template>
    <xsl:template match="dv:ownerLogo">
        <dv:ownerLogo/>
    </xsl:template>
</xsl:stylesheet>
