DATA SEGMENT
    Byte1   DB  1010110b
    Byte2   DB  -011011b
    Six1    DW  752h
    Six2    DW  -8D4h
    Des1    DD  17036
    Des2    DD  11111111111111111b
DATA ENDS

CODE SEGMENT
main:
	Cli
	Inc			al
	Mov			bh,5*9-4*(16-7)
	Dec			Six1[ecx]
	Add			eax,cl

	Inc			ebp
	Mov			eax,100000000h*(5-100110b)+5Fh
	Dec			Des2[esi]
	Add			Des1[ebx],edx

	Cmp			bh,es:Six2[ebp]
	Jnb		step1
	Add			Byte2[edi],al
	Or			Des1[edx],-7089*8-8*(33-14)+8974
	Or			ss:Byte1[eax],1F5h-9*5*(15-8)

step1:
	Cmp			eax,Byte2[edx]
	Jnb		step2
	Add			bl,ecx

step2:
	Or			Six2[esi],(101111101001b-654)*101b-1AFh
	Or			cs:Des2[edi],1011011110110b

CODE ENDS
END