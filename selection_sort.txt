.data
	numbers: .word -10, 50, 74, 32, 51, 4 , 0 , -7 , -10 , 16
	length:  .word 10
	output_prompt: .asciiz "The sorted array is: "
	New_Line: .asciiz "\n"
	space_str: .asciiz " "

.text
.globl main

main:
    la $s0, numbers        # $s0 = array of numbers
    lw $s1, length         # $s1 = length of the array (n)
    li $s2, 0              # $s2 = i and initialized to 0
    
    outer:
    bge $s2, $s1, print_array #if (i >= length) then branch to print_array
	    
	    add $t1, $s2, $0  #pos = i + 0
	    addi $s3, $s2, 1  #j = i + 1
	    
	    inner:
	    bge $s3, $s1, increment_outer #if (j >= length) then branch to increment outer
	    
	    	mul $t2, $s3, 4 #j * 4
	    	mul $t3, $t1, 4 #pos * 4
	    	add $t2, $t2, $s0 #j * 4 + base
	    	add $t3, $t3, $s0 #pos * 4 + base
	    	lw  $t2, 0($t2) #M[j * 4 + base]
	    	lw  $t3, 0($t3) #M[pos * 4 + base]
	    
	    	bge $t2, $t3, not_less #if (t2 < t3 then update pos to j)
	    		addi $t1, $s3, 0  #pos = j
	    	
	    	not_less:	#increment j by 1
	    	addi $s3, $s3, 1
	    	j inner
	    
	    increment_outer:
	    mul $t2, $s2, 4
	    add $t2, $t2, $s0 #load address of M[i] into $t2
	    mul $t3, $t1, 4
	    add $t3, $t3, $s0 #load address of M[pos] into $t3
	    
	    #swapping M[i] and M[pos]
	    lw $t4, 0($t2)
	    lw $t5, 0($t3)
	    sw $t5, 0($t2)
	    sw $t4, 0($t3)
	    
	    #incrementing i by 1
	    addi $s2, $s2, 1
	    j outer
	
     print_array:
     
     #printing prompt
     la $t0, output_prompt
     li $v0, 4
     add $a0, $t0, $0
     syscall
     
     #i = 0
     li $t0, 0
     
     print:
     bge $t0, $s1, exit
     
     	#Getting value of i'th element
        mul $t3, $t0, 4
     	add $t1, $t3, $s0
     	lw $t1, 0($t1)
     	
     	#Printing i'th element
     	li $v0, 1
     	add $a0, $t1, $0
     	syscall
     	
     	#Printing a space
     	la $t4, space_str
     	li $v0, 4
     	add $a0, $t4, $0
     	syscall
     	
     	#Incrementing i by 1
     	addi $t0, $t0, 1
     	j print
     
     #Printing a new line
     exit:
     la $t0, New_Line
     li $v0, 4
     add $a0, $t0, $0
     syscall
     
     #Exiting the program
     li $v0, 10
     syscall