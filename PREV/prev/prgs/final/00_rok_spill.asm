% Code generated by PREV compiler
SP	GREG	Stack_Segment
FP	GREG	#6100000000000000
	LOC	Data_Segment
DATA	GREG	@
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
	SET	 $0,48
	SUB	 SP,SP,$0
	JMP	 L2
L2	SET	 $3,$253
	SET	 $0,1
	SET	 $2,$0
	SET	 $0,2
	SET	 $1,$0
	SET	 $0,3
	SET	 $0,$0
	STO	 $3,$254,0
	STO	 $2,$254,8
	STO	 $1,$254,16
	STO	 $0,$254,24
	PUSHJ	 $8,L0
	LDO	 $0,$254,0
	SET	 $0,42
	SET	 $0,$0
	JMP	 L3
	%	 --- Epilogue ---
L3	STO	 $0,FP,0  % Save return value 
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
% Code for function: L0
	%	 --- Prolog ---
L0	SET	 $0,16
	%	 Storing FP 
	SUB	 $0,SP,$0
	STO	 FP,$0,0
	%	 STORING RA 
	GET	 $1,rJ
	STO	 $1,$0,8
	%	 Lowering FP 
	SET	 FP,SP
	%	 Lowering SP 
	SET	 $0,16
	SUB	 SP,SP,$0
	JMP	 L4
L4	SET	 $1,$253
	SET	 $0,8
	SET	 $0,$0
	ADD	 $0,$1,$0
	SET	 $0,$0
	LDO	 $0,$0,0
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
L6	SET	$0,8
	ADD	$0,FP,$0
	LDO	$1,FP,0
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
