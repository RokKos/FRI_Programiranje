% Code generated by PREV compiler
SP	GREG	Stack_Segment
FP	GREG	#6100000000000000
HP	GREG	Data_Segment
	LOC	Data_Segment
L4	BYTE	" ",0
L5	BYTE	"\n",0
L9	BYTE	"a:\n",0
L10	BYTE	"b:\n",0
L11	BYTE	"a*b:\n",0
ReadSize	IS	255

ReadArgs	BYTE	0,ReadSize

% Code Segment
	LOC	#500
Main	PUSHJ	$8,_main
% STOPPING PROGRAM
	TRAP	0,Halt,0
% Code for function: _multiply
	%	 --- Prolog ---
_multiply	SET	 $0,56
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
	JMP	 L43
L43	SET	 $0,0
	SET	 $1,$0
	SET	 $0,200
	SET	 $0,$0
	STO	 $1,$254,0
	STO	 $0,$254,8
	PUSHJ	 $8,_new
	LDO	 $0,$254,0
	SET	 $1,$0
	SET	 $2,$253
	SET	 $0,8
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$2,$0
	SET	 $0,$0
	STO	 $1,$0,0
	SET	 $0,0
	SET	 $1,$0
	SET	 $2,$253
	SET	 $0,16
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$2,$0
	SET	 $0,$0
	STO	 $1,$0,0
L14	SET	 $0,$253
	SET	 $1,16
	NEG	 $1,0,$1
	SET	 $1,$1
	ADD	 $0,$0,$1
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $1,$0
	SET	 $0,5
	SET	 $0,$0
	CMP	 $0,$1,$0
	ZSN	 $0,$0,1
	SET	 $0,$0
	BZ	 $0,L21
L13	SET	 $0,0
	SET	 $2,$0
	SET	 $0,$253
	SET	 $1,24
	NEG	 $1,0,$1
	SET	 $1,$1
	ADD	 $0,$0,$1
	SET	 $0,$0
	STO	 $2,$0,0
L16	SET	 $0,$253
	SET	 $1,24
	NEG	 $1,0,$1
	SET	 $1,$1
	ADD	 $0,$0,$1
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $1,$0
	SET	 $0,5
	SET	 $0,$0
	CMP	 $0,$1,$0
	ZSN	 $0,$0,1
	SET	 $0,$0
	BZ	 $0,L20
L15	SET	 $0,0
	SET	 $2,$0
	SET	 $1,$253
	SET	 $0,32
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$1,$0
	SET	 $0,$0
	STO	 $2,$0,0
	SET	 $0,0
	SET	 $1,$0
	SET	 $0,$253
	SET	 $2,40
	NEG	 $2,0,$2
	SET	 $2,$2
	ADD	 $0,$0,$2
	SET	 $0,$0
	STO	 $1,$0,0
L18	SET	 $1,$253
	SET	 $0,32
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$1,$0
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $1,$0
	SET	 $0,5
	SET	 $0,$0
	CMP	 $0,$1,$0
	ZSN	 $0,$0,1
	SET	 $0,$0
	BZ	 $0,L19
L17	SET	 $1,$253
	SET	 $0,40
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$1,$0
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $1,$0
	SET	 $0,$253
	SET	 $2,8
	SET	 $2,$2
	ADD	 $0,$0,$2
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $0,$0
	SET	 $2,$253
	SET	 $3,16
	NEG	 $3,0,$3
	SET	 $3,$3
	ADD	 $2,$2,$3
	SET	 $2,$2
	LDO	 $2,$2,0
	SET	 $3,$2
	SET	 $2,40
	SET	 $2,$2
	MUL	 $2,$3,$2
	SET	 $2,$2
	ADD	 $0,$0,$2
	SET	 $0,$0
	SET	 $3,$253
	SET	 $2,32
	NEG	 $2,0,$2
	SET	 $2,$2
	ADD	 $2,$3,$2
	SET	 $2,$2
	LDO	 $2,$2,0
	SET	 $2,$2
	SET	 $3,8
	SET	 $3,$3
	MUL	 $2,$2,$3
	SET	 $2,$2
	ADD	 $0,$0,$2
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $2,$0
	SET	 $3,$253
	SET	 $0,16
	SET	 $0,$0
	ADD	 $0,$3,$0
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $0,$0
	SET	 $4,$253
	SET	 $3,32
	NEG	 $3,0,$3
	SET	 $3,$3
	ADD	 $3,$4,$3
	SET	 $3,$3
	LDO	 $3,$3,0
	SET	 $4,$3
	SET	 $3,40
	SET	 $3,$3
	MUL	 $3,$4,$3
	SET	 $3,$3
	ADD	 $0,$0,$3
	SET	 $0,$0
	SET	 $3,$253
	SET	 $4,24
	NEG	 $4,0,$4
	SET	 $4,$4
	ADD	 $3,$3,$4
	SET	 $3,$3
	LDO	 $3,$3,0
	SET	 $3,$3
	SET	 $4,8
	SET	 $4,$4
	MUL	 $3,$3,$4
	SET	 $3,$3
	ADD	 $0,$0,$3
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $0,$0
	MUL	 $0,$2,$0
	SET	 $0,$0
	ADD	 $0,$1,$0
	SET	 $1,$0
	SET	 $2,$253
	SET	 $0,40
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$2,$0
	SET	 $0,$0
	STO	 $1,$0,0
	SET	 $1,$253
	SET	 $0,32
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$1,$0
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $1,$0
	SET	 $0,1
	SET	 $0,$0
	ADD	 $0,$1,$0
	SET	 $2,$0
	SET	 $1,$253
	SET	 $0,32
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$1,$0
	SET	 $0,$0
	STO	 $2,$0,0
	JMP	 L18
L19	SET	 $1,$253
	SET	 $0,40
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$1,$0
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $1,$0
	SET	 $2,$253
	SET	 $0,8
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$2,$0
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $0,$0
	SET	 $2,$253
	SET	 $3,16
	NEG	 $3,0,$3
	SET	 $3,$3
	ADD	 $2,$2,$3
	SET	 $2,$2
	LDO	 $2,$2,0
	SET	 $2,$2
	SET	 $3,40
	SET	 $3,$3
	MUL	 $2,$2,$3
	SET	 $2,$2
	ADD	 $0,$0,$2
	SET	 $0,$0
	SET	 $2,$253
	SET	 $3,24
	NEG	 $3,0,$3
	SET	 $3,$3
	ADD	 $2,$2,$3
	SET	 $2,$2
	LDO	 $2,$2,0
	SET	 $3,$2
	SET	 $2,8
	SET	 $2,$2
	MUL	 $2,$3,$2
	SET	 $2,$2
	ADD	 $0,$0,$2
	SET	 $0,$0
	STO	 $1,$0,0
	SET	 $1,$253
	SET	 $0,24
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$1,$0
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $1,$0
	SET	 $0,1
	SET	 $0,$0
	ADD	 $0,$1,$0
	SET	 $0,$0
	SET	 $2,$253
	SET	 $1,24
	NEG	 $1,0,$1
	SET	 $1,$1
	ADD	 $1,$2,$1
	SET	 $1,$1
	STO	 $0,$1,0
	JMP	 L16
L20	SET	 $0,$253
	SET	 $1,16
	NEG	 $1,0,$1
	SET	 $1,$1
	ADD	 $0,$0,$1
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $0,$0
	SET	 $1,1
	SET	 $1,$1
	ADD	 $0,$0,$1
	SET	 $1,$0
	SET	 $2,$253
	SET	 $0,16
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$2,$0
	SET	 $0,$0
	STO	 $1,$0,0
	JMP	 L14
L21	SET	 $0,$253
	SET	 $1,8
	NEG	 $1,0,$1
	SET	 $1,$1
	ADD	 $0,$0,$1
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $0,$0
	JMP	 L44
	%	 --- Epilogue ---
L44	STO	 $0,FP,0  % Save return value 
	%	 Highering Stack pointer 
	SET	 SP,FP
	%	 Getting RA 
	SET	 $0,56
	SUB	 $0,SP,$0
	LDO	 $1,$0,8
	PUT	 rJ,$1
	%	 Getting old FP 
	LDO	 FP,$0,0
	POP	 8,0
% Code for function: _print
	%	 --- Prolog ---
_print	SET	 $0,32
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
	JMP	 L45
L45	SET	 $0,0
	SET	 $1,$0
	SET	 $2,$253
	SET	 $0,8
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$2,$0
	SET	 $0,$0
	STO	 $1,$0,0
L23	SET	 $1,$253
	SET	 $0,8
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$1,$0
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $1,$0
	SET	 $0,5
	SET	 $0,$0
	CMP	 $0,$1,$0
	ZSN	 $0,$0,1
	SET	 $0,$0
	BZ	 $0,L27
L22	SET	 $0,0
	SET	 $2,$0
	SET	 $0,$253
	SET	 $1,16
	NEG	 $1,0,$1
	SET	 $1,$1
	ADD	 $0,$0,$1
	SET	 $0,$0
	STO	 $2,$0,0
L25	SET	 $1,$253
	SET	 $0,16
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$1,$0
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $1,$0
	SET	 $0,5
	SET	 $0,$0
	CMP	 $0,$1,$0
	ZSN	 $0,$0,1
	SET	 $0,$0
	BZ	 $0,L26
L24	SET	 $0,0
	SET	 $0,$0
	SET	 $2,$253
	SET	 $1,8
	SET	 $1,$1
	ADD	 $1,$2,$1
	SET	 $1,$1
	LDO	 $1,$1,0
	SET	 $1,$1
	SET	 $3,$253
	SET	 $2,8
	NEG	 $2,0,$2
	SET	 $2,$2
	ADD	 $2,$3,$2
	SET	 $2,$2
	LDO	 $2,$2,0
	SET	 $3,$2
	SET	 $2,40
	SET	 $2,$2
	MUL	 $2,$3,$2
	SET	 $2,$2
	ADD	 $1,$1,$2
	SET	 $1,$1
	SET	 $3,$253
	SET	 $2,16
	NEG	 $2,0,$2
	SET	 $2,$2
	ADD	 $2,$3,$2
	SET	 $2,$2
	LDO	 $2,$2,0
	SET	 $3,$2
	SET	 $2,8
	SET	 $2,$2
	MUL	 $2,$3,$2
	SET	 $2,$2
	ADD	 $1,$1,$2
	SET	 $1,$1
	LDO	 $1,$1,0
	SET	 $1,$1
	STO	 $0,$254,0
	STO	 $1,$254,8
	PUSHJ	 $8,_putInt
	LDO	 $0,$254,0
	SET	 $0,0
	SET	 $2,$0
	LDA	 $0,L4
	LDO	 $1,$0,0
	SET	 $0,$0
	STO	 $2,$254,0
	STO	 $0,$254,8
	PUSHJ	 $8,_putString
	LDO	 $0,$254,0
	SET	 $1,$253
	SET	 $0,16
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$1,$0
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $1,$0
	SET	 $0,1
	SET	 $0,$0
	ADD	 $0,$1,$0
	SET	 $0,$0
	SET	 $2,$253
	SET	 $1,16
	NEG	 $1,0,$1
	SET	 $1,$1
	ADD	 $1,$2,$1
	SET	 $1,$1
	STO	 $0,$1,0
	JMP	 L25
L26	SET	 $0,0
	SET	 $1,$0
	LDA	 $2,L5
	LDO	 $0,$2,0
	SET	 $0,$2
	STO	 $1,$254,0
	STO	 $0,$254,8
	PUSHJ	 $8,_putString
	LDO	 $0,$254,0
	SET	 $0,$253
	SET	 $1,8
	NEG	 $1,0,$1
	SET	 $1,$1
	ADD	 $0,$0,$1
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $1,$0
	SET	 $0,1
	SET	 $0,$0
	ADD	 $0,$1,$0
	SET	 $0,$0
	SET	 $2,$253
	SET	 $1,8
	NEG	 $1,0,$1
	SET	 $1,$1
	ADD	 $1,$2,$1
	SET	 $1,$1
	STO	 $0,$1,0
	JMP	 L23
L27	SET	 $0,0
	SET	 $0,$0
	JMP	 L46
	%	 --- Epilogue ---
L46	STO	 $0,FP,0  % Save return value 
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
% Code for function: _init
	%	 --- Prolog ---
_init	SET	 $0,40
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
	JMP	 L47
L47	SET	 $0,0
	SET	 $0,$0
	SET	 $1,200
	SET	 $1,$1
	STO	 $0,$254,0
	STO	 $1,$254,8
	PUSHJ	 $8,_new
	LDO	 $0,$254,0
	SET	 $2,$0
	SET	 $1,$253
	SET	 $0,8
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$1,$0
	SET	 $0,$0
	STO	 $2,$0,0
	SET	 $0,0
	SET	 $0,$0
	SET	 $2,$253
	SET	 $1,16
	NEG	 $1,0,$1
	SET	 $1,$1
	ADD	 $1,$2,$1
	SET	 $1,$1
	STO	 $0,$1,0
L29	SET	 $1,$253
	SET	 $0,16
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$1,$0
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $0,$0
	SET	 $1,5
	SET	 $1,$1
	CMP	 $0,$0,$1
	ZSN	 $0,$0,1
	SET	 $0,$0
	BZ	 $0,L33
L28	SET	 $0,0
	SET	 $2,$0
	SET	 $0,$253
	SET	 $1,24
	NEG	 $1,0,$1
	SET	 $1,$1
	ADD	 $0,$0,$1
	SET	 $0,$0
	STO	 $2,$0,0
L31	SET	 $1,$253
	SET	 $0,24
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$1,$0
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $1,$0
	SET	 $0,5
	SET	 $0,$0
	CMP	 $0,$1,$0
	ZSN	 $0,$0,1
	SET	 $0,$0
	BZ	 $0,L32
L30	SET	 $0,$253
	SET	 $1,8
	SET	 $1,$1
	ADD	 $0,$0,$1
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $1,$0
	SET	 $2,$253
	SET	 $0,8
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$2,$0
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $0,$0
	SET	 $3,$253
	SET	 $2,16
	NEG	 $2,0,$2
	SET	 $2,$2
	ADD	 $2,$3,$2
	SET	 $2,$2
	LDO	 $2,$2,0
	SET	 $3,$2
	SET	 $2,40
	SET	 $2,$2
	MUL	 $2,$3,$2
	SET	 $2,$2
	ADD	 $0,$0,$2
	SET	 $0,$0
	SET	 $3,$253
	SET	 $2,24
	NEG	 $2,0,$2
	SET	 $2,$2
	ADD	 $2,$3,$2
	SET	 $2,$2
	LDO	 $2,$2,0
	SET	 $3,$2
	SET	 $2,8
	SET	 $2,$2
	MUL	 $2,$3,$2
	SET	 $2,$2
	ADD	 $0,$0,$2
	SET	 $0,$0
	STO	 $1,$0,0
	SET	 $1,$253
	SET	 $0,24
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
	SET	 $0,$253
	SET	 $2,24
	NEG	 $2,0,$2
	SET	 $2,$2
	ADD	 $0,$0,$2
	SET	 $0,$0
	STO	 $1,$0,0
	JMP	 L31
L32	SET	 $1,$253
	SET	 $0,16
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$1,$0
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $0,$0
	SET	 $1,1
	SET	 $1,$1
	ADD	 $0,$0,$1
	SET	 $1,$0
	SET	 $2,$253
	SET	 $0,16
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$2,$0
	SET	 $0,$0
	STO	 $1,$0,0
	JMP	 L29
L33	SET	 $1,$253
	SET	 $0,8
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$1,$0
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $0,$0
	JMP	 L48
	%	 --- Epilogue ---
L48	STO	 $0,FP,0  % Save return value 
	%	 Highering Stack pointer 
	SET	 SP,FP
	%	 Getting RA 
	SET	 $0,40
	SUB	 $0,SP,$0
	LDO	 $1,$0,8
	PUT	 rJ,$1
	%	 Getting old FP 
	LDO	 FP,$0,0
	POP	 8,0
% Code for function: _eye
	%	 --- Prolog ---
_eye	SET	 $0,40
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
	JMP	 L49
L49	SET	 $0,0
	SET	 $1,$0
	SET	 $0,200
	SET	 $0,$0
	STO	 $1,$254,0
	STO	 $0,$254,8
	PUSHJ	 $8,_new
	LDO	 $0,$254,0
	SET	 $1,$0
	SET	 $2,$253
	SET	 $0,8
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$2,$0
	SET	 $0,$0
	STO	 $1,$0,0
	SET	 $0,0
	SET	 $1,$0
	SET	 $2,$253
	SET	 $0,16
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$2,$0
	SET	 $0,$0
	STO	 $1,$0,0
L35	SET	 $0,$253
	SET	 $1,16
	NEG	 $1,0,$1
	SET	 $1,$1
	ADD	 $0,$0,$1
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $0,$0
	SET	 $1,5
	SET	 $1,$1
	CMP	 $0,$0,$1
	ZSN	 $0,$0,1
	SET	 $0,$0
	BZ	 $0,L42
L34	SET	 $0,0
	SET	 $1,$0
	SET	 $0,$253
	SET	 $2,24
	NEG	 $2,0,$2
	SET	 $2,$2
	ADD	 $0,$0,$2
	SET	 $0,$0
	STO	 $1,$0,0
L37	SET	 $0,$253
	SET	 $1,24
	NEG	 $1,0,$1
	SET	 $1,$1
	ADD	 $0,$0,$1
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $1,$0
	SET	 $0,5
	SET	 $0,$0
	CMP	 $0,$1,$0
	ZSN	 $0,$0,1
	SET	 $0,$0
	BZ	 $0,L41
L36	SET	 $0,$253
	SET	 $1,16
	NEG	 $1,0,$1
	SET	 $1,$1
	ADD	 $0,$0,$1
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $0,$0
	SET	 $1,$253
	SET	 $2,24
	NEG	 $2,0,$2
	SET	 $2,$2
	ADD	 $1,$1,$2
	SET	 $1,$1
	LDO	 $1,$1,0
	SET	 $1,$1
	CMP	 $0,$0,$1
	ZSZ	 $0,$0,1
	SET	 $0,$0
	BZ	 $0,L40
L38	SET	 $0,1
	SET	 $1,$0
	SET	 $0,$253
	SET	 $2,8
	NEG	 $2,0,$2
	SET	 $2,$2
	ADD	 $0,$0,$2
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $0,$0
	SET	 $2,$253
	SET	 $3,16
	NEG	 $3,0,$3
	SET	 $3,$3
	ADD	 $2,$2,$3
	SET	 $2,$2
	LDO	 $2,$2,0
	SET	 $3,$2
	SET	 $2,40
	SET	 $2,$2
	MUL	 $2,$3,$2
	SET	 $2,$2
	ADD	 $0,$0,$2
	SET	 $0,$0
	SET	 $3,$253
	SET	 $2,24
	NEG	 $2,0,$2
	SET	 $2,$2
	ADD	 $2,$3,$2
	SET	 $2,$2
	LDO	 $2,$2,0
	SET	 $2,$2
	SET	 $3,8
	SET	 $3,$3
	MUL	 $2,$2,$3
	SET	 $2,$2
	ADD	 $0,$0,$2
	SET	 $0,$0
	STO	 $1,$0,0
	JMP	 L39
L40	SET	 $0,0
	SET	 $1,$0
	SET	 $2,$253
	SET	 $0,8
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$2,$0
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $0,$0
	SET	 $3,$253
	SET	 $2,16
	NEG	 $2,0,$2
	SET	 $2,$2
	ADD	 $2,$3,$2
	SET	 $2,$2
	LDO	 $2,$2,0
	SET	 $2,$2
	SET	 $3,40
	SET	 $3,$3
	MUL	 $2,$2,$3
	SET	 $2,$2
	ADD	 $0,$0,$2
	SET	 $0,$0
	SET	 $2,$253
	SET	 $3,24
	NEG	 $3,0,$3
	SET	 $3,$3
	ADD	 $2,$2,$3
	SET	 $2,$2
	LDO	 $2,$2,0
	SET	 $2,$2
	SET	 $3,8
	SET	 $3,$3
	MUL	 $2,$2,$3
	SET	 $2,$2
	ADD	 $0,$0,$2
	SET	 $0,$0
	STO	 $1,$0,0
L39	SET	 $1,$253
	SET	 $0,24
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$1,$0
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $1,$0
	SET	 $0,1
	SET	 $0,$0
	ADD	 $0,$1,$0
	SET	 $0,$0
	SET	 $2,$253
	SET	 $1,24
	NEG	 $1,0,$1
	SET	 $1,$1
	ADD	 $1,$2,$1
	SET	 $1,$1
	STO	 $0,$1,0
	JMP	 L37
L41	SET	 $1,$253
	SET	 $0,16
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$1,$0
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $0,$0
	SET	 $1,1
	SET	 $1,$1
	ADD	 $0,$0,$1
	SET	 $2,$0
	SET	 $0,$253
	SET	 $1,16
	NEG	 $1,0,$1
	SET	 $1,$1
	ADD	 $0,$0,$1
	SET	 $0,$0
	STO	 $2,$0,0
	JMP	 L35
L42	SET	 $1,$253
	SET	 $0,8
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$1,$0
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $0,$0
	JMP	 L50
	%	 --- Epilogue ---
L50	STO	 $0,FP,0  % Save return value 
	%	 Highering Stack pointer 
	SET	 SP,FP
	%	 Getting RA 
	SET	 $0,40
	SUB	 $0,SP,$0
	LDO	 $1,$0,8
	PUT	 rJ,$1
	%	 Getting old FP 
	LDO	 FP,$0,0
	POP	 8,0
% Code for function: _main
	%	 --- Prolog ---
_main	SET	 $0,32
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
	JMP	 L51
L51	SET	 $0,0
	SET	 $0,$0
	STO	 $0,$254,0
	PUSHJ	 $8,_eye
	LDO	 $0,$254,0
	SET	 $2,$0
	SET	 $0,$253
	SET	 $1,8
	NEG	 $1,0,$1
	SET	 $1,$1
	ADD	 $0,$0,$1
	SET	 $0,$0
	STO	 $2,$0,0
	SET	 $0,2
	SET	 $1,$0
	SET	 $2,$253
	SET	 $0,8
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$2,$0
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $0,$0
	SET	 $2,4
	SET	 $3,$2
	SET	 $2,40
	SET	 $2,$2
	MUL	 $2,$3,$2
	SET	 $2,$2
	ADD	 $0,$0,$2
	SET	 $0,$0
	SET	 $2,3
	SET	 $3,$2
	SET	 $2,8
	SET	 $2,$2
	MUL	 $2,$3,$2
	SET	 $2,$2
	ADD	 $0,$0,$2
	SET	 $0,$0
	STO	 $1,$0,0
	SET	 $0,0
	SET	 $0,$0
	LDA	 $1,L9
	LDO	 $2,$1,0
	SET	 $1,$1
	STO	 $0,$254,0
	STO	 $1,$254,8
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
	PUSHJ	 $8,_print
	LDO	 $0,$254,0
	SET	 $0,0
	SET	 $1,$0
	SET	 $0,1
	SET	 $0,$0
	STO	 $1,$254,0
	STO	 $0,$254,8
	PUSHJ	 $8,_init
	LDO	 $0,$254,0
	SET	 $1,$0
	SET	 $2,$253
	SET	 $0,16
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$2,$0
	SET	 $0,$0
	STO	 $1,$0,0
	SET	 $0,9
	SET	 $0,$0
	SET	 $2,$253
	SET	 $1,16
	NEG	 $1,0,$1
	SET	 $1,$1
	ADD	 $1,$2,$1
	SET	 $1,$1
	LDO	 $1,$1,0
	SET	 $1,$1
	SET	 $2,1
	SET	 $3,$2
	SET	 $2,40
	SET	 $2,$2
	MUL	 $2,$3,$2
	SET	 $2,$2
	ADD	 $1,$1,$2
	SET	 $1,$1
	SET	 $2,2
	SET	 $2,$2
	SET	 $3,8
	SET	 $3,$3
	MUL	 $2,$2,$3
	SET	 $2,$2
	ADD	 $1,$1,$2
	SET	 $1,$1
	STO	 $0,$1,0
	SET	 $0,4
	SET	 $1,$0
	SET	 $0,$253
	SET	 $2,16
	NEG	 $2,0,$2
	SET	 $2,$2
	ADD	 $0,$0,$2
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $0,$0
	SET	 $2,2
	SET	 $3,$2
	SET	 $2,40
	SET	 $2,$2
	MUL	 $2,$3,$2
	SET	 $2,$2
	ADD	 $0,$0,$2
	SET	 $0,$0
	SET	 $2,2
	SET	 $2,$2
	SET	 $3,8
	SET	 $3,$3
	MUL	 $2,$2,$3
	SET	 $2,$2
	ADD	 $0,$0,$2
	SET	 $0,$0
	STO	 $1,$0,0
	SET	 $0,3
	SET	 $1,$0
	SET	 $2,$253
	SET	 $0,16
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$2,$0
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $0,$0
	SET	 $2,3
	SET	 $2,$2
	SET	 $3,40
	SET	 $3,$3
	MUL	 $2,$2,$3
	SET	 $2,$2
	ADD	 $0,$0,$2
	SET	 $0,$0
	SET	 $2,2
	SET	 $3,$2
	SET	 $2,8
	SET	 $2,$2
	MUL	 $2,$3,$2
	SET	 $2,$2
	ADD	 $0,$0,$2
	SET	 $0,$0
	STO	 $1,$0,0
	SET	 $0,0
	SET	 $1,$0
	LDA	 $0,L10
	LDO	 $2,$0,0
	SET	 $0,$0
	STO	 $1,$254,0
	STO	 $0,$254,8
	PUSHJ	 $8,_putString
	LDO	 $0,$254,0
	SET	 $0,0
	SET	 $0,$0
	SET	 $2,$253
	SET	 $1,16
	NEG	 $1,0,$1
	SET	 $1,$1
	ADD	 $1,$2,$1
	SET	 $1,$1
	LDO	 $1,$1,0
	SET	 $1,$1
	STO	 $0,$254,0
	STO	 $1,$254,8
	PUSHJ	 $8,_print
	LDO	 $0,$254,0
	SET	 $0,0
	SET	 $1,$0
	LDA	 $2,L11
	LDO	 $0,$2,0
	SET	 $0,$2
	STO	 $1,$254,0
	STO	 $0,$254,8
	PUSHJ	 $8,_putString
	LDO	 $0,$254,0
	SET	 $0,0
	SET	 $1,$0
	SET	 $0,0
	SET	 $0,$0
	SET	 $2,$253
	SET	 $3,8
	NEG	 $3,0,$3
	SET	 $3,$3
	ADD	 $2,$2,$3
	SET	 $2,$2
	LDO	 $2,$2,0
	SET	 $2,$2
	SET	 $4,$253
	SET	 $3,16
	NEG	 $3,0,$3
	SET	 $3,$3
	ADD	 $3,$4,$3
	SET	 $3,$3
	LDO	 $3,$3,0
	SET	 $3,$3
	STO	 $0,$254,0
	STO	 $2,$254,8
	STO	 $3,$254,16
	PUSHJ	 $8,_multiply
	LDO	 $0,$254,0
	SET	 $0,$0
	STO	 $1,$254,0
	STO	 $0,$254,8
	PUSHJ	 $8,_print
	LDO	 $0,$254,0
	SET	 $0,0
	SET	 $0,$0
	JMP	 L52
	%	 --- Epilogue ---
L52	STO	 $0,FP,0  % Save return value 
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

--- PREV STD LIB ---

 % Code for function: _new
	%	 --- Prolog ---
_new	SET	 $0,16
	%	 Storing FP 
	SUB	 $0,SP,$0
	STO	 FP,$0,0
	%	 STORING RA 
	GET	 $1,rJ
	STO	 $1,$0,8
	%	 Lowering FP 
	SET	 FP,SP
	%	 Lowering SP 
	SET	 $0,24
	SUB	 SP,SP,$0
	JMP	 L53
L53	SET	$0,8
	ADD	$0,FP,$0
	LDO	$1,$0,0
	SET	$0,HP % For return value
	ADD	HP,HP,$1
	%	 --- Epilogue ---
L54	STO	 $0,FP,0  % Save return value 
	%	 Highering Stack pointer 
	SET	 SP,FP
	%	 Getting RA 
	SET	 $0,16
	SUB	 $0,SP,$0
	LDO	 $1,$0,8
	PUT	 rJ,$1
	%	 Getting old FP 
	LDO	 FP,$0,0
	POP	 8,0
_del	POP 8,0 % Memory leak
% Code for function: _putChar
	%	 --- Prolog ---
_putChar	SET	 $0,16
	%	 Storing FP 
	SUB	 $0,SP,$0
	STO	 FP,$0,0
	%	 STORING RA 
	GET	 $1,rJ
	STO	 $1,$0,8
	%	 Lowering FP 
	SET	 FP,SP
	%	 Lowering SP 
	SET	 $0,24
	SUB	 SP,SP,$0
	JMP	 L55
L55	SET	$0,14
	ADD	$0,FP,$0
	%Putting char one position in front
	%so that we put end char at the end
	LDB	$1,$0,1
	STB	$1,$0,0
	SET	$1,0
	STB	$1,$0,1
	SET	$255,$0
	TRAP	0,Fputs,StdOut
	%	 --- Epilogue ---
L56	STO	 $0,FP,0  % Save return value 
	%	 Highering Stack pointer 
	SET	 SP,FP
	%	 Getting RA 
	SET	 $0,16
	SUB	 $0,SP,$0
	LDO	 $1,$0,8
	PUT	 rJ,$1
	%	 Getting old FP 
	LDO	 FP,$0,0
	POP	 8,0
% Code for function: _putString
	%	 --- Prolog ---
_putString	SET	 $0,16
	%	 Storing FP 
	SUB	 $0,SP,$0
	STO	 FP,$0,0
	%	 STORING RA 
	GET	 $1,rJ
	STO	 $1,$0,8
	%	 Lowering FP 
	SET	 FP,SP
	%	 Lowering SP 
	SET	 $0,24
	SUB	 SP,SP,$0
	JMP	 L57
L57	SET	$0,8
	ADD	$0,FP,$0
	LDO	$1,$0,0
	SET	$255,$1
	TRAP	0,Fputs,StdOut
	%	 --- Epilogue ---
L58	STO	 $0,FP,0  % Save return value 
	%	 Highering Stack pointer 
	SET	 SP,FP
	%	 Getting RA 
	SET	 $0,16
	SUB	 $0,SP,$0
	LDO	 $1,$0,8
	PUT	 rJ,$1
	%	 Getting old FP 
	LDO	 FP,$0,0
	POP	 8,0
% Code for function: _readString
	%	 --- Prolog ---
_readString	SET	 $0,16
	%	 Storing FP 
	SUB	 $0,SP,$0
	STO	 FP,$0,0
	%	 STORING RA 
	GET	 $1,rJ
	STO	 $1,$0,8
	%	 Lowering FP 
	SET	 FP,SP
	%	 Lowering SP 
	SET	 $0,24
	SUB	 SP,SP,$0
	JMP	 L59
L59	LDA	$255,ReadArgs
	SET	$0,$255
	TRAP	0,Fgets,StdIn
	%	 --- Epilogue ---
L60	STO	 $0,FP,0  % Save return value 
	%	 Highering Stack pointer 
	SET	 SP,FP
	%	 Getting RA 
	SET	 $0,16
	SUB	 $0,SP,$0
	LDO	 $1,$0,8
	PUT	 rJ,$1
	%	 Getting old FP 
	LDO	 FP,$0,0
	POP	 8,0
% Code for function: _putInt
	%	 --- Prolog ---
_putInt	SET	 $0,32
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
	JMP	 L61
% Storing inverse number
L61	SET	$0,16
	SUB	$0,FP,$0
	SET	$1,1
	STO	$1,$0,0
% While condition of inverse loop
_putInt_Inverse_Loop_	SET	$0,8
	ADD	$0,$0,FP
	LDO	$0,$0,0
	BZ	$0,_putInt_Print_out_loop
% While loop of inverse loop
	SET	$0,16
	SUB	$0,FP,$0
	LDO	$2,$0,0
	MUL	$2,$2,10 % Multipling inverse num
	SET	$0,8
	ADD	$0,$0,FP
	LDO	$3,$0,0
	DIV	$3,$3,10
	STO	$3,$0,0 % Storing N
	GET	$1,rR
	ADD	$2,$2,$1
	SET	$0,16
	SUB	$0,FP,$0
	STO	$2,$0,0
	JMP	_putInt_Inverse_Loop_
% While condition of print loop
_putInt_Print_out_loop	SET	$0,16
	SUB	$0,FP,$0
	LDO	$0,$0,0
	SET	$1,1
	CMP	$0,$0,$1
	ZSP	$0,$0,1
	BZ	$0,_putInt_Print_out_end
	SET	$0,16
	SUB	$0,FP,$0
	LDO	$1,$0,0
	DIV	$1,$1,10
	GET	$2,rR
	STO	$1,$0,0
	ADD	$2,$2,48
	STO	$2,$254,8
	PUSHJ	$8,_putChar
	JMP	_putInt_Print_out_loop
_putInt_Print_out_end	JMP	L62	%	 --- Epilogue ---
L62	STO	 $0,FP,0  % Save return value 
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
