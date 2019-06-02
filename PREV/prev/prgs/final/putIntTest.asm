% Code generated by PREV compiler
SP	GREG	Stack_Segment
FP	GREG	#6100000000000000
HP	GREG	Data_Segment
	LOC	Data_Segment
% Code Segment
	LOC	#500
Main	PUSHJ	$8,_main
% STOPPING PROGRAM
	TRAP	0,Halt,0
% Code for function: _main
	%	 --- Prolog ---
_main	SET	 $0,16
	%	 Storing FP 
	SUB	 $0,SP,$0
	STO	 FP,$0,0
	%	 STORING RA 
	GET	 $1,rJ
	STO	 $1,$0,8
	%	 Lowering FP 
	SET	 FP,SP
	%	 Lowering SP 
	SET	 $0,32
	SUB	 SP,SP,$0
	JMP	 L4
L4	SET	 $0,0
	SET	 $1,$0
	SET	 $0,42
	SET	 $0,$0
	STO	 $1,$254,0
	STO	 $0,$254,8
	PUSHJ	 $8,_putInt
	LDO	 $0,$254,0
	SET	 $0,42
	SET	 $0,$0
	JMP	 L5
	%	 --- Epilogue ---
L5	STO	 $0,FP,0  % Save return value 
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
	JMP	 L6
L6	SET	$0,14
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
L7	STO	 $0,FP,0  % Save return value 
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
	JMP	 L8
L8	SET	$0,8
	ADD	$0,FP,$0
	LDO	$1,$0,0
	SET	$255,$1
	TRAP	0,Fputs,StdOut
	%	 --- Epilogue ---
L9	STO	 $0,FP,0  % Save return value 
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
	JMP	 L10
% Storing inverse number
L10	SET	$0,FP
	SET	$1,16
	NEG	$1,0,$1
	ADD	$0,$0,$1
	SET	$1,0
	STO	$1,$0,0
% While condition of inverse loop
_putInt_Inverse_Loop_	SET	$0,FP
	SET	$1,8
	ADD	$0,$0,$1
	LDO	$0,$0,0
	BZ	$0,_putInt_Print_out_loop
% While loop of inverse loop
	SET	$0,FP
	SET	$1,16
	NEG	$1,0,$1
	ADD	$0,$0,$1
	LDO	$2,$0,0
	MUL	$2,$2,10 % Multipling inverse num
	SET	$0,FP
	SET	$1,8
	ADD	$0,$0,$1
	LDO	$3,$0,0
	DIV	$3,$3,10
	STO	$3,$0,0 % Storing N
	GET	$1,rR
	ADD	$2,$2,$1
	SET	$0,FP
	SET	$1,16
	NEG	$1,0,$1
	ADD	$0,$0,$1
	STO	$2,$0,0
	JMP	_putInt_Inverse_Loop_
% While condition of print loop
_putInt_Print_out_loop	SET	$0,FP
	SET	$1,16
	NEG	$1,0,$1
	ADD	$0,$0,$1
	LDO	$0,$0,0
	BZ	$0,_putInt_Print_out_end
	SET	$0,FP
	SET	$1,16
	NEG	$1,0,$1
	ADD	$0,$0,$1
	LDO	$1,$0,0
	DIV	$1,$1,10
	GET	$2,rR
	STO	$1,$0,0
	ADD	$2,$2,48
	STO	$2,$254,8
	PUSHJ	$8,_putChar
	JMP	_putInt_Print_out_loop
_putInt_Print_out_end	JMP	L11	%	 --- Epilogue ---
L11	STO	 $0,FP,0  % Save return value 
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
