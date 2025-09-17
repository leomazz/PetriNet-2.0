/**
 * TranslateController.java
 */
package tcc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.ListIterator;

public class TranslateController {
    public static void main(String args[]) {
        try {
            /************************************************************************/
                        /*					PARA O FOMATO DA IEC61131.
                        /************************************************************************/
            File f = new File("C:\\Java\\TCC\\demo3_IEC61131.txt"); // Em casa deixar esta linha.
            // File f = new File("C:\\Documents and Settings\\Administrator\\Desktop\\Victor\\saida_IEC.txt"); // No lab deixar esta.
            FileWriter fw = new FileWriter(f);
            PrintWriter pw = new PrintWriter(fw);
            // tratamento do arquivo de entrada.
            XMLProcessor xmlp = new XMLProcessor("C:\\Java\\TCC\\demo3.xml"); // Em casa deixar esta linha.
            // XMLProcessor xmlp = new XMLProcessor("C:\\Documents and Settings\\Administrator\\Desktop\\Victor\\V105_IEC.xml"); // No lab deixar esta.
            // obtenção dos lugares, transições e sinais de entrada e de saída.
            List conditionList = xmlp.XMLInterpreter("place");
            List eventList = xmlp.XMLInterpreter("transition");
            List iosList = xmlp.XMLInterpreter("signals");
            ListIterator itr;
            /************************************************************************/
            // declaração das variáveis associadas a cada lugar do modelo em sipn.
            itr = conditionList.listIterator();
            pw.println("PROGRAM Main"+"\n");
            pw.println("VAR"+"\n");
            while(itr.hasNext()) {
                Condition c = (Condition)itr.next();
                String status = c.getInitialMarking() == true ? "TRUE" : "FALSE";
                pw.println("\t\t"+c.getId()+" : BOOL := "+status+";");
            }
            pw.println("\t\tstab : BOOL := FALSE;"+"\n");
            pw.println("END_VAR"+"\n");
            /************************************************************************/
            // declaração das variáveis associadas aos sinais de entrada e saída
            itr = iosList.listIterator();
            pw.println("VAR_GLOBAL"+"\n");
            int i, j, k, w;
            i = j = k = w = 0;
            while(itr.hasNext()) {
                IOSignals ios = (IOSignals)itr.next();
                if(ios.getType().equals("input")) {
                    pw.println("\t\t"+ios.getDesc()+"\t\t"+"at"+"\t\t"+"%IX"+i+"."+j+":"+"\t\t"+"BOOL;");
                    j++;
                    if(j == 9) {
                        j = 0; i++; }
                } else {
                    pw.println("\t\t"+ios.getDesc()+"\t\t"+"at"+"\t\t"+"%QX"+k+"."+w+":"+"\t\t"+"BOOL;");
                    w++;
                    if(w == 9) {
                        w = 0; k++; }
                }
            }// fecha while.
            pw.println("\n"+"END_VAR"+"\n");
            /************************************************************************/
            // código em IL para a variável de estabilidade.
            pw.println("(****** Reset stability variable ******)");
            pw.println("l_0:"+"\t"+"R"+"\t\t"+"stab"+"\n");
            /************************************************************************/
            // código em IL para as transições.
            itr = eventList.listIterator();
            int count = 1;
            while(itr.hasNext()) {
                Event e = (Event)itr.next();
                pw.printf("(****** Transition %s ******)\n", e.getId());
                pw.printf("l_%d:", count);
                ListIterator itr2 = e.getPreConditionList().listIterator();
                // verifica se a transição está habilitada.
                while(itr2.hasNext()) {
                    Condition c = (Condition)itr2.next();
                    pw.println("\t"+"LD"+"\t\t"+c.getId());
                }
                itr2 = e.getPosConditionList().listIterator();
                while(itr2.hasNext()) {
                    Condition c = (Condition)itr2.next();
                    pw.println("\t\t"+"ANDN"+"\t"+c.getId());
                }
                // se a transição não estiver habilitada, passa para o próximo rótulo no programa em IL.
                int countAux = count + 1;
                pw.println("\t\t"+"JMPCN"+"\t"+"l_"+countAux);
                // verifica se a condição de disparo da transição está satisfeita.
                itr2 = e.getInputSignalsList().listIterator();
                ListIterator itr3 = e.getOperatorsList().listIterator();
                while(itr2.hasNext()) {
                    String signal = (String)itr2.next();
                    String operator = (String)itr3.next();
                    pw.println("\t\t"+operator+"\t\t"+signal);
                }
                // se a condição de disparo não estiver satisfeita, passa para o próximo rótulo no programa em IL.
                pw.println("\t\t"+"JMPCN"+"\t"+"l_"+countAux);
                // atualiza a marcação da RdP.
                itr2 = e.getPreConditionList().listIterator();
                while(itr2.hasNext()) {
                    // retira a marca dos pré-lugares.
                    Condition c = (Condition)itr2.next();
                    pw.println("\t\t"+"R"+"\t\t"+c.getId());
                }
                itr2 = e.getPosConditionList().listIterator();
                while(itr2.hasNext()) {
                    // coloca a marca nos pós-lugares.
                    Condition c = (Condition)itr2.next();
                    pw.println("\t\t"+"S"+"\t\t"+c.getId());
                }
                
                // se a transição disparou, seta a variável de estabilidade.
                pw.println("\t\t"+"S"+"\t\t"+"stab"+"\n");
                count++;
            }
            /************************************************************************/
            // verifica se  variável de estabilidade foi resetada.
            pw.println("(****** Check stability variable ******)");
            pw.printf("l_%d:", count);
            pw.println("\t"+"LD"+"\t\t"+"stab");
            pw.println("\t\t"+"JMPCN"+"\t"+"l_0"+"\n");
            /************************************************************************/
            // código em IL para os lugares.
            itr = conditionList.listIterator();
            count++;
            while(itr.hasNext()) {
                Condition c = (Condition)itr.next();
                pw.printf("(****** Place %s ******)\n", c.getId());
                pw.printf("l_%d:", count);
                pw.println("\t"+"LD"+"\t\t"+c.getId());
                int countAux = count + 1;
                pw.println("\t\t"+"JMPCN"+"\t"+"l_"+countAux);
                ListIterator itr2 = c.getOutputSignalsList().listIterator();
                ListIterator itr3 = c.getOperatorsList().listIterator();
                while(itr2.hasNext()) {
                    String s1 = (String)itr2.next();
                    String s2 = (String)itr3.next();
                    pw.println("\t\t"+s2+"\t\t"+s1);
                }
                count++;
                pw.println();
            }
            /************************************************************************/
            // último rótulo programa. Instrução de retorno: RET.
            pw.printf("l_%d:", count);
            pw.println("\t"+"RET"+"\n");
            pw.println("END_PROGRAM");
            /************************************************************************/
            pw.close();
            fw.close();
            
            /************************************************************************/
                        /*					PARA O FOMATO DA SIEMENS: STEP7.
                        /************************************************************************/
            f = new File("C:\\Java\\TCC\\demo3_STEP7.txt"); // Em casa deixar esta linha.
            // f = new File("C:\\Documents and Settings\\Administrator\\Desktop\\Victor\\V105_STEP7.txt");
            fw = new FileWriter(f);
            pw = new PrintWriter(fw);
            // tratamento do arquivo de entrada.
            // xmlp = new XMLProcessor("C:\\Documents and Settings\\Administrator\\Desktop\\Victor\\V105_STEP7.xml");
            xmlp = new XMLProcessor("C:\\Java\\TCC\\demo3.xml"); // em casa deixar esta
            // obtenção dos lugares, transições e sinais de entrada e de saída.
            conditionList = xmlp.XMLInterpreter("place");
            eventList = xmlp.XMLInterpreter("transition");
            iosList = xmlp.XMLInterpreter("signals");
            /************************************************************************/
            // cabeçalho do function block.
            pw.println(
                    "ORGANIZATION_BLOCK \"Cycle Execution\"" + "\n" +
                    "TITLE = \"Main Program Sweep (Cycle)\"" + "\n" +
                    "VERSION : "
                    );
            pw.println();
            /************************************************************************/
            // declaração das variáveis.
            pw.println("VAR_TEMP");
            pw.println(
                    "\t" + "OB1_EV_CLASS : BYTE ; //Bits 0-3 = 1 (Coming event), Bits 4-7 = 1 (Event class 1)" + "\n" +
                    "\t" + "OB1_SCAN_1 : BYTE ; //1 (Cold restart scan 1 of OB 1), 3 (Scan 2-n of OB 1)" + "\n" +
                    "\t" + "OB1_PRIORITY : BYTE ; //Priority of OB Execution" + "\n" +
                    "\t" + "OB1_OB_NUMBR : BYTE ; //1 (Organization block 1, OB1)" + "\n" +
                    "\t" + "OB1_RESERVED_1 : BYTE ; //Reserved for system" + "\n" +
                    "\t" + "OB1_RESERVED_2 : BYTE ; //Reserved for system" + "\n" +
                    "\t" + "OB1_PREV_CYCLE : INT ; //Cycle time of previous OB1 scan (milliseconds)" + "\n" +
                    "\t" + "OB1_MIN_CYCLE : INT ; //Minimum cycle time of OB1 (milliseconds)" + "\n" +
                    "\t" + "OB1_MAX_CYCLE : INT ; //Maximum cycle time of OB1 (milliseconds)" + "\n" +
                    "\t" +"OB1_DATE_TIME : DATE_AND_TIME ;   //Date and time OB1 started"
                    );
            // declaracao das variáveis do programa gerado a partir da rede de Petri.
            pw.println("\t"+"// variáveis do programa gerado a partir da rede de Petri.");
            itr = conditionList.listIterator();
            while(itr.hasNext()) {
                Condition c = (Condition)itr.next();
                pw.println("\t"+c.getId()+":"+"BOOL;");
            }
            pw.println("\t"+"stab:BOOL;");
            pw.println("END_VAR");
            pw.println();
            /************************************************************************/
            // começo das instruções do programa.
            pw.println("BEGIN");
            pw.println();
            pw.println("NETWORK");
            pw.println("TITLE =");
            pw.println("// (Re)inicializa rede de Petri");
            pw.println("\t"+"A"+"\t"+"I12.3;");
            itr = conditionList.listIterator();
            while(itr.hasNext()) {
                Condition c = (Condition)itr.next();
                String operator = c.getInitialMarking() == true ? "S" : "R";
                pw.println("\t"+operator+"\t"+"#"+c.getId()+";");
            }
            pw.println("NETWORK");
            pw.println("TITLE =");
            pw.println("L001:"+"\t"+"R"+"\t"+"#stab;");
            pw.println();
            // código em IL para as transições.
            itr = eventList.listIterator();
            while(itr.hasNext()) {
                Event e = (Event)itr.next();
                pw.println("NETWORK");
                pw.println("TITLE =");
                pw.println("// Transition "+e.getId());
                ListIterator itr2 = e.getPreConditionList().listIterator();
                // verifica se a transição está habilitada.
                while(itr2.hasNext()) {
                    Condition c = (Condition)itr2.next();
                    pw.println("\t"+"A"+"\t"+"#"+c.getId()+";");
                }
                itr2 = e.getPosConditionList().listIterator();
                while(itr2.hasNext()) {
                    Condition c = (Condition)itr2.next();
                    pw.println("\t"+"AN"+"\t"+"#"+c.getId()+";");
                }
                // verifica se a condição de disparo da transição está satisfeita.
                itr2 = e.getInputSignalsList().listIterator();
                ListIterator itr3 = e.getOperatorsList().listIterator();
                i = 0;
                while(itr2.hasNext()) {
                    String signal = (String)itr2.next();
                    String operator = (String)itr3.next();
                    pw.println("\t"+operator+"\t"+signal+";"+"\t//"+e.getFromCommentsList(i));
                    i++;
                }
                // atualiza a marcação da RdP.
                itr2 = e.getPreConditionList().listIterator();
                while(itr2.hasNext()) {
                    // retira a marca dos pré-lugares.
                    Condition c = (Condition)itr2.next();
                    pw.println("\t"+"R"+"\t"+"#"+c.getId()+";");
                }
                itr2 = e.getPosConditionList().listIterator();
                while(itr2.hasNext()) {
                    // coloca a marca nos pós-lugares.
                    Condition c = (Condition)itr2.next();
                    pw.println("\t"+"S"+"\t"+"#"+c.getId()+";");
                }
                // se a transição disparou, seta a variável de estabilidade.
                pw.println("\t"+"S"+"\t"+"#stab;");
                pw.println();
            }
            /************************************************************************/
            // verifica se  variável de estabilidade foi resetada.
            pw.println("NETWORK");
            pw.println("TITLE =");
            pw.println("// Check stability variable");
            pw.println("\t"+"A"+"\t"+"#stab"+";");
            pw.println("\t"+"JC"+"\t"+"L001;");
            pw.println();
            /************************************************************************/
            // código em IL para os lugares.
            itr = conditionList.listIterator();
            while(itr.hasNext()) {
                Condition c = (Condition)itr.next();
                pw.println("NETWORK");
                pw.println("TITLE =");
                pw.println("// Place "+c.getId());
                pw.println("\t"+"A"+"\t"+"#"+c.getId()+";");
                ListIterator itr2 = c.getOutputSignalsList().listIterator();
                ListIterator itr3 = c.getOperatorsList().listIterator();
                i = 0;
                while(itr2.hasNext()) {
                    String s1 = (String)itr2.next();
                    String s2 = (String)itr3.next();
                    pw.println("\t"+s2+"\t"+s1+";"+"\t//"+c.getFromCommentsList(i));
                    i++;
                }
                pw.println();
            }
            /************************************************************************/
            // fim do block.
            pw.println("END_ORGANIZATION_BLOCK");
            /************************************************************************/
            pw.close();
            fw.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }// fecha main.
    
    public static void geraIL(File entradaEmXML, File saidaEmIL) {
        try {
            //File f = new File("C:\\Java\\TCC\\demo3_STEP7.txt"); // Em casa deixar esta linha.
            File f = saidaEmIL;
            // f = new File("C:\\Documents and Settings\\Administrator\\Desktop\\Victor\\V105_STEP7.txt");
            FileWriter fw = new FileWriter(f);
            PrintWriter pw = new PrintWriter(fw);
            // tratamento do arquivo de entrada.
            // xmlp = new XMLProcessor("C:\\Documents and Settings\\Administrator\\Desktop\\Victor\\V105_STEP7.xml");
            //XMLProcessor xmlp = new XMLProcessor("C:\\Java\\TCC\\demo3.xml"); // em casa deixar esta
            XMLProcessor xmlp = new XMLProcessor(entradaEmXML.getAbsolutePath());
            // obtenção dos lugares, transições e sinais de entrada e de saída.
            List conditionList = xmlp.XMLInterpreter("place");
            List eventList = xmlp.XMLInterpreter("transition");
            List iosList = xmlp.XMLInterpreter("signals");
            /************************************************************************/
            // cabeçalho do function block.
            pw.println(
                    "ORGANIZATION_BLOCK \"Cycle Execution\"" + "\n" +
                    "TITLE = \"Main Program Sweep (Cycle)\"" + "\n" +
                    "VERSION : "
                    );
            pw.println();
            /************************************************************************/
            // declaração das variáveis.
            pw.println("VAR_TEMP");
            pw.println(
                    "\t" + "OB1_EV_CLASS : BYTE ; //Bits 0-3 = 1 (Coming event), Bits 4-7 = 1 (Event class 1)" + "\n" +
                    "\t" + "OB1_SCAN_1 : BYTE ; //1 (Cold restart scan 1 of OB 1), 3 (Scan 2-n of OB 1)" + "\n" +
                    "\t" + "OB1_PRIORITY : BYTE ; //Priority of OB Execution" + "\n" +
                    "\t" + "OB1_OB_NUMBR : BYTE ; //1 (Organization block 1, OB1)" + "\n" +
                    "\t" + "OB1_RESERVED_1 : BYTE ; //Reserved for system" + "\n" +
                    "\t" + "OB1_RESERVED_2 : BYTE ; //Reserved for system" + "\n" +
                    "\t" + "OB1_PREV_CYCLE : INT ; //Cycle time of previous OB1 scan (milliseconds)" + "\n" +
                    "\t" + "OB1_MIN_CYCLE : INT ; //Minimum cycle time of OB1 (milliseconds)" + "\n" +
                    "\t" + "OB1_MAX_CYCLE : INT ; //Maximum cycle time of OB1 (milliseconds)" + "\n" +
                    "\t" +"OB1_DATE_TIME : DATE_AND_TIME ;   //Date and time OB1 started"
                    );
            // declaracao das variáveis do programa gerado a partir da rede de Petri.
            pw.println("\t"+"// variáveis do programa gerado a partir da rede de Petri.");
            ListIterator itr = conditionList.listIterator();
            while(itr.hasNext()) {
                Condition c = (Condition)itr.next();
                pw.println("\t"+c.getId()+":"+"BOOL;");
            }
            pw.println("\t"+"stab:BOOL;");
            pw.println("END_VAR");
            pw.println();
            /************************************************************************/
            // começo das instruções do programa.
            pw.println("BEGIN");
            pw.println();
            pw.println("NETWORK");
            pw.println("TITLE =");
            pw.println("// (Re)inicializa rede de Petri");
            pw.println("\t"+"A"+"\t"+"I12.3;");
            itr = conditionList.listIterator();
            while(itr.hasNext()) {
                Condition c = (Condition)itr.next();
                String operator = c.getInitialMarking() == true ? "S" : "R";
                pw.println("\t"+operator+"\t"+"#"+c.getId()+";");
            }
            pw.println("NETWORK");
            pw.println("TITLE =");
            pw.println("L001:"+"\t"+"R"+"\t"+"#stab;");
            pw.println();
            // código em IL para as transições.
            itr = eventList.listIterator();
            while(itr.hasNext()) {
                Event e = (Event)itr.next();
                pw.println("NETWORK");
                pw.println("TITLE =");
                pw.println("// Transition "+e.getId());
                ListIterator itr2 = e.getPreConditionList().listIterator();
                // verifica se a transição está habilitada.
                while(itr2.hasNext()) {
                    Condition c = (Condition)itr2.next();
                    pw.println("\t"+"A"+"\t"+"#"+c.getId()+";");
                }
                itr2 = e.getPosConditionList().listIterator();
                while(itr2.hasNext()) {
                    Condition c = (Condition)itr2.next();
                    pw.println("\t"+"AN"+"\t"+"#"+c.getId()+";");
                }
                // verifica se a condição de disparo da transição está satisfeita.
                itr2 = e.getInputSignalsList().listIterator();
                ListIterator itr3 = e.getOperatorsList().listIterator();
                int i = 0;
                while(itr2.hasNext()) {
                    String signal = (String)itr2.next();
                    String operator = (String)itr3.next();
                    pw.println("\t"+operator+"\t"+signal+";"+"\t//"+e.getFromCommentsList(i));
                    i++;
                }
                // atualiza a marcação da RdP.
                itr2 = e.getPreConditionList().listIterator();
                while(itr2.hasNext()) {
                    // retira a marca dos pré-lugares.
                    Condition c = (Condition)itr2.next();
                    pw.println("\t"+"R"+"\t"+"#"+c.getId()+";");
                }
                itr2 = e.getPosConditionList().listIterator();
                while(itr2.hasNext()) {
                    // coloca a marca nos pós-lugares.
                    Condition c = (Condition)itr2.next();
                    pw.println("\t"+"S"+"\t"+"#"+c.getId()+";");
                }
                // se a transição disparou, seta a variável de estabilidade.
                pw.println("\t"+"S"+"\t"+"#stab;");
                pw.println();
            }
            /************************************************************************/
            // verifica se  variável de estabilidade foi resetada.
            pw.println("NETWORK");
            pw.println("TITLE =");
            pw.println("// Check stability variable");
            pw.println("\t"+"A"+"\t"+"#stab"+";");
            pw.println("\t"+"JC"+"\t"+"L001;");
            pw.println();
            /************************************************************************/
            // código em IL para os lugares.
            itr = conditionList.listIterator();
            while(itr.hasNext()) {
                Condition c = (Condition)itr.next();
                pw.println("NETWORK");
                pw.println("TITLE =");
                pw.println("// Place "+c.getId());
                pw.println("\t"+"A"+"\t"+"#"+c.getId()+";");
                ListIterator itr2 = c.getOutputSignalsList().listIterator();
                ListIterator itr3 = c.getOperatorsList().listIterator();
                int i = 0;
                while(itr2.hasNext()) {
                    String s1 = (String)itr2.next();
                    String s2 = (String)itr3.next();
                    pw.println("\t"+s2+"\t"+s1+";"+"\t//"+c.getFromCommentsList(i));
                    i++;
                }
                pw.println();
            }
            /************************************************************************/
            // fim do block.
            pw.println("END_ORGANIZATION_BLOCK");
            /************************************************************************/
            pw.close();
            fw.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
        
    }
    
}//  fecha class.
