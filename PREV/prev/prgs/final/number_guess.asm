% Code generated by PREV compiler
SP	GREG	Stack_Segment
FP	GREG	#6100000000000000
	LOC	Data_Segment
DATA	GREG	@
L5	"Ugani celo stevilo x na intervalu [1,100]",0
L6	"Vpisi stevilo: ",0
L7	"x je manjsi",0
L8	"x je vecji",0
L9	"Bravo, zadel si v ",0
L10	" poskusov!",0
% Code Segment
	LOC	#500
Main	PUSHJ	$8,main
% STOPPING PROGRAM
	TRAP	0,Halt,0
% Code for function: _readInt
	%	 --- Prolog ---
readInt	SET	 $0,48
	%	 Storing FP 
	SUB	 $0,SP,$0
	STO	 FP,$0,0
	%	 STORING RA 
	GET	 $1,rJ
	STO	 $1,$0,8
	%	 Lowering FP 
	SET	 FP,SP
	%	 Lowering SP 
	SET	 $0,56
	SUB	 SP,SP,$0
	JMP	 L31
L31	SET	 $0,0
	SET	 $0,$0
	STO	 $0,$254,0
	PUSHJ	 $8,_readString
	LDO	 $0,$254,0
	SET	 $0,$253
	SET	 $2,16
	NEG	 $2,0,$2
	SET	 $2,$2
	ADD	 $0,$0,$2
	SET	 $0,$0
	STO	 $1,$0,0
	SET	 $0,0
	SET	 $2,$0
	SET	 $1,$253
	SET	 $0,32
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$1,$0
	SET	 $0,$0
	STO	 $2,$0,0
	SET	 $0,$253
	SET	 $1,16
	NEG	 $1,0,$1
	SET	 $1,$1
	ADD	 $0,$0,$1
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $1,$0
	SET	 $0,45
	SET	 $0,$0
	CMP	 $0,$1,$0
	NXOR	 $0,$0,0
	SET	 $0,$0
	BZ	 $0,L15
L13	SET	 $0,1
	SET	 $2,$0
	SET	 $0,$253
	SET	 $1,32
	NEG	 $1,0,$1
	SET	 $1,$1
	ADD	 $0,$0,$1
	SET	 $0,$0
	STO	 $2,$0,0
	SET	 $0,$253
	SET	 $1,16
	NEG	 $1,0,$1
	SET	 $1,$1
	ADD	 $0,$0,$1
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $0,$0
	SET	 $1,8
	SET	 $1,$1
	ADD	 $0,$0,$1
	SET	 $0,$0
	SET	 $2,$253
	SET	 $1,16
	NEG	 $1,0,$1
	SET	 $1,$1
	ADD	 $1,$2,$1
	SET	 $1,$1
	STO	 $0,$1,0
	JMP	 L14
L14	SET	 $0,0
	SET	 $0,$0
	SET	 $2,$253
	SET	 $1,8
	NEG	 $1,0,$1
	SET	 $1,$1
	ADD	 $1,$2,$1
	SET	 $1,$1
	STO	 $0,$1,0
	SET	 $0,$253
	SET	 $1,16
	NEG	 $1,0,$1
	SET	 $1,$1
	ADD	 $0,$0,$1
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $0,$0
	SET	 $2,$253
	SET	 $1,24
	NEG	 $1,0,$1
	SET	 $1,$1
	ADD	 $1,$2,$1
	SET	 $1,$1
	STO	 $0,$1,0
L17	SET	 $1,$253
	SET	 $0,24
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$1,$0
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $1,$0
	SET	 $0,48
	SET	 $0,$0
	CMP	 $0,$1,$0
	ZSNN	 $0,$0,1
	SET	 $0,$0
	SET	 $2,$253
	SET	 $1,24
	NEG	 $1,0,$1
	SET	 $1,$1
	ADD	 $1,$2,$1
	SET	 $1,$1
	LDO	 $1,$1,0
	SET	 $2,$1
	SET	 $1,57
	SET	 $1,$1
	CMP	 $1,$2,$1
	ZSNP	 $1,$1,1
	SET	 $1,$1
	AND	 $0,$0,$1
	SET	 $0,$0
	BZ	 $0,L18
L16	SET	 $1,$253
	SET	 $0,8
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$1,$0
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $0,$0
	SET	 $1,10
	SET	 $1,$1
	MUL	 $0,$0,$1
	SET	 $0,$0
	SET	 $2,$253
	SET	 $1,24
	NEG	 $1,0,$1
	SET	 $1,$1
	ADD	 $1,$2,$1
	SET	 $1,$1
	LDO	 $1,$1,0
	SET	 $1,$1
	SET	 $2,48
	SET	 $2,$2
	SUB	 $1,$1,$2
	SET	 $1,$1
	ADD	 $0,$0,$1
	SET	 $2,$0
	SET	 $0,$253
	SET	 $1,8
	NEG	 $1,0,$1
	SET	 $1,$1
	ADD	 $0,$0,$1
	SET	 $0,$0
	STO	 $2,$0,0
	SET	 $1,$253
	SET	 $0,16
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$1,$0
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $1,$0
	SET	 $0,8
	SET	 $0,$0
	ADD	 $0,$1,$0
	SET	 $1,$0
	SET	 $2,$253
	SET	 $0,16
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$2,$0
	SET	 $0,$0
	STO	 $1,$0,0
	SET	 $1,$253
	SET	 $0,16
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$1,$0
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $1,$0
	SET	 $2,$253
	SET	 $0,24
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$2,$0
	SET	 $0,$0
	STO	 $1,$0,0
	JMP	 L17
L18	SET	 $0,$253
	SET	 $1,32
	NEG	 $1,0,$1
	SET	 $1,$1
	ADD	 $0,$0,$1
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $0,$0
	BZ	 $0,L21
L19	SET	 $1,$253
	SET	 $0,8
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$1,$0
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $1,$0
	SET	 $0,1
	NEG	 $0,0,$0
	SET	 $0,$0
	MUL	 $0,$1,$0
	SET	 $1,$0
	SET	 $2,$253
	SET	 $0,8
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$2,$0
	SET	 $0,$0
	STO	 $1,$0,0
	JMP	 L20
L20	SET	 $0,$253
	SET	 $1,8
	NEG	 $1,0,$1
	SET	 $1,$1
	ADD	 $0,$0,$1
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $0,$0
	JMP	 L32
	%	 --- Epilogue ---
L32	STO	 $0,FP,0  % Save return value 
	%	 Highering Stack pointer 
	SET	 SP,FP
	%	 Getting RA 
	SET	 $0,48
	SUB	 $0,SP,$0
	LDO	 $1,$0,8
	PUT	 rJ,$1
	%	 Getting old FP 
	LDO	 FP,$0,0
	POP	 8,0
% Code for function: _ugani
	%	 --- Prolog ---
ugani	SET	 $0,32
	%	 Storing FP 
	SUB	 $0,SP,$0
	STO	 FP,$0,0
	%	 STORING RA 
	GET	 $1,rJ
	STO	 $1,$0,8
	%	 Lowering FP 
	SET	 FP,SP
	%	 Lowering SP 
	SET	 $0,48
	SUB	 SP,SP,$0
	JMP	 L33
L33	SET	 $0,0
	SET	 $2,$0
	SET	 $1,$253
	SET	 $0,8
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$1,$0
	SET	 $0,$0
	STO	 $2,$0,0
	SET	 $0,0
	SET	 $1,$0
	SET	 $0, L5
	SET	 $0,$0
	STO	 $1,$254,0
	STO	 $0,$254,8
	PUSHJ	 $8,_putString
	LDO	 $0,$254,0
	SET	 $0,0
	SET	 $1,$0
	SET	 $0,10
	SET	 $2,$0
	SET	 $0,256
	SET	 $0,$0
	DIV	 $0,$2,$0
	GET	 $0, $rR
	SET	 $0,$0
	STO	 $1,$254,0
	STO	 $0,$254,8
	PUSHJ	 $8,_putChar
	LDO	 $0,$254,0
	SET	 $0,1
	NEG	 $0,0,$0
	SET	 $1,$0
	SET	 $2,$253
	SET	 $0,16
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$2,$0
	SET	 $0,$0
	STO	 $1,$0,0
L23	SET	 $1,$253
	SET	 $0,16
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$1,$0
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $0,$0
	SET	 $1,$253
	SET	 $2,8
	SET	 $2,$2
	ADD	 $1,$1,$2
	SET	 $1,$1
	LDO	 $1,$1,0
	SET	 $1,$1
	CMP	 $0,$0,$1
	XOR	 $0,$0,0
	SET	 $0,$0
	BZ	 $0,L30
L22	SET	 $1,$253
	SET	 $0,8
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$1,$0
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $1,$0
	SET	 $0,1
	SET	 $0,$0
	ADD	 $0,$1,$0
	SET	 $1,$0
	SET	 $2,$253
	SET	 $0,8
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$2,$0
	SET	 $0,$0
	STO	 $1,$0,0
	SET	 $0,0
	SET	 $0,$0
	SET	 $1, L6
	SET	 $1,$1
	STO	 $0,$254,0
	STO	 $1,$254,8
	PUSHJ	 $8,_putString
	LDO	 $0,$254,0
	SET	 $0,0
	SET	 $0,$0
	STO	 $0,$254,0
	PUSHJ	 $8,_readInt
	LDO	 $0,$254,0
	SET	 $0,$253
	SET	 $2,16
	NEG	 $2,0,$2
	SET	 $2,$2
	ADD	 $0,$0,$2
	SET	 $0,$0
	STO	 $1,$0,0
	SET	 $1,$253
	SET	 $0,16
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$1,$0
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $0,$0
	SET	 $2,$253
	SET	 $1,8
	SET	 $1,$1
	ADD	 $1,$2,$1
	SET	 $1,$1
	LDO	 $1,$1,0
	SET	 $1,$1
	CMP	 $0,$0,$1
	ZSP	 $0,$0,1
	SET	 $0,$0
	BZ	 $0,L26
L24	SET	 $0,0
	SET	 $0,$0
	SET	 $1, L7
	SET	 $1,$1
	STO	 $0,$254,0
	STO	 $1,$254,8
	PUSHJ	 $8,_putString
	LDO	 $0,$254,0
	SET	 $0,0
	SET	 $0,$0
	SET	 $1,10
	SET	 $1,$1
	SET	 $2,256
	SET	 $2,$2
	DIV	 $1,$1,$2
	GET	 $1, $rR
	SET	 $1,$1
	STO	 $0,$254,0
	STO	 $1,$254,8
	PUSHJ	 $8,_putChar
	LDO	 $0,$254,0
	JMP	 L25
L26	SET	 $1,$253
	SET	 $0,16
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$1,$0
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $0,$0
	SET	 $1,$253
	SET	 $2,8
	SET	 $2,$2
	ADD	 $1,$1,$2
	SET	 $1,$1
	LDO	 $1,$1,0
	SET	 $1,$1
	CMP	 $0,$0,$1
	ZSN	 $0,$0,1
	SET	 $0,$0
	BZ	 $0,L29
L27	SET	 $0,0
	SET	 $0,$0
	SET	 $1, L8
	SET	 $1,$1
	STO	 $0,$254,0
	STO	 $1,$254,8
	PUSHJ	 $8,_putString
	LDO	 $0,$254,0
	SET	 $0,0
	SET	 $1,$0
	SET	 $0,10
	SET	 $2,$0
	SET	 $0,256
	SET	 $0,$0
	DIV	 $0,$2,$0
	GET	 $0, $rR
	SET	 $0,$0
	STO	 $1,$254,0
	STO	 $0,$254,8
	PUSHJ	 $8,_putChar
	LDO	 $0,$254,0
	JMP	 L28
L28L25	JMP	 L23
L30	SET	 $0,0
	SET	 $1,$0
	SET	 $0, L9
	SET	 $0,$0
	STO	 $1,$254,0
	STO	 $0,$254,8
	PUSHJ	 $8,_putString
	LDO	 $0,$254,0
	SET	 $0,0
	SET	 $0,$0
	SET	 $2,$253
	SET	 $1,8
	NEG	 $1,0,$1
	SET	 $1,$1
	ADD	 $1,$2,$1
	SET	 $1,$1
	LDO	 $1,$1,0
	SET	 $1,$1
	STO	 $0,$254,0
	STO	 $1,$254,8
	PUSHJ	 $8,_putInt
	LDO	 $0,$254,0
	SET	 $0,0
	SET	 $1,$0
	SET	 $0, L10
	SET	 $0,$0
	STO	 $1,$254,0
	STO	 $0,$254,8
	PUSHJ	 $8,_putString
	LDO	 $0,$254,0
	SET	 $0,0
	SET	 $1,$0
	SET	 $0,10
	SET	 $2,$0
	SET	 $0,256
	SET	 $0,$0
	DIV	 $0,$2,$0
	GET	 $0, $rR
	SET	 $0,$0
	STO	 $1,$254,0
	STO	 $0,$254,8
	PUSHJ	 $8,_putChar
	LDO	 $0,$254,0
	SET	 $0,0
	SET	 $0,$0
	JMP	 L34
	%	 --- Epilogue ---
L34	STO	 $0,FP,0  % Save return value 
	%	 Highering Stack pointer 
	SET	 SP,FP
	%	 Getting RA 
	SET	 $0,32
	SUB	 $0,SP,$0
	LDO	 $1,$0,8
	PUT	 rJ,$1
	%	 Getting old FP 
	LDO	 FP,$0,0
	POP	 8,0
% Code for function: _main
	%	 --- Prolog ---
main	SET	 $0,24
	%	 Storing FP 
	SUB	 $0,SP,$0
	STO	 FP,$0,0
	%	 STORING RA 
	GET	 $1,rJ
	STO	 $1,$0,8
	%	 Lowering FP 
	SET	 FP,SP
	%	 Lowering SP 
	SET	 $0,40
	SUB	 SP,SP,$0
	JMP	 L35
L35	SET	 $0,0
	SET	 $1,$0
	SET	 $0,10
	SET	 $0,$0
	STO	 $1,$254,0
	STO	 $0,$254,8
	PUSHJ	 $8,_ugani
	LDO	 $0,$254,0
	SET	 $0,0
	SET	 $1,$0
	SET	 $0,87
	SET	 $0,$0
	STO	 $1,$254,0
	STO	 $0,$254,8
	PUSHJ	 $8,_ugani
	LDO	 $0,$254,0
	SET	 $0,0
	SET	 $0,$0
	SET	 $1,23
	SET	 $1,$1
	STO	 $0,$254,0
	STO	 $1,$254,8
	PUSHJ	 $8,_ugani
	LDO	 $0,$254,0
	SET	 $0,0
	SET	 $1,$0
	SET	 $0,62
	SET	 $0,$0
	STO	 $1,$254,0
	STO	 $0,$254,8
	PUSHJ	 $8,_ugani
	LDO	 $0,$254,0
	SET	 $0,0
	SET	 $1,$0
	SET	 $0,77
	SET	 $0,$0
	STO	 $1,$254,0
	STO	 $0,$254,8
	PUSHJ	 $8,_ugani
	LDO	 $0,$254,0
	SET	 $0,0
	SET	 $0,$0
	SET	 $1,2
	SET	 $1,$1
	STO	 $0,$254,0
	STO	 $1,$254,8
	PUSHJ	 $8,_ugani
	LDO	 $0,$254,0
	SET	 $0,0
	SET	 $1,$0
	SET	 $0,53
	SET	 $0,$0
	STO	 $1,$254,0
	STO	 $0,$254,8
	PUSHJ	 $8,_ugani
	LDO	 $0,$254,0
	SET	 $0,0
	SET	 $1,$0
	SET	 $0,42
	SET	 $0,$0
	STO	 $1,$254,0
	STO	 $0,$254,8
	PUSHJ	 $8,_ugani
	LDO	 $0,$254,0
	SET	 $0,0
	SET	 $1,$0
	SET	 $0,41
	SET	 $0,$0
	STO	 $1,$254,0
	STO	 $0,$254,8
	PUSHJ	 $8,_ugani
	LDO	 $0,$254,0
	SET	 $0,0
	SET	 $0,$0
	SET	 $1,93
	SET	 $1,$1
	STO	 $0,$254,0
	STO	 $1,$254,8
	PUSHJ	 $8,_ugani
	LDO	 $0,$254,0
	SET	 $0,0
	SET	 $1,$0
	SET	 $0,67
	SET	 $0,$0
	STO	 $1,$254,0
	STO	 $0,$254,8
	PUSHJ	 $8,_ugani
	LDO	 $0,$254,0
	SET	 $0,0
	SET	 $0,$0
	SET	 $1,24
	SET	 $1,$1
	STO	 $0,$254,0
	STO	 $1,$254,8
	PUSHJ	 $8,_ugani
	LDO	 $0,$254,0
	SET	 $0,0
	SET	 $1,$0
	SET	 $0,37
	SET	 $0,$0
	STO	 $1,$254,0
	STO	 $0,$254,8
	PUSHJ	 $8,_ugani
	LDO	 $0,$254,0
	SET	 $0,0
	SET	 $1,$0
	SET	 $0,51
	SET	 $0,$0
	STO	 $1,$254,0
	STO	 $0,$254,8
	PUSHJ	 $8,_ugani
	LDO	 $0,$254,0
	SET	 $0,0
	SET	 $0,$0
	JMP	 L36
	%	 --- Epilogue ---
L36	STO	 $0,FP,0  % Save return value 
	%	 Highering Stack pointer 
	SET	 SP,FP
	%	 Getting RA 
	SET	 $0,24
	SUB	 $0,SP,$0
	LDO	 $1,$0,8
	PUT	 rJ,$1
	%	 Getting old FP 
	LDO	 FP,$0,0
	POP	 8,0
