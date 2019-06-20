<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="imcgen">
  <html>
    <style>
      table, tr, td {
      text-align: center;
      vertical-align: top;
      }
    </style>
    <body>
      <table>
	<xsl:apply-templates select="node"/>
      </table>
    </body>
  </html>
</xsl:template>

<xsl:template match="node">
  <td>
    <table width="100%">
      <tr bgcolor="FFEE00">
	<td colspan="1000">
	  <nobr>
	    <xsl:text>&#xA0;</xsl:text>
	    <font style="font-family:arial black">
	      <xsl:value-of select="@label"/>
	    </font>
	    <xsl:if test="@spec!=''">
	      <xsl:text>&#xA0;</xsl:text>
	      <font style="font-family:helvetica">
		<xsl:value-of select="@spec"/>
	      </font>
	    </xsl:if>
	    <xsl:text>&#xA0;</xsl:text>
	  </nobr>
	  <br/>
	  <nobr>
	    <xsl:text>&#xA0;</xsl:text>
	    <xsl:apply-templates select="location"/>
	    <xsl:text>&#xA0;</xsl:text>
	  </nobr>
	  <xsl:if test="@lexeme!=''">
	    <br/>
	    <nobr>
	      <xsl:text>&#xA0;</xsl:text>
	      <font style="font-family:courier new">
		<xsl:value-of select="@lexeme"/>
	      </font>
	      <xsl:text>&#xA0;</xsl:text>
	    </nobr>
	  </xsl:if>
	  <br/>
	  <table width="100%">
	    <xsl:apply-templates select="declAt"/>
	    <xsl:apply-templates select="lvalue"/>
	    <tr>
	      <xsl:apply-templates select="type"/>
	    </tr>
	  </table>
	  <xsl:apply-templates select="frame"/>
	  <xsl:apply-templates select="access"/>
	  <table width="100%">
	    <xsl:apply-templates select="imc"/>
	  </table>
	</td>
      </tr>
      <tr>
	<xsl:apply-templates select="node"/>
      </tr>
    </table>
  </td>
</xsl:template>

<xsl:template match="declAt">
  <tr bgcolor="FFCF00">
    <td>
      <nobr>
	<xsl:text>&#xA0;</xsl:text>
	[<xsl:value-of select="@location"/>]
	<xsl:text>&#xA0;</xsl:text>
      </nobr>
    </td>
  </tr>	
</xsl:template>

<xsl:template match="lvalue">
  <tr bgcolor="FFCF00">
    <td>
      <nobr>
	<xsl:text>&#xA0;</xsl:text>
	LVALUE
	<xsl:text>&#xA0;</xsl:text>
      </nobr>
    </td>
  </tr>
</xsl:template>

<xsl:template match="type">
  <td>
    <table width="100%" border="1" rules="all">
      <tr>
	<td bgcolor="FFCF00" colspan="10000000">
	  <nobr>
	    <xsl:text>&#xA0;</xsl:text>
	    <xsl:value-of select="@label"/>
	    <xsl:if test="@name!=''">
	      <xsl:text>&#xA0;</xsl:text>
	      <xsl:value-of select="@name"/>
	    </xsl:if>
	    <xsl:text>&#xA0;</xsl:text>
	  </nobr>
	  <xsl:if test="@loc!=''">
	    <br/>
	    <xsl:value-of select="@loc"/>
	  </xsl:if>
	</td>
      </tr>
      <tr>
	<xsl:apply-templates select="type"/>
      </tr>
    </table>
  </td>
</xsl:template>

<xsl:template match="frame">
  <table width="100%">
    <tr bgcolor="EECF00">
      <td>
	<nobr>
	  FRAME
	  label=<font style="font-family:courier new"><xsl:value-of select="@label"/></font>
	  depth=<xsl:value-of select="@depth"/> 
	  size=<xsl:value-of select="@size"/> 
	  locs=<xsl:value-of select="@locssize"/>
	  args=<xsl:value-of select="@argssize"/>
	</nobr>
      </td>
    </tr>
  </table>
</xsl:template>

<xsl:template match="access">
  <table width="100%" bgcolor="EECF00">
    <tr>
      <td>
	ACCESS
      </td>
    </tr>
    <tr>
      <td>
	size=<xsl:value-of select="@size"/> 
      </td>
    </tr>
    <xsl:if test="@label!=''">
      <tr>
	<td>
	  label=<font style="font-family:courier new"><xsl:value-of select="@label"/></font>
	</td>
      </tr>
    </xsl:if>
    <xsl:if test="@offset!=''">
      <tr>
	<td>
	  offset=<xsl:value-of select="@offset"/>
	</td>
      </tr>
    </xsl:if>
    <xsl:if test="@depth!=''">
      <tr>
	<td>
	  depth=<xsl:value-of select="@depth"/>
	</td>
      </tr>
    </xsl:if>
  </table>
</xsl:template>

<xsl:template match="imc">
  <td>
    <table width="100%">
      <tr bgcolor="00BBFF">
	<td colspan="1000">
	  <nobr>
	    <xsl:value-of select="@name"/>
	    <xsl:if test="@value!=''">
	      (<xsl:value-of select="@value"/>)
	    </xsl:if>
	  </nobr>
	</td>
      </tr>
      <tr>
	<xsl:apply-templates select="imc"/>
      </tr>
    </table>
  </td>
</xsl:template>

<xsl:template match="location">
  <nobr>
    <font style="font-family:helvetica">
      <xsl:value-of select="@loc"/>
    </font>
  </nobr>
</xsl:template>

</xsl:stylesheet>
