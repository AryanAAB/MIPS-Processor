    .data
Radius_input: .asciiz "Enter the radius:\n"
Result: .asciiz "The number of lattice points are: "
New_Line: .asciiz "\n"
    .text
    .globl main

main:
    # printing the prompt to input radius 
    li $v0 , 4
    la $a0 , Radius_input
    syscall

    # input of radius
    li $v0 , 5
    syscall
    move $s0 , $v0

    # printing new line
    li $v0 , 4
    la $a0 , New_Line
    syscall    

    li $s1 , 0      #counter
    li $s2 , 1      #x_coordinate
    li $s3 , 0      #y_coordinate

    loop_x:
        bge $s2 , $s0 , end_program     #if x_coordinate is greater than radius then break out of loop
        li $s3 , 0      #for every time the loop begins again, reset y_coordinate to 0

        loop_y:
            bge $s3 , $s0 , increment_x     #if y_coordinate is greater than radius then break from y loop and increase x_coordinate

            mul $t0 , $s2 , $s2     # x^2
            mul $t1 , $s3 , $s3     # y^2
            add $t2 , $t0 , $t1     # x^2 + y^2
            mul $t3 , $s0 , $s0     # r^2

            blt $t2 , $t3 , increment_counter       #if (x^2 + y^2 < r^2) then increase counter by 1

            increment_y:        # increase y_coordinate by 1
                addi $s3 , $s3 , 1
                j loop_y

        increment_x:        # increase x_coordinate by 1
            addi $s2 , $s2 , 1
            j loop_x
    
    increment_counter:      # increase counter by 1
        addi $s1 , $s1 , 1
        j increment_y
    
    end_program:
        # printing the prompt to display the number of lattice points
        li $v0 , 4
        la $a0 , Result
        syscall

        # the final count of lattice points will be ((4 * counter) + 1).
        li $v0 , 1
        mul $s1 , $s1 , 4
        addi $s1 , $s1 , 1
        move $a0 , $s1
        syscall

        # printing new line
        li $v0 , 4
        la $a0 , New_Line
        syscall

        # ending the program
        li $v0 , 10
        syscall
