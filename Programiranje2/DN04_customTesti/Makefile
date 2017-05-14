CC = gcc
CFLAGS = -std=c99 -pedantic -Wall
LIBS = -lm

TIMEOUT_VALUE = 1
HARD_TIMEOUT_VALUE = 3
TIMEOUT_SIGNAL = 124
SHELL = /bin/bash
OK_STRING=\033[1;32mOK\033[0;38m
FAILED_STRING=\033[1;31mfailed\033[0;38m
TIMEOUT_STRING=\033[1;35mtimeout\033[0;38m

${name} : ${name}.c
	@echo "=== COMPILING: ==="
	${CC} ${CFLAGS} -o $@ $< ${LIBS}

test : ${name}
	@echo "=== TESTING: ==="; \
	singleTestFile=$$(ls -1 test*.in | head -1); \
	testNumberLength=$$(( $${#singleTestFile} - 7 )); \
	cutParameter=$$((5 + testNumberLength - 1)); \
	nTests=$$(ls -1 test*.in | cut -c5-$$cutParameter | sort | tail -1); \
	nOk=0; \
	trap exit SIGINT; \
	\
	for i0 in $$(seq 1 $$nTests); do \
		i=$$(printf %0$${testNumberLength}d $$i0); \
		timeout -k ${HARD_TIMEOUT_VALUE} ${TIMEOUT_VALUE} ./${name} < test$$i.in > test$$i.res 2> /dev/null; \
		result=$$?; \
		if [[ $$result == ${TIMEOUT_SIGNAL} ]]; then \
			echo -e "test$$i -- ${TIMEOUT_STRING}"; \
		else \
			diff --ignore-trailing-space test$$i.res test$$i.out > test$$i.diff; \
			result=$$?; \
			if [[ $$result == 0 ]]; then \
				echo -e "test$$i -- ${OK_STRING}"; \
				nOk=$$((nOk + 1)); \
			else \
				echo -e "test$$i -- ${FAILED_STRING}"; \
			fi \
		fi \
	done; \
	echo "Result: $$nOk / $$nTests"

clean :
	rm -fr ${name} test*.res test*.diff
