RM	= 'rm' -fr

.PHONY: clean distclean

clean:
	find . -name '*~' -exec $(RM) {} \;
	$(RM) -fr bins
	$(MAKE) -C prgs distclean

distclean: clean
	find . -name '.[a-z]*' -exec $(RM) {} \;
