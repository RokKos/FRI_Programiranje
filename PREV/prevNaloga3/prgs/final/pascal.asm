% Code generated by PREV compiler
SP	GREG	Stack_Segment
FP	GREG	#6100000000000000
HP	GREG	Data_Segment
	LOC	Data_Segment
ReadSize	IS	255

ReadArgs	BYTE	0,ReadSize

% Code Segment
	LOC	#500
Main	PUSHJ	$8,_main
% STOPPING PROGRAM
	TRAP	0,Halt,0
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
	JMP	 L17
L17	SET	 $0,0
	SET	 $1,$0
	SET	 $2,$253
	SET	 $0,8
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$2,$0
	SET	 $0,$0
	STO	 $1,$0,0
L6	SET	 $1,$253
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
	BZ	 $0,L10
L5	SET	 $0,0
	SET	 $2,$0
	SET	 $1,$253
	SET	 $0,16
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$1,$0
	SET	 $0,$0
	STO	 $2,$0,0
L8	SET	 $1,$253
	SET	 $0,16
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$1,$0
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $0,$0
	SET	 $1,$253
	SET	 $2,8
	NEG	 $2,0,$2
	SET	 $2,$2
	ADD	 $1,$1,$2
	SET	 $1,$1
	LDO	 $1,$1,0
	SET	 $1,$1
	CMP	 $0,$0,$1
	ZSNP	 $0,$0,1
	SET	 $0,$0
	BZ	 $0,L9
L7	SET	 $0,0
	SET	 $1,$0
	SET	 $0,0
	SET	 $0,$0
	SET	 $3,$253
	SET	 $2,16
	NEG	 $2,0,$2
	SET	 $2,$2
	ADD	 $2,$3,$2
	SET	 $2,$2
	LDO	 $2,$2,0
	SET	 $2,$2
	SET	 $4,$253
	SET	 $3,8
	NEG	 $3,0,$3
	SET	 $3,$3
	ADD	 $3,$4,$3
	SET	 $3,$3
	LDO	 $3,$3,0
	SET	 $3,$3
	STO	 $0,$254,0
	STO	 $2,$254,8
	STO	 $3,$254,16
	PUSHJ	 $8,_pascal
	LDO	 $0,$254,0
	SET	 $0,$0
	STO	 $1,$254,0
	STO	 $0,$254,8
	PUSHJ	 $8,_putInt
	LDO	 $0,$254,0
	SET	 $0,0
	SET	 $1,$0
	SET	 $0,32
	SET	 $0,$0
	STO	 $1,$254,0
	STO	 $0,$254,8
	PUSHJ	 $8,_putChar
	LDO	 $0,$254,0
	SET	 $0,$253
	SET	 $1,16
	NEG	 $1,0,$1
	SET	 $1,$1
	ADD	 $0,$0,$1
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $1,$0
	SET	 $0,1
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
	JMP	 L8
L9	SET	 $0,0
	SET	 $0,$0
	SET	 $1,10
	SET	 $2,$1
	SET	 $1,256
	SET	 $1,$1
	DIV	 $1,$2,$1
	GET	 $1,rR
	SET	 $1,$1
	STO	 $0,$254,0
	STO	 $1,$254,8
	PUSHJ	 $8,_putChar
	LDO	 $0,$254,0
	SET	 $1,$253
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
	SET	 $0,$0
	SET	 $2,$253
	SET	 $1,8
	NEG	 $1,0,$1
	SET	 $1,$1
	ADD	 $1,$2,$1
	SET	 $1,$1
	STO	 $0,$1,0
	JMP	 L6
L10	SET	 $0,0
	SET	 $0,$0
	JMP	 L18
	%	 --- Epilogue ---
L18	STO	 $0,FP,0  % Save return value 
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
% Code for function: _pascal
	%	 --- Prolog ---
_pascal	SET	 $0,24
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
	JMP	 L19
L19	SET	 $1,$253
	SET	 $0,8
	SET	 $0,$0
	ADD	 $0,$1,$0
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $1,$0
	SET	 $0,0
	SET	 $0,$0
	CMP	 $0,$1,$0
	ZSZ	 $0,$0,1
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
	SET	 $3,$3
	ADD	 $2,$2,$3
	SET	 $2,$2
	LDO	 $2,$2,0
	SET	 $2,$2
	CMP	 $0,$0,$2
	ZSZ	 $0,$0,1
	SET	 $0,$0
	OR	 $0,$1,$0
	SET	 $0,$0
	BZ	 $0,L13
L11	SET	 $0,1
	SET	 $1,$0
	SET	 $2,$253
	SET	 $0,8
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$2,$0
	SET	 $0,$0
	STO	 $1,$0,0
	JMP	 L12
L13	SET	 $1,$253
	SET	 $0,8
	SET	 $0,$0
	ADD	 $0,$1,$0
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $0,$0
	SET	 $1,0
	SET	 $1,$1
	CMP	 $0,$0,$1
	ZSN	 $0,$0,1
	SET	 $1,$0
	SET	 $2,$253
	SET	 $0,8
	SET	 $0,$0
	ADD	 $0,$2,$0
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $0,$0
	SET	 $3,$253
	SET	 $2,16
	SET	 $2,$2
	ADD	 $2,$3,$2
	SET	 $2,$2
	LDO	 $2,$2,0
	SET	 $2,$2
	CMP	 $0,$0,$2
	ZSP	 $0,$0,1
	SET	 $0,$0
	OR	 $0,$1,$0
	SET	 $0,$0
	BZ	 $0,L16
L14	SET	 $0,0
	SET	 $2,$0
	SET	 $1,$253
	SET	 $0,8
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$1,$0
	SET	 $0,$0
	STO	 $2,$0,0
	JMP	 L15
L16	SET	 $0,0
	SET	 $0,$0
	SET	 $2,$253
	SET	 $1,8
	SET	 $1,$1
	ADD	 $1,$2,$1
	SET	 $1,$1
	LDO	 $1,$1,0
	SET	 $1,$1
	SET	 $2,$253
	SET	 $3,16
	SET	 $3,$3
	ADD	 $2,$2,$3
	SET	 $2,$2
	LDO	 $2,$2,0
	SET	 $3,$2
	SET	 $2,1
	SET	 $2,$2
	SUB	 $2,$3,$2
	SET	 $2,$2
	STO	 $0,$254,0
	STO	 $1,$254,8
	STO	 $2,$254,16
	PUSHJ	 $8,_pascal
	LDO	 $0,$254,0
	SET	 $2,$0
	SET	 $0,0
	SET	 $0,$0
	SET	 $3,$253
	SET	 $1,8
	SET	 $1,$1
	ADD	 $1,$3,$1
	SET	 $1,$1
	LDO	 $1,$1,0
	SET	 $1,$1
	SET	 $3,1
	SET	 $3,$3
	SUB	 $1,$1,$3
	SET	 $1,$1
	SET	 $3,$253
	SET	 $4,16
	SET	 $4,$4
	ADD	 $3,$3,$4
	SET	 $3,$3
	LDO	 $3,$3,0
	SET	 $3,$3
	SET	 $4,1
	SET	 $4,$4
	SUB	 $3,$3,$4
	SET	 $3,$3
	STO	 $0,$254,0
	STO	 $1,$254,8
	STO	 $3,$254,16
	PUSHJ	 $8,_pascal
	LDO	 $0,$254,0
	SET	 $0,$0
	ADD	 $0,$2,$0
	SET	 $1,$0
	SET	 $0,$253
	SET	 $2,8
	NEG	 $2,0,$2
	SET	 $2,$2
	ADD	 $0,$0,$2
	SET	 $0,$0
	STO	 $1,$0,0
L15	SWYM	0,4,2 %Two labels one after another
L12	SET	 $1,$253
	SET	 $0,8
	NEG	 $0,0,$0
	SET	 $0,$0
	ADD	 $0,$1,$0
	SET	 $0,$0
	LDO	 $0,$0,0
	SET	 $0,$0
	JMP	 L20
	%	 --- Epilogue ---
L20	STO	 $0,FP,0  % Save return value 
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
	JMP	 L21
L21	SET	$0,8
	ADD	$0,FP,$0
	LDO	$1,$0,0
	SET	$0,HP % For return value
	ADD	HP,HP,$1
	%	 --- Epilogue ---
L22	STO	 $0,FP,0  % Save return value 
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
	JMP	 L23
L23	SET	$0,14
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
L24	STO	 $0,FP,0  % Save return value 
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
	JMP	 L25
L25	SET	$0,8
	ADD	$0,FP,$0
	LDO	$1,$0,0
	SET	$255,$1
	TRAP	0,Fputs,StdOut
	%	 --- Epilogue ---
L26	STO	 $0,FP,0  % Save return value 
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
	JMP	 L27
L27	LDA	$255,ReadArgs
	SET	$0,$255
	TRAP	0,Fgets,StdIn
	%	 --- Epilogue ---
L28	STO	 $0,FP,0  % Save return value 
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
	JMP	 L29
% Storing inverse number
L29	SET	$0,16
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
_putInt_Print_out_end	JMP	L30	%	 --- Epilogue ---
L30	STO	 $0,FP,0  % Save return value 
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
