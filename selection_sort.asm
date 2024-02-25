    .data
numbers: .word -10 , -7 , 0 , 4 , 16
length:  .word 5
output_prompt: .asciiz "The sorted array is: "
New_Line: .asciiz "\n"
space_str: .asciiz " "

    .text
    .globl main

main:
    la $s0 , numbers        # $s0 = array of numbers
    lw $s1 , length         # $s1 = length of the array (n)
    li $s2 , 0              # $s2 = i and initialized to 0
    li $s3 , 1              # $s3 = j and initialized to 1
    subi $s4 , $s2 , 1      # $s4 = (n-1)
    li $s7 , 0              # $s7 = k and initialized to 0 for printing the array

    loop_i:
        bge $s2 , $s1 , print_array
        li $s5 , 0          # $s5 = index of max element and it is initialized to 0
        li $s3 , 1

        loop_j:
            bge $s3 , $s4 , increment_i
	        lw $t0 , numbers($s3)
	        lw $t1 , numbers($s5)
            bgt $t0 , $t1 , set_max

            increment_j:
                addi $s3 , $s3 , 1
                j loop_j
        
        swap_elements:
            sub $s6 , $s4 , $s2
            lw $t0 , numbers($s6)
            lw $t1 , numbers($s5)

            move $t2 , $t0
            move $t0 , $t1
            move $t1 , $t2

        increment_i:
                addi $s2 , $s2 , 1
                j loop_i
    
    set_max:
        move $s5 , $s3
        j increment_j
    
    li $v0 , 4
    la $a0 , output_prompt
    syscall

    print_array:
        bge $s7 , $s1 , end_program
        li $v0 , 1
        lw $a0 , 0($s0)
        syscall

        li $v0 , 4
        la $a0 , space_str
        syscall

        addi $s0, $s0, 4
        addi $s7, $s7, 1
        j print_array

    end_program:
        li $v0 , 4
        la $a0 , New_Line
        syscall

        li $v0 , 10
        syscall
