<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="lexan">
  <html>
    <body>
      <table>
	<xsl:apply-templates select="term"/>
      </table>
    </body>
  </html>
</xsl:template>

<xsl:template match="term">
  <tr bgcolor="FFCF00">
    <td align="center">
      <nobr>
	<xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
	<xsl:apply-templates select="location"/>
	<xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
      </nobr>
    </td>
    <td align="right">
      <nobr>
	<xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
	<font style="font-family:arial black">
	  <xsl:value-of select="@token"/>
	</font>
	<xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
      </nobr>
    </td>
    <td align="left">
      <nobr>
	<xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
	<font style="font-family:courier new">
	  <xsl:value-of select="@lexeme"/>
	</font>
	<xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
      </nobr>
    </td>
  </tr>
</xsl:template>

<xsl:template match="location">
  <nobr>
    <font style="font-family:helvetica">
      <xsl:value-of select="@loc"/>
    </font>
  </nobr>
</xsl:template>

</xsl:stylesheet>
