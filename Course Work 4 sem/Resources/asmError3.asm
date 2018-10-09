DATA SEGMENT
    Byte1   DB  1010110b
    Byte2   DB  -011011b
    Six1    DW  752h
    Six2    DW  -8D4h
    Des1    DD  17036
    Des2    DD  -46870
DATA ENDS

CODE SEGMENT
main:
	C
				al
				bh,5*9-4*(16-7)
	Dc			Six1 ecx]
	Add			eax

				ebp
	Mov			,100h*(5-100110b)+5Fh
	Dec			Des2[
	Add			Des1[ebx]edx

	Cmp			bh,es:Six2[ebp]
	Jnb
	Add			Byte2[edi],al
	Or			-9*8-8*(33-14)
	Or			ss:[eax],1-9*5*(15-8)

st:
	Cmp		eax,edx]
	Jnb		s2
	Add			bl,

st2:
	Or			Six2[esi]1001001b-654)*101b-1Fh
	O			cs:Des2[edi],,1011110b

CODE ENDS
END