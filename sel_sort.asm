.data 
  size: .word 7         # Number of elements in the array
  array: .word -2,2,5,1,4,3,-1  # The array of elements

.text
  main:
    la $t0, array       # $t0 points to the beginning of the array
    lw $t1, size         # Load the size of the array
    li $t2, 0           # Initialize loop variable i to 0

    subi $t3, $t1, 1     # $t3 = size - 1, used for loop termination
    loop:
      slt $t4, $t2, $t3  # Check if i < size - 1
      beq $t4, $zero, exit  # If not, exit the outer loop

      # Initialization of minimum index to current i
      add $t5, $t2, $zero  # $t5 = minIndex = i
      addi $t6, $t2, 1     # $t6 = j = i + 1

      loop1:
        slt $t4, $t6, $t1  # Check if j < size
        beq $t4, $zero, exit1  # If not, exit the inner loop

        # Access array elements at indices i and j
        sll $t7, $t6, 2     # $t7 = j * 4
        sll $t8, $t5, 2     # $t8 = minIndex * 4
        lw $t7, 0($t0, $t7)  # $t7 = array[j]
        lw $t8, 0($t0, $t8)  # $t8 = array[minIndex]

        slt $t4, $t7, $t8   # Check if array[j] < array[minIndex]
        beq $t4, $zero, end1  # If not, go to end1

        # Update minIndex to j
        add $t5, $t6, $zero  # minIndex = j

        end1:
          addi $t6, $t6, 1   # Increment j
          j loop1

      exit1:
        beq $t5, $t2, end   # If minIndex == i, go to end

        # Swap elements at positions i and minIndex
        sll $t7, $t5, 2     # $t7 = minIndex * 4
        sll $t8, $t2, 2     # $t8 = i * 4
        lw $t9, 0($t0, $t7)  # $t9 = array[minIndex]
        lw $s0, 0($t0, $t8)  # $s0 = array[i]
        sw $t9, 0($t0, $t8)  # array[i] = array[minIndex]
        sw $s0, 0($t0, $t7)  # array[minIndex] = array[i]

        end:
          addi $t2, $t2, 1   # Increment i
          j loop

