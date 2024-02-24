.data
    prompt: .asciiz "Enter a number:\n"  # String prompt for user input

.text
main:
    li $v0, 4               # System call code for printing a string
    la $a0, prompt          # Load the address of the prompt into $a0
    syscall

    li $v0, 5               # System call code for reading an integer
    syscall
    add $a0, $v0, $zero     # Copy the user input to $a0

    jal fib                 # Jump and link to the fib function

    add $a0, $v0, $zero     # Copy the result of fib to $a0
    li $v0, 1               # System call code for printing an integer
    syscall

    li $v0, 10              # System call code for program exit
    syscall

fib:    
    addi $t0, $zero, 1      # Initialize $t0 to 1 (used as a constant)

    beqz $a0, return0       # If input is 0, return 0
    beq $a0, $t0, return1   # If input is 1, return 1

    add $t1, $zero, $zero   # Initialize $t1 to 0 (Fibonacci term 0)
    add $t2, $zero, $zero   # Initialize $t2 to 0 (Fibonacci term 1)
    addi $t3, $zero, 1      # Initialize $t3 to 1 (Fibonacci term 2)
    addi $t4, $zero, 1      # Initialize $t4 to 1 (Loop counter)

loop:
    bge $t4, $a0, endloop   # If loop counter >= input, exit the loop
    add $t1, $t2, $t3       # Calculate the next Fibonacci term
    add $t2, $zero, $t3     # Update $t2 with the value of $t3
    add $t3, $zero, $t1     # Update $t3 with the value of $t1
    addi $t4, $t4, 1        # Increment the loop counter
    j loop

endloop:
    add $v0, $zero, $t1     # Set $v0 to the final Fibonacci term
    jr $ra                  # Return to the calling function

return0:
    add $v0, $zero, $zero   # If input is 0, return 0
    jr $ra

return1:
    addi $v0, $zero, 1      # If input is 1, return 1
    jr $ra
