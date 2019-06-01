JAVA	= java
JAVAC	= javac
RM	= 'rm' -fr

SRCDIR	= ../../srcs
DSTDIR	= .
XSLDIR	= ../../data/

SRCS	= $(shell test -d $(SRCDIR) && find $(SRCDIR) -name '*.java')

TARGET	= $(notdir $(shell pwd))
LOGGED	= $(notdir $(shell pwd))

all: clean tests

compiler/Main.class: $(SRCS)
	$(RM) -fr compiler module-info.class
	$(JAVAC) -sourcepath $(SRCDIR) -d $(DSTDIR) $(SRCDIR)/compiler/Main.java

tests: compiler/Main.class $(shell find . -name '*.prev')
	echo ; \
	for SRC in `'ls' -1 *.prev` ; do \
		echo "TEST $$SRC:" ; \
		$(JAVA) -classpath $(DSTDIR) compiler.Main $$SRC --target-phase=$(TARGET) --logged-phase=$(LOGGED) --xsl=$(XSLDIR) ; \
		echo ; \
	done ; \
	for XML in `'ls' -1 *.xml` ; do \
		xsltproc $$XML > `basename $$XML .xml`.html ; \
	done ;

test: compiler/Main.class $(SRC)
	@ echo "TEST $$SRC:"
	@ $(JAVA) -classpath $(DSTDIR) compiler.Main $$SRC --target-phase=$(TARGET) --logged-phase=$(LOGGED) --xsl=$(XSLDIR)
	@ NAME=`basename $$SRC .prev` ; \
	for XML in `'ls' -1 $$NAME*xml` ; do \
		xsltproc $$XML > `basename $$XML .xml`.html ; \
	done ;

chrome:
	/Applications/Google\ Chrome.app/contents/MacOS/Google\ Chrome --allow-file-access-from-files 1>/dev/null 2>/dev/null &

.PHONY: clean distclean

clean:
	$(RM) *~ *.xml *.html

distclean: clean
	$(RM) -fr compiler module-info.class
	find . -type d -depth 1 -exec $(MAKE) -C {} distclean \;


assemble: tests
	./mmixal 00_rok_spill.asm
	./mmix -v -i 00_rok_spill.asm.mmo

