import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Perform extends JPanel
{
    private long PC;
    private InstructionMemory instructions;
    private DataMemory data;
    private CenterTextField field;
    private Register registers;
    private InputOutputFields io;
    private TopPanel panel;

    public Perform(InstructionMemory instructions, DataMemory data, Register registers, CenterTextField field, InputOutputFields io, TopPanel panel)
    {
        this.instructions = instructions;
        this.data = data;
        this.field = field;
        this.registers = registers;
        this.io = io;
        this.panel = panel;

        PC = MemAddress.START;
    }

    public void perform()
    {
        reset();
        MemAddress address = fetch();
        field.appendPlain("\n");

        Opcodes opcode = decode(address);
        int [] registers = regRead(address, opcode);
        field.appendPlain("\n");

        int [] values = execute(opcode, registers);
        field.appendPlain("\n");
        
        if(values != null)
        {
            int value = memAccess(opcode, registers, values);
            field.appendPlain("\n");

            wb(opcode, registers, values, value);
            field.appendPlain("\n");

            incrementPC(opcode, values, registers);
            field.appendPlain("\n");
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Program Terminated!", "Program Terminated", JOptionPane.INFORMATION_MESSAGE);
            panel.stop();
        }
    }

    private void reset()
    {
        registers.reset();
        instructions.reset();
        data.reset();
    }

    private MemAddress fetch()
    {
        field.appendBold("FETCH STAGE");
        
        MemAddress instruct = instructions.updateInstruction(PC);
        
        field.appendPlain("Reading instruction at PC = " + instruct.getAddress());
        field.appendPlain("Read value is " + instruct.getValue() + ".");
        
        return instruct;
    }

    private Opcodes decode(MemAddress mem)
    {
        field.appendBold("DECODE STAGE/REGISTER ACCESS STAGE");

        String opcode = mem.getBinValue().substring(Formats.OPCODE_START.get(), Formats.OPCODE_END.get());
        Opcodes ans = Opcodes.getByOpcode(opcode);
        
        field.appendPlain("Reading OPCODE = " + opcode + ".");
        field.appendPlain("Instruction is of type : " + ans + ".");

        if(Opcodes.getByOpcode(opcode) == Opcodes.RFORMAT)
        {
            String function = mem.getBinValue().substring(Formats.FUNC_START.get(), Formats.FUNC_END.get());
            ans = Opcodes.getByFunction(function);

            field.appendPlain("Reading function field = " + function + ".");
            field.appendPlain("Instruction is of type : " + ans + ".");
        }

        return ans;
    }

    private int [] regRead(MemAddress address, Opcodes opcode)
    {
        Formats [] format = opcode.getReadRegister1();
        String binString = address.getBinValue().substring(format[0].get(), format[1].get());
        int reg1 = registers.getValue(Processor.getIntFromBinary(binString));
        int reg1Pos = Processor.getIntFromBinary(binString);
        field.appendPlain("Register Read 1 at $" + Long.parseLong(binString, 2) + " is " + reg1 + ".");

        format = opcode.getReadRegister2();
        binString = address.getBinValue().substring(format[0].get(), format[1].get());
        int reg2 = registers.getValue(Processor.getIntFromBinary(binString));
        int reg2Pos = Processor.getIntFromBinary(binString);
        field.appendPlain("Register Read 2 at $" + Long.parseLong(binString, 2) + " is " + reg2 + ".");
        
        format = opcode.getWriteRegister3();
        binString = address.getBinValue().substring(format[0].get(), format[1].get());
        int reg3Pos = Processor.getIntFromBinary(binString);
        field.appendPlain("Register Write 3 at $" + Long.parseLong(binString, 2) + " or $" + reg2Pos + ".");

        format = opcode.getSignedExetension();
        binString = address.getBinValue().substring(format[0].get(), format[1].get());
        int before = Processor.getIntFromBinary(binString);

        while(binString.length() < 32)
        {
            binString = binString.charAt(0) + binString;
        }

        int after = Processor.getIntFromBinary(binString);
        field.appendPlain("Unsigned Extended Value " + before + ".");
        field.appendPlain("Signed Extended Value " + after + ".");

        format = opcode.getJumpFormats();
        binString = address.getBinValue().substring(format[0].get(), format[1].get());
        binString = binString + "00";
        int jumpValue = Processor.getIntFromBinary(binString);
        field.appendPlain("Jump Extension " + jumpValue + ".");

        format = opcode.getShiftFormats();
        binString = address.getBinValue().substring(format[0].get(), format[1].get());
        int shiftAmt = Processor.getIntFromBinary(binString);
        field.appendPlain("Shift Amount : " + shiftAmt + ".");

        return new int [] {reg1, reg2, before, after, jumpValue, reg1Pos, reg2Pos, reg3Pos, shiftAmt};
    }

    private int [] execute(Opcodes opcode, int [] registers)
    {
        field.appendBold("EXECUTE STAGE");

        int executeValue = -1, readValue = -1;
        int hi = -1, lo = -1;

        switch(opcode)
        {
            case RFORMAT:
                throw new UnsupportedOperationException("Cannot perform operation");
            case ADD:
                executeValue = registers[0] + registers[1];
                field.appendPlain("Adding registers A1 and A2. The resultant is " + executeValue + ".");
                break;
            case ADDU:
                long result = (long)(registers[0]) + registers[1];
                executeValue = (int)(result & 0xFFFFFFFFL);

                field.appendPlain("Adding registers A1 and A2. The resultant is " + executeValue + ".");
                break;
            case ADDIU:
                executeValue = registers[0] + registers[3];
                field.appendPlain("Adding A1 register with unsigned immediate field. The resultant is " + executeValue + ".");
                break;
            case AND:
                executeValue = registers[0] & registers[1];
                field.appendPlain("Anding registers A1 and A2. The resultant is " + executeValue + ".");
                break;
            case ANDI:
                executeValue = registers[0] & registers[2];
                field.appendPlain("Anding A1 register with unsigned immediate field. The resultant is " + executeValue + ".");
                break;
            case ORI:
                executeValue = registers[0] | registers[2];
                field.appendPlain("Oring A1 register with unsigned immediate field. The resultant is " + executeValue + ".");
                break;
            case SUB:
                executeValue = registers[0] - registers[1];
                field.appendPlain("Subtracting registers A1 and A2. The resultant is " + executeValue + ".");
                break;
            case MUL:
                long value = 1L * registers[0] * registers[1];
                hi = (int)(value >> 32);
                lo = (int)(value & -1);
                executeValue = lo;
                
                field.appendPlain("Multiplying registers A1 and A2. The resultant is " + executeValue + ".");
                break;
            case SLT:
                executeValue = registers[0] < registers[1] ? 1 : 0;
                field.appendPlain("Comparing registers A1 and A2 (A1 < A2). The resultant is " + executeValue + ".");
                break;
            case SLTI:
                executeValue = registers[0] < registers[3] ? 1 : 0;
                field.appendPlain("Comparing registers A1 with immediate value " + registers[3] + ". The resultant is " + executeValue + ".");
                break;
            case SLL:
                executeValue = registers[1] << registers[8];
                field.appendPlain("Shifting register A1 to the right by " + registers[8] + ". The resultant is " + executeValue + ".");
                break;
            case BNE:
                executeValue = (registers[0] == registers[1]) ? 0 : 1;
                field.appendPlain("Subtracting registers A1 and A2. Flag set to value " + executeValue + ".");
                break;
            case DIV:
                lo = (registers[0] / registers[1]);
                hi = (registers[0] % registers[1]);
                field.appendPlain("Dividing registers A1 and A2. Quotient : " + lo + " Remainder : " + hi + ".");
                break;
            case BEQ:
                executeValue = (registers[0] == registers[1]) ? 1 : 0;
                field.appendPlain("Subtracting registers A1 and A2. Zero flag set to value " + executeValue + ".");
                break;
            case SYSCALL:
                if(this.registers.getValue(2) == 4)
                {
                    int address = this.registers.getValue(4);
                    int pos = 0;

                    String str = "";
                    while(true)
                    {
                        if(pos == 4)
                        {
                            pos = 0;
                            address += 4;
                        }

                        DataAddress temp = data.getData(address);
                        char character = (char)(temp.getByte(pos));

                        if(character == 0)
                            break;

                        str += character;
                        pos++;
                    }

                    io.writeData(str);
                }
                else if(this.registers.getValue(2) == 1)
                {
                    int ans = this.registers.getValue(4);
                    io.writeData(ans + "\n");
                }
                else if(this.registers.getValue(2) == 10)
                {
                    field.appendBold("\nPROGRAM TERMINATED");
                    io.writeData("--program is finished running--\n");
                    return null;
                }
                else if(this.registers.getValue(2) == 5)
                {
                    readValue = io.getData();
                    field.appendPlain("Read Value : " + readValue);
                }

                break;

            case BREAK:
                JOptionPane.showMessageDialog(this, "Error!", "Error Message", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
                break;
            case LUI:
                executeValue = registers[3] << 16;
                field.appendPlain("Shifting signed immediate by 16. The resultant is " + executeValue + ".");
                break;
            
            case LW:
            case SW:
            case ADDI:
                executeValue = registers[0] + registers[3];
                field.appendPlain("Adding A1 register with signed immediate field. The resultant is " + executeValue + ".");
                break;
            
            default:
                field.appendPlain("Nothing is happening in ALU.");
                break;
        }

        return new int [] {executeValue, hi, lo, readValue};
    }

    private int memAccess(Opcodes opcode, int [] registers, int [] values)
    {
        field.appendBold("MEMORY ACCESS STAGE");

        int value = -1;
        switch(opcode)
        {
            default:
                field.appendPlain("Nothing is happening in mem acess stage.");
                break;

            case LW:
                DataAddress address = data.getData(values[0]);
                value = getValue(address);    
                field.appendPlain("Reading from data memory : " + value + " at " + values[0] + ".");
                break;  
            case SW:
                value = registers[1];
                data.writeData(values[0], value);
                field.appendPlain("Writing to memory : " + value + " at " + values[0] + ".");
                break;
        }

        return value;
    }

    private void wb(Opcodes opcode, int [] registerPositions, int [] executeValues, int memValue)
    {
        field.appendBold("WRITEBACK STAGE");

        switch (opcode)
        {
            case SUB:
            case AND:
            case ADDU:
            case ADD:
            case SLT:
            case SLL:
                field.appendPlain("Writing to register at position " + registerPositions[7] + " with value " + executeValues[0] + ".");
                registers.updateRegister(registerPositions[7], executeValues[0]);
                break;
            
            case DIV:
                field.appendPlain("Writing to register at position 33(lo) with value " + executeValues[2] + ".");
                registers.updateRegister(33, executeValues[2]);
                field.appendPlain("Writing to register at position 32(hi) with value " + executeValues[1] + ".");
                registers.updateRegister(32, executeValues[1]);
                break;
            case JAL:
                field.appendPlain("Writing to register at position 31(ra) with value " + (int)(PC + 4) + ".");
                registers.updateRegister(31, (int)(PC + 4));
                break;
            
            case LUI:
            case SLTI:
            case ORI:
            case ADDI:
            case ADDIU:
            case ANDI:
                field.appendPlain("Writing to register at position " + registerPositions[6] + " with value " + executeValues[0] + ".");
                registers.updateRegister(registerPositions[6], executeValues[0]);
                break;
            case LW:
                field.appendPlain("Writing to register at position " + registerPositions[6] + " with value " + memValue + ".");
                registers.updateRegister(registerPositions[6], memValue);
                break;
            case MFLO:
                field.appendPlain("Writing to register at position " + registerPositions[7] + " with value " + this.registers.getValue(33) + ".");
                registers.updateRegister(registerPositions[7], this.registers.getValue(33));
                break;

            case MUL:
                field.appendPlain("Writing to register at position 33(lo) with value " + executeValues[2] + ".");
                registers.updateRegister(33, executeValues[2]);
                field.appendPlain("Writing to register at position " + registerPositions[7] + " with value " + executeValues[2] + ".");
                registers.updateRegister(registerPositions[7], executeValues[2]);
                field.appendPlain("Writing to register at position 32(hi) with value " + executeValues[1] + ".");
                registers.updateRegister(32, executeValues[1]);
                break;
            
            case SYSCALL:
                if(this.registers.getValue(2) == 5)
                {
                    field.appendPlain("Writing to register at position 2 with value " + executeValues[3] + ".");
                    registers.updateRegister(2, executeValues[3]);
                    break;
                }
            default:
                field.appendPlain("Nothing is happening in write back stage.");
                break;
        }
    }

    private void incrementPC(Opcodes opcode, int [] executeValues, int [] registerValues)
    {
        field.appendBold("CHANGING PC");

        switch (opcode) 
        {
            case J:
            case JAL:
                int jumpTargetAddress = registerValues[4];
                field.appendPlain("Jump target address is : " + jumpTargetAddress + ".");
                PC = jumpTargetAddress;
                field.appendPlain("PC = " + PC);
                break;


            case JR:
                int value = registerValues[0];
                field.appendPlain("Jumping back to " + value);
                PC = value;
                field.appendPlain("PC = " + PC);
                break;

            
            case BNE:
            case BEQ:
                int branchAddress = (int)(PC) + (registerValues[3] << 2) + 4;
                field.appendPlain("Branch target address is : " + branchAddress + ".");

                if(executeValues[0] == 1)
                {
                    field.appendPlain("Branching to BTA");
                    PC = branchAddress;
                    field.appendPlain("PC = " + PC);
                    break;
                }
                else
                {
                    field.appendPlain("Not Branching to BTA");
                }
            
            default:
                field.appendPlain("PC = PC + 4");
                PC = PC + 4;
                field.appendPlain("PC = " + PC);
                break;
        }
    }

    private int getValue(DataAddress temp)
    {
        int ans = temp.getByte(3);
        ans <<= 8;
        ans += temp.getByte(2);
        ans <<= 8;
        ans += temp.getByte(1);
        ans <<= 8;
        ans += temp.getByte(0);

        return ans;
    }
}
