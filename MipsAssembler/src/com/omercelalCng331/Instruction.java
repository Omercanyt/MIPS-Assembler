package com.omercelalCng331;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Instruction {


    int InstructionType; //1  R-type, 2  I-type, 3 J-type
    String startingAddress="10000000000000000001000000000000";   // initial address

    public Instruction() throws IOException {
    }


    public static String binaryToHex(String binaryNumber){   ////
        return (String.format("%35X", Long.parseLong(binaryNumber,2)));
    }


    public int getInstructionType() {
        return InstructionType;
    }

    public void setInstructionType(int instructionType) {
        InstructionType = instructionType;
    }

    public int Menu() throws Exception{

        int selection = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to MIPS Assember Project !");
        System.out.println("1.Interactive Mode.");
        System.out.println("2.Batch Mode");
        System.out.println("3.Exit");
        System.out.println("Please Enter your choice: ");
        try {
            selection = scanner.nextInt();

        }catch(Exception error) {  // exception of error handled if the user enters wrong input
            System.out.println("Make a Valid Choice !!!");
            selection = Menu();
        }
        return selection;
    }



    public static void main(String[] args) throws Exception {

        Instruction assembler = new Instruction();
        File LookUpFile = new File("LookupTable.txt");

        String[] registerNames={};  /// im going to read the whole line seperately with this ,
        String[] instructionsLookup ={};
        String line;
        ArrayList<String[]> instructionListLookup = new ArrayList<>();  // and store these seperated datas in my array list
        ArrayList<String> instructionListInputSrc = new ArrayList<>();  // and store these seperated datas in my array list
        FileReader fileReaderLookup = new FileReader(LookUpFile);
        BufferedReader bufferedReaderLookUp = new BufferedReader(fileReaderLookup);

        String text = bufferedReaderLookUp.readLine();


        registerNames =text.split(",");
        /*
        for(int i =0;i<registerNames.length;i++){
            System.out.println(registerNames[i]);
        }

        String deneme = "$t5";

        int x=0;
        while(!deneme.equals(registerNames[x])){
            x++;
        }

        System.out.println(x);*/

        while((line=bufferedReaderLookUp.readLine()) != null){
            instructionsLookup=line.split(",");
            instructionListLookup.add(instructionsLookup);
        }
        bufferedReaderLookUp.close();

        int choice;
        choice =assembler.Menu();

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        if (choice==1){

            System.out.println("Enter an instruction ! :");
            Scanner scanner = new Scanner(System.in);
            String newInstruction=scanner.nextLine();
            String typeChecker = newInstruction.replace(" ",",");
            String[] typeChecker2 = typeChecker.split(",");

            Rtype r = new Rtype();
            Itype itype = new Itype();
            Jtype jtype = new Jtype();
            int index=-1;
            String type = null;
            if(typeChecker2[0].equals("add") || typeChecker2[0].equals("slt") || typeChecker2[0].equals("jr"))
                type="R";
            if(typeChecker2[0].equals("addi") || typeChecker2[0].equals("sw") || typeChecker2[0].equals("beq") || typeChecker2[0].equals("bne") || typeChecker2[0].equals("lw"))
                type="I";
            if(typeChecker2[0].equals("jal") || typeChecker2[0].equals("j"))
                type="J";
            if(typeChecker2[0].equals("jr"))
                type="JR";
            if(typeChecker2[0].equals("move"))
                type="mov";
            if(typeChecker2[0].equals("sll"))
                type="sll";
            if( typeChecker2[0].equals("slti"))
                type ="slti";


            //         sll $t1,$s1,2


            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            int x;


            if(type.equals("slti")){
                String[] newInstructionSplitted3 = {"name","rs","rt","im"};
                int flag=0;
                for(int a=0;a<typeChecker2.length;a++){
                    if(typeChecker2[a].length()!=0){
                        newInstructionSplitted3[flag]=typeChecker2[a];
                        flag++;
                    }
                }
                for(int i=0;i<instructionListLookup.size();i++){
                    if(newInstructionSplitted3[0].equals(instructionListLookup.get(i)[0])){ ///  WE CHECK THE LOOKUPTABLE TO IDENTIFY REGISTER ADDRESSES
                        index=index+1;      //// AND KEEP TRACK THE INDEX OF ITTT
                        break;
                    }
                    index=index+1;
                }

                x=0;
                while(!newInstructionSplitted3[1].equals(registerNames[x])){            //// AND HERE WE ASSIGNED THEM BY CHECKING
                    x++;
                }
                String rs = Integer.toBinaryString(x);

                x=0;
                while(!newInstructionSplitted3[2].equals(registerNames[x])){             //// AND HERE WE ASSIGNED THEM BY CHECKING
                    x++;
                }
                String rt = Integer.toBinaryString(x);

                int q=Integer.parseInt(newInstructionSplitted3[3]);

                String im = Integer.toBinaryString(q);
                itype.setOpCode(instructionListLookup.get(index)[2]);
                itype.setImmediate(im);
                itype.setRs(rt);
                itype.setRt(rs);
                System.out.println(itype.getFullItypeHex());

            }


            if(type.equals("sll")){
                String[] newInstructionSplittedsll = {"name","rd","rt","shiftamount"};
                r.setOpCode("000000");
                r.setRs("00000");
                r.setFuncCode("000000");
                String instrs = newInstruction.replace(" ",",");
                String[] instrctiondetails = typeChecker.split(",");
                x=0;
                while(!instrctiondetails[1].equals(registerNames[x])){
                    x++;
                }
                String rd = Integer.toBinaryString(x);
                r.setRd(rd);

                x=0;
                while(!instrctiondetails[2].equals(registerNames[x])){
                    x++;
                }
                String rt = Integer.toBinaryString(x);
                r.setRt(rt);
                System.out.println(r.getFullRtypeHex());

                int shamount = Integer.parseInt(instrctiondetails[3]);
                String shift = Integer.toBinaryString(shamount);
                r.setShiiftAmount(shift);

                System.out.println(r.getFullRtypeHex());

            }


            if(type.equals("mov")){
                String[] newInstructionSplitted = {"name","rs","rd"};

                String instrs = newInstruction.replace(" ",",");
                String[] instrctiondetails = typeChecker.split(",");

                x=0;
                while(!instrctiondetails[1].equals(registerNames[x])){
                    x++;
                }
                String rd = Integer.toBinaryString(x);
                r.setRd(rd);
                x=0;
                while(!instrctiondetails[2].equals(registerNames[x])){
                    x++;
                }
                String rs = Integer.toBinaryString(x);
                r.setRs(rs);

                r.setRd(rd);
                r.setOpCode("000000");
                r.setRt("00000");
                r.setShiiftAmount("00000");
                r.setFuncCode("100000");
                r.setRs(rs);
                System.out.println(r.getFullRtypeHex());



            }

            if (type.equals("JR")){
                String[] newInstructionSplitted = {"name","rs"};
                r.setOpCode("000000");
                r.setRt("00000");
                r.setRd("00000");
                r.setShiiftAmount("00000");
                r.setFuncCode("001000");
                String instrs = newInstruction.replace(" ",",");
                String[] instrctiondetails = typeChecker.split(",");
                x=0;
                while(!instrctiondetails[1].equals(registerNames[x])){
                    x++;
                }
                String rs = Integer.toBinaryString(x);
                r.setRs(rs);
                System.out.println(r.getFullRtypeHex());
            }

            if(type.equals("R")){

                String[] newInstructionSplitted = {"name","rs","rd","rt"};
                int flag=0;
                for(int a=0;a<typeChecker2.length;a++){
                    if(typeChecker2[a].length()!=0){
                        newInstructionSplitted[flag]=typeChecker2[a];   //// WE CHECKED THE TYPE TO GET OPCODE, SHAMT, AND FUNC CODE FROM LOOKUP TABLE
                        flag++;
                    }
                }

                for(int i=0;i<instructionListLookup.size();i++){
                    if(newInstructionSplitted[0].equals(instructionListLookup.get(i)[0])){//// WE CHECKED TOOOK THE ALL INSTRUCTION AND INDEX
                        index=index+1;
                        break;
                    }
                    index=index+1;
                }

                //dst
                r.setOpCode(instructionListLookup.get(index)[2]);
                r.setShiiftAmount(instructionListLookup.get(index)[3]);         /// SET THE PPCODE, SHAMT, AND FUNC CODE FROM LOOKUP TABLE
                r.setFuncCode(instructionListLookup.get(index)[4]);
                
                x=0;
                while(!newInstructionSplitted[1].equals(registerNames[x])){
                    x++;
                }

                String rd = Integer.toBinaryString(x);

                x=0;
                while(!newInstructionSplitted[2].equals(registerNames[x])){
                    x++;
                }

                String rs = Integer.toBinaryString(x);                                     /// SET THE REGISTER ADDRESSES FROM LOOKUP TABLE

                x=0;
                while(!newInstructionSplitted[3].equals(registerNames[x])){
                    x++;
                }

                String rt = Integer.toBinaryString(x);

                //add $s0 $s1 $s2

                r.setRd(rd);
                r.setRs(rs);                                                /// SET THE REGISTER ADDRESSES FROM LOOKUP TABLE
                r.setRt(rt);
                System.out.println("Hexadecimal value is : "+r.getFullRtypeHex());
            }
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            if(type.equals("I")){

                if(typeChecker2[0].equals("lw")||typeChecker2[0].equals("sw")){
                    String[] newInstructionSplitted2 = {"name","rt","im(rs)"};              /// DIIFFERENT IMPLEMENTATION FOR SW AND LW
                    int flag=0;
                    for(int a=0;a<typeChecker2.length;a++){
                        if(typeChecker2[a].length()!=0){
                            newInstructionSplitted2[flag]=typeChecker2[a];
                            flag++;
                        }
                    }
                    for(int i=0;i<instructionListLookup.size();i++){
                        if(newInstructionSplitted2[0].equals(instructionListLookup.get(i)[0])){
                            index=index+1;
                            break;
                        }
                        index=index+1;
                    }



                    String str = newInstructionSplitted2[2];
                    String rsa = str.substring(str.indexOf("(")+1,str.indexOf(")"));
                    String immidiate = str.substring(0,str.indexOf("("));

                    int q=Integer.parseInt(immidiate);

                    String im = Integer.toBinaryString(q);


                    x=0;
                    while(!rsa.equals(registerNames[x])){
                        x++;
                    }
//lw $t0, 32($s3)
                    String rs = Integer.toBinaryString(x);

                    x=0;
                    while(!newInstructionSplitted2[1].equals(registerNames[x])){
                        x++;
                    }
                    String rt = Integer.toBinaryString(x);


                    itype.setOpCode(instructionListLookup.get(index)[2]);
                    itype.setImmediate(im);
                    itype.setRs(rs);
                    itype.setRt(rt);

                    System.out.println(itype.getFullItypeHex());


                }




                else{
                    String[] newInstructionSplitted3 = {"name","rs","rt","im"};
                    int flag=0;
                    for(int a=0;a<typeChecker2.length;a++){
                        if(typeChecker2[a].length()!=0){
                            newInstructionSplitted3[flag]=typeChecker2[a];
                            flag++;
                        }
                    }
                    for(int i=0;i<instructionListLookup.size();i++){
                        if(newInstructionSplitted3[0].equals(instructionListLookup.get(i)[0])){
                            index=index+1;
                            break;
                        }
                        index=index+1;
                    }

                    x=0;
                    while(!newInstructionSplitted3[1].equals(registerNames[x])){
                        x++;
                    }
                    String rs = Integer.toBinaryString(x);

                    x=0;
                    while(!newInstructionSplitted3[2].equals(registerNames[x])){
                        x++;
                    }
                    String rt = Integer.toBinaryString(x);

                    int q=Integer.parseInt(newInstructionSplitted3[3]);

                    String im = Integer.toBinaryString(q);
                    itype.setOpCode(instructionListLookup.get(index)[2]);
                    itype.setImmediate(im);

                    if(newInstructionSplitted3[0].charAt(0)=='b') {
                        itype.setRs(rs);
                        itype.setRt(rt);
                    }
                    else {
                        itype.setRs(rt);
                        itype.setRt(rs);
                    }

                    System.out.println(itype.getFullItypeHex());
//bne $s1, $s2, 3
                }


            }
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            if(type.equals("J")){

                String[] newInstructionSplitted4 = {"name","address"};
                int flag=0;
                for(int a=0;a<typeChecker2.length;a++){
                    if(typeChecker2[a].length()!=0){
                        newInstructionSplitted4[flag]=typeChecker2[a];
                        flag++;
                    }
                }
                for(int i=0;i<instructionListLookup.size();i++){
                    if(newInstructionSplitted4[0].equals(instructionListLookup.get(i)[0])){
                        index=index+1;
                        break;
                    }
                    index=index+1;
                }

                int q2 = Integer.parseInt(newInstructionSplitted4[1]);
                String address = Integer.toBinaryString(q2);
                jtype.setAddressJtype(address);
                jtype.setOpCode(instructionListLookup.get(index)[2]);
                System.out.println(jtype.getFullJtypeHex());

            }

        }


 /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        if (choice==2){

            String word[]={};
            String instructionsInputSrc;
            File AssemblerInputFile = new File("AssemblerInput.src");
            FileReader fileReaderAssemblerInput = new FileReader(AssemblerInputFile);
            BufferedReader bufferedReaderInputSrc = new BufferedReader(fileReaderAssemblerInput);
            while((line=bufferedReaderInputSrc.readLine()) != null){
                instructionsInputSrc=line;
                instructionListInputSrc.add(instructionsInputSrc);          //// PUTTING THEM ALL TO WORK ON IT IN ARRAYLIST OF INSTRUCTIONS BY READING IT
            }
            bufferedReaderInputSrc.close();

            ArrayList<String > labels = new ArrayList<>();
            for(int i=0;i<instructionListInputSrc.size();i++){


                word = instructionListInputSrc.get(i).split("\\s",2);    //// DELETING THE SPACES OF THE INSTRUCTIONSS

                if(word[0].trim() != null){
                    labels.add(word[0]);                //// PUTTING THEM IN CORRECT FORMAT TO WORK ON IT IN ARRAYLIST OF LABELS
                }
            }

            ArrayList<Labels> lableList = new ArrayList<>();
            for(int i=0;i<labels.size();i++){
                if(labels.get(i).length()!=0) {
                    Labels l1 = new Labels();
                    //System.out.println(labels.get(i));                /// SEPERATING THE LABELS FROM INSTRUCTIONS AND THEIR ADDRESSESS
                    l1.setLabelName(labels.get(i));
                    //System.out.println("Offset of this label is : "+i);
                    l1.setLabelAddress(i);
                    lableList.add(l1);

                }
            }



            ArrayList<String > onlyInstructions = new ArrayList<>();
            for(int i=0;i<instructionListInputSrc.size();i++){
                word = instructionListInputSrc.get(i).split("\\s",2);           /// SEPERATING THE ONLY INSTRUCTIONS WITHOUT LABELSS
                if(word[0].length() != 0){
                    String inst = instructionListInputSrc.get(i).substring(instructionListInputSrc.get(i).indexOf(":")+1);
                    onlyInstructions.add(inst);
                }
                else {
                    onlyInstructions.add(instructionListInputSrc.get(i).stripLeading());
                }
            }

           /*for (int i=0;i<onlyInstructions.size();i++){
                System.out.println(onlyInstructions.get(i));
            } //TO SEE INSTRUCTIONS*/

            ArrayList<String > finalResults = new ArrayList<>();            //// THIS PART IS TO WRITE O THE OBJECT FILE AFTER

            int counterwhile=0;
            while(counterwhile!= onlyInstructions.size()){
                onlyInstructions.get(counterwhile);
                String typeChecker = onlyInstructions.get(counterwhile).replace(" ",",");       /// REPLACE SPACES WITH COMMAS
                String[] typeChecker2 = typeChecker.split(",");


                Rtype r = new Rtype();
                Itype itype = new Itype();
                Jtype jtype = new Jtype();
                int index=-1;
                String type = null;
                if(typeChecker2[0].equals("add") || typeChecker2[0].equals("slt") || typeChecker2[0].equals("sll") || typeChecker2[0].equals("jr"))
                    type="R";
                if(typeChecker2[0].equals("addi") || typeChecker2[0].equals("sw") || typeChecker2[0].equals("beq") || typeChecker2[0].equals("bne") || typeChecker2[0].equals("lw"))
                    type="I";
                if(typeChecker2[0].equals("jal") || typeChecker2[0].equals("j"))
                    type="J";
                if(typeChecker2[0].equals("jr"))
                    type="JR";

                if(typeChecker2[0].equals("move"))
                    type="mov";

                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                if(typeChecker2[0].equals("sll"))
                    type="sll";
                if( typeChecker2[0].equals("slti"))
                    type ="slti";


                //         sll $t1,$s1,2


                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                int x;


                if(type.equals("slti")){

                    String[] newInstructionSplitted3 = {"name","rs","rt","im"};
                    int flag=0;
                    for(int a=0;a<typeChecker2.length;a++){
                        if(typeChecker2[a].length()!=0){
                            newInstructionSplitted3[flag]=typeChecker2[a];
                            flag++;
                        }
                    }
                    for(int i=0;i<instructionListLookup.size();i++){
                        if(newInstructionSplitted3[0].equals(instructionListLookup.get(i)[0])){
                            index=index+1;
                            break;
                        }
                        index=index+1;
                    }

                    x=0;
                    while(!newInstructionSplitted3[1].equals(registerNames[x])){
                        x++;
                    }
                    String rs = Integer.toBinaryString(x);

                    x=0;
                    while(!newInstructionSplitted3[2].equals(registerNames[x])){
                        x++;
                    }
                    String rt = Integer.toBinaryString(x);

                    String im = null;
                    int matchFlag=0;
                    int addressDifference=0;


                    for(int i=0;i<lableList.size();i++){
                        if(newInstructionSplitted3[3].equals(lableList.get(i).getLabelName())){
                            matchFlag++;
                            addressDifference=lableList.get(i).getLabelAddress()-counterwhile;
                            im = Integer.toBinaryString(addressDifference-1);

                            //System.out.println("BULUNDUGUM INSTRUCTION :::::::::::::::::::::::::    "+counterwhile);
                            //System.out.println("GIDECEGIMMM INSTRUCTION :::::::::::::::::::::::::    "+lableList.get(i).getLabelAddress());
                        }
                    }
                    if(matchFlag==0) {
                        int q=Integer.parseInt(newInstructionSplitted3[3]);
                        im = Integer.toBinaryString(q);
                    }
                    itype.setOpCode(instructionListLookup.get(index)[2]);
                    itype.setImmediate(im);
                    itype.setRs(rt);
                    itype.setRt(rs);
                    finalResults.add(itype.getFullItypeHex());
                    //System.out.println(itype.getFullItypeHex());


                }


                if(type.equals("sll")){
                    String[] newInstructionSplittedsll = {"name","rd","rt","shiftamount"};
                    r.setOpCode("000000");
                    r.setRs("00000");
                    r.setFuncCode("000000");
                    String instrs = onlyInstructions.get(counterwhile).replace(" ",",");
                    String[] instrctiondetails = typeChecker.split(",");
                    x=0;
                    while(!instrctiondetails[1].equals(registerNames[x])){
                        x++;
                    }
                    String rd = Integer.toBinaryString(x);
                    r.setRd(rd);

                    x=0;
                    while(!instrctiondetails[2].equals(registerNames[x])){
                        x++;
                    }
                    String rt = Integer.toBinaryString(x);
                    r.setRt(rt);
                    //System.out.println(r.getFullRtypeHex());

                    int shamount = Integer.parseInt(instrctiondetails[3]);
                    String shift = Integer.toBinaryString(shamount);
                    r.setShiiftAmount(shift);

                    finalResults.add(r.getFullRtypeHex());
                    //System.out.println(r.getFullRtypeHex());

                }
                if(type.equals("mov")){
                    String[] newInstructionSplitted = {"name","rd","rs"};
                    r.setOpCode("000000");
                    r.setRt("00000");
                    r.setShiiftAmount("00000");
                    r.setFuncCode("100000");
                    String instrs = onlyInstructions.get(counterwhile).replace(" ",",");
                    String[] instrctiondetails = typeChecker.split(",");

                    x=0;
                    while(!instrctiondetails[1].equals(registerNames[x])){
                        x++;
                    }
                    String rd = Integer.toBinaryString(x);
                    r.setRd(rd);
                    x=0;
                    while(!instrctiondetails[2].equals(registerNames[x])){
                        x++;
                    }
                    String rs = Integer.toBinaryString(x);
                    r.setRs(rs);
                    finalResults.add(r.getFullRtypeHex());
                    //System.out.println(r.getFullRtypeHex());

                }

                if (type.equals("JR")){
                    String[] newInstructionSplitted = {"name","rs"};
                    r.setOpCode("000000");
                    r.setRt("00000");
                    r.setRd("00000");
                    r.setShiiftAmount("00000");
                    r.setFuncCode("001000");
                    String instrs = onlyInstructions.get(counterwhile).replace(" ",",");
                    String[] instrctiondetails = typeChecker.split(",");
                    x=0;
                    while(!instrctiondetails[1].equals(registerNames[x])){
                        x++;
                    }
                    String rs = Integer.toBinaryString(x);
                    r.setRs(rs);
                    finalResults.add(r.getFullRtypeHex());
                    //System.out.println(r.getFullRtypeHex());
                }

                if(type.equals("R")){

                    String[] newInstructionSplitted = {"name","rs","rd","rt"};
                    int flag=0;
                    for(int a=0;a<typeChecker2.length;a++){
                        if(typeChecker2[a].length()!=0){
                            newInstructionSplitted[flag]=typeChecker2[a];
                            flag++;
                        }
                    }

                    for(int i=0;i<instructionListLookup.size();i++){
                        if(newInstructionSplitted[0].equals(instructionListLookup.get(i)[0])){
                            index=index+1;
                            break;
                        }
                        index=index+1;
                    }

                    //dst
                    r.setOpCode(instructionListLookup.get(index)[2]);
                    r.setShiiftAmount(instructionListLookup.get(index)[3]);
                    r.setFuncCode(instructionListLookup.get(index)[4]);

                    x=0;
                    while(!newInstructionSplitted[1].equals(registerNames[x])){
                        x++;
                    }

                    String rd = Integer.toBinaryString(x);

                    x=0;
                    while(!newInstructionSplitted[2].equals(registerNames[x])){
                        x++;
                    }

                    String rs = Integer.toBinaryString(x);

                    x=0;
                    while(!newInstructionSplitted[3].equals(registerNames[x])){
                        x++;
                    }

                    String rt = Integer.toBinaryString(x);

                    //add $s0 $s1 $s2

                    r.setRd(rd);
                    r.setRs(rs);
                    r.setRt(rt);
                    finalResults.add(r.getFullRtypeHex());
                    //System.out.println("Hexadecimal value is : "+r.getFullRtypeHex());
                }
                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                if(type.equals("I")){
                    //addi $s1, $s2, 3
                    if(typeChecker2[0].equals("lw")||typeChecker2[0].equals("sw")){
                        String[] newInstructionSplitted2 = {"name","rt","im(rs)"};
                        int flag=0;
                        for(int a=0;a<typeChecker2.length;a++){
                            if(typeChecker2[a].length()!=0){
                                newInstructionSplitted2[flag]=typeChecker2[a];
                                flag++;
                            }
                        }
                        for(int i=0;i<instructionListLookup.size();i++){
                            if(newInstructionSplitted2[0].equals(instructionListLookup.get(i)[0])){
                                index=index+1;
                                break;
                            }
                            index=index+1;
                        }



                        String str = newInstructionSplitted2[2];
                        String rsa = str.substring(str.indexOf("(")+1,str.indexOf(")"));
                        String immidiate = str.substring(0,str.indexOf("("));

                        int q=Integer.parseInt(immidiate);

                        String im = Integer.toBinaryString(q);


                        x=0;
                        while(!rsa.equals(registerNames[x])){
                            x++;
                        }
//lw $t0, 32($s3)
                        String rs = Integer.toBinaryString(x);

                        x=0;
                        while(!newInstructionSplitted2[1].equals(registerNames[x])){
                            x++;
                        }
                        String rt = Integer.toBinaryString(x);


                        itype.setOpCode(instructionListLookup.get(index)[2]);
                        itype.setImmediate(im);
                        itype.setRs(rs);
                        itype.setRt(rt);
                        finalResults.add(itype.getFullItypeHex());
                        //System.out.println(itype.getFullItypeHex());


                    }



                    else{
                        String[] newInstructionSplitted3 = {"name","rs","rt","im"};
                        int flag=0;
                        for(int a=0;a<typeChecker2.length;a++){
                            if(typeChecker2[a].length()!=0){
                                newInstructionSplitted3[flag]=typeChecker2[a];
                                flag++;
                            }
                        }
                        for(int i=0;i<instructionListLookup.size();i++){
                            if(newInstructionSplitted3[0].equals(instructionListLookup.get(i)[0])){
                                index=index+1;
                                break;
                            }
                            index=index+1;
                        }

                        x=0;
                        while(!newInstructionSplitted3[1].equals(registerNames[x])){
                            x++;
                        }
                        String rs = Integer.toBinaryString(x);

                        x=0;
                        while(!newInstructionSplitted3[2].equals(registerNames[x])){
                            x++;
                        }
                        String rt = Integer.toBinaryString(x);

                        String im = null;
                        int matchFlag=0;
                        int addressDifference=0;


                        for(int i=0;i<lableList.size();i++){
                            if(newInstructionSplitted3[3].equals(lableList.get(i).getLabelName())){
                                 matchFlag++;
                                 addressDifference=lableList.get(i).getLabelAddress()-counterwhile;
                                 im = Integer.toBinaryString(addressDifference-1);

                                 //System.out.println("BULUNDUGUM INSTRUCTION :::::::::::::::::::::::::    "+counterwhile);
                                 //System.out.println("GIDECEGIMMM INSTRUCTION :::::::::::::::::::::::::    "+lableList.get(i).getLabelAddress());
                            }
                        }
                        if(matchFlag==0) {
                            int q=Integer.parseInt(newInstructionSplitted3[3]);
                            im = Integer.toBinaryString(q);
                        }
                        itype.setOpCode(instructionListLookup.get(index)[2]);
                        itype.setImmediate(im);
                        if(newInstructionSplitted3[0].charAt(0)=='b') {
                            itype.setRs(rs);
                            itype.setRt(rt);
                        }
                        else {
                            itype.setRs(rt);
                            itype.setRt(rs);
                        }
                        finalResults.add(itype.getFullItypeHex());
                        //System.out.println(itype.getFullItypeHex());

                    }


                }
                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                if(type.equals("J")){
                    String[] newInstructionSplitted4 = {"name","address"};
                    int flag=0;
                    for(int a=0;a<typeChecker2.length;a++){
                        if(typeChecker2[a].length()!=0){
                            newInstructionSplitted4[flag]=typeChecker2[a];
                            flag++;
                        }
                    }
                    for(int i=0;i<instructionListLookup.size();i++){
                        if(newInstructionSplitted4[0].equals(instructionListLookup.get(i)[0])){
                            index=index+1;
                            break;
                        }
                        index=index+1;
                    }

                    String address=null;

                    int matchFlag=0;
                    for(int i=0;i<lableList.size();i++){
                        if(newInstructionSplitted4[1].equals(lableList.get(i).getLabelName())){
                            address = Integer.toBinaryString(lableList.get(i).getLabelAddress());
                            matchFlag++;
                        }
                    }
                    jtype.setAddressJtype(address);
                    jtype.setOpCode(instructionListLookup.get(index)[2]);
                    finalResults.add(jtype.getFullJtypeHex());
                   // System.out.println(jtype.getFullJtypeHex());

                }


                counterwhile++;

            } ///////////////////////////////WWWWWWWWWWWWWWWWWWWWWWWWİİİİİİİİİİİLEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE

            for(int i=0;i<finalResults.size();i++){
                System.out.println(finalResults.get(i));

            }

            File outputFile = new File("OutputSrc.obj");
            if(!outputFile.exists())
                outputFile.createNewFile();

            FileWriter fileWriter = new FileWriter(outputFile,false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for(int i=0;i<finalResults.size();i++){
                bufferedWriter.write(finalResults.get(i));
                bufferedWriter.write("\n");
            }

            bufferedWriter.close();



        }

        }

    }


class Labels {
    String labelName;
    int labelAddress;

    public Labels(){}
    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public int getLabelAddress() {
        return labelAddress;
    }
    public void setLabelAddress(int labelAddress) {
        this.labelAddress = labelAddress;
    }

}

class Rtype extends Instruction{
    String opCode; // opcode for instruction types (6 bits)
    String rs; // register containing base address (5 bits)
    String rt; // register destination/source (5 bits)
    String rd; // register destination (5 bits)
    String funcCode; // function code (identifies the specific R-format instruction) (6 bits)
    String shiiftAmount="00000"; //shamt: shift amount (0 when N/A) (5 bits)
    String FullRtypeHex;

    public Rtype() throws IOException {
        super();
    }

    public String getFullRtypeHex() {
        String adder = this.opCode+this.getRs()+this.rt+this.rd+this.shiiftAmount+this.funcCode;
        String result=binaryToHex(adder).trim();
        while(result.length()<8){
            result = '0'+result;
        }
        return ("0x"+result.trim());
    }

    public String getOpCode() {
        return opCode;
    }

    public void setOpCode(String opCode) {

        this.opCode = opCode;
    }

    public String getRs() {
        return rs;
    }

    public void setRs(String rs) {
        while(rs.length()<5){
            rs ="0"+rs;
        }
        this.rs = rs;
    }

    public String getRt() {
        return rt;
    }

    public void setRt(String rt) {
        while(rt.length()<5){
            rt ="0"+rt;
        }
        this.rt = rt;
    }

    public String getRd() {
        return rd;
    }

    public void setRd(String rd) {
        while(rd.length()<5){
            rd ="0"+rd;
        }
        this.rd = rd;
    }

    public String getFuncCode() {
        return funcCode;
    }

    public void setFuncCode(String funcCode) {
        this.funcCode = funcCode;
    }

    public String getShiiftAmount() {
        return shiiftAmount;
    }

    public void setShiiftAmount(String shiiftAmount) {
        while(shiiftAmount.length()<5){
            shiiftAmount ="0"+shiiftAmount;
        }
        this.shiiftAmount = shiiftAmount;
    }
}

class Itype extends Instruction{
    String opCode; // opcode for instruction types (6 bits)
    String rs; // register containing base address (5 bits)
    String rt; // register destination/source (5 bits)
    String immediate; // value or offset (16 bits)
    String FullItypeHex;

    public Itype() throws IOException {
        super();
    }

    public String getFullItypeHex() {
        String adder = this.opCode+this.rs+this.rt+this.immediate;
        return ("0x"+binaryToHex(adder).trim());
    }

    public String getOpCode() {
        return opCode;
    }

    public void setOpCode(String opCode) {
        this.opCode = opCode;
    }
    public String getRs() {
        return rs;
    }

    public void setRs(String rs) {
        while(rs.length()<5){
            rs ="0"+rs;
        }
        this.rs = rs;
    }

    public String getRt() {
        return rt;
    }

    public void setRt(String rt) {
        while(rt.length()<5){
            rt ="0"+rt;
        }
        this.rt = rt;
    }

    public String getImmediate() {
        return immediate;
    }

    public void setImmediate(String immediate) {
        while(immediate.length()>16){
            immediate=immediate.substring(1);
        }
        while(immediate.length()<16){
            immediate ="0"+immediate;
        }
        this.immediate = immediate;
    }
}

class Jtype extends Instruction{

    String opCode; // opcode for instruction types (6 bits)
    String addressJtype; //  address (26 bits)
    String FullJtypeHex;

    public Jtype() throws IOException {
        super();
    }

    public String getFullJtypeHex() {
        String adder = this.opCode+this.addressJtype;
        return ("0x0"+binaryToHex(adder).trim());
    }

    public String getOpCode() {
        return opCode;
    }

    public void setOpCode(String opCode) {
        this.opCode = opCode;
    }

    public String getAddressJtype() {
        return addressJtype;
    }

    public void setAddressJtype(String addressJtype) {
        while(addressJtype.length()<26){
            addressJtype ="0"+addressJtype;
        }
        this.addressJtype = addressJtype;
    }
}
