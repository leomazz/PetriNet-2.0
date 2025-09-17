# Ambiente de Simulação Distribuída de Sistemas Produtivos / Environment for the Distributed Simulation of Production Systems

## 🇧🇷 Ambiente de Simulação Distribuída de Sistemas Produtivos

Este repositório contém a implementação do **PetriNet 2.0**, um ambiente de software para **simulação distribuída de sistemas produtivos**, desenvolvido em Java como parte de um Trabalho de Formatura da Engenharia Mecatrônica (Escola Politécnica da USP, 2007).

###  Objetivo
O projeto implementa um **Ambiente de Simulação (AS)** capaz de executar e analisar modelos de **sistemas a eventos discretos (SEDs)** descritos por **redes de Petri (RdP)**.  
A simulação pode ser realizada de forma distribuída, em diferentes computadores, com comunicação via **TCP/IP** e sincronização baseada no mecanismo de **token ring**.

###  Funcionalidades
- Modelagem de sistemas produtivos usando **redes de Petri**.  
- Representação em **PNML (Petri Net Markup Language – XML)**.  
- Execução de simulações **locais ou distribuídas** entre estações.  
- Comunicação cliente-servidor em **Java sockets (TCP/IP)**.  
- Sincronização conservadora dos eventos (garante a ordem de execução).  
- Interface gráfica para criação, edição e visualização das redes.  

###  Como usar
1. Crie os modelos das estações em XML/PNML.  
2. Configure os parâmetros de cada estação (IP, porta, próxima estação no anel lógico).  
3. Inicialize as estações na ordem correta (primeiro a última, até a estação A).  
4. Execute a simulação em modo **automático** ou **passo-a-passo**.  

###  Estrutura do Projeto
- Desenvolvido e organizado originalmente no IDE NetBeans (Java, Ant).
- `src/` → Código-fonte em Java  
- `lib/` → Bibliotecas externas (dom4j, jaxen, jdom, sax2)  
- `nbproject/` → Arquivos de configuração do NetBeans
- `build.xml` → Script Ant para compilar e gerar o JAR  
- `examples/` → Modelos XML de exemplo  

###  Compilação e execução
```bash
# Compilar com Ant
ant clean jar

# Executar (exemplo)
java -jar dist/PetriNet-2.0.jar
```

---

## 🇺🇸 Environment for the Distributed Simulation of Production Systems

This repository contains the implementation of **PetriNet 2.0**, a Java-based environment for **distributed simulation of production systems**, developed as a final graduation project in Mechatronics Engineering (Escola Politécnica, University of São Paulo, 2007).

###  Goal
The project implements a **Simulation Environment (SE)** to execute and analyze **discrete event systems (DES)** models described with **Petri nets**.  
The simulation can run **distributed across multiple computers**, using **TCP/IP communication** and a **token ring mechanism** for synchronization.

###  Features
- Modeling of production systems with **Petri nets**.  
- Support for **PNML (Petri Net Markup Language – XML)**.  
- Local or **distributed simulation** between stations.  
- Java **socket-based client-server communication** (TCP/IP).  
- Conservative synchronization of events (causality preserved).  
- Graphical interface for creation, edition, and visualization of Petri net models.  

###  Usage
1. Create station models in XML/PNML.  
2. Configure each station parameters (IP, port, next station in the logical ring).  
3. Initialize the stations in the correct order (from last to first, ending with station A).  
4. Run the simulation in **automatic** or **step-by-step** mode.  

###  Project Structure
- Originally developed and organized using NetBeans IDE (Java, Ant).
- `src/` → Java source code  
- `lib/` → External libraries (dom4j, jaxen, jdom, sax2)  
- `nbproject/` → NetBeans configuration files  
- `build.xml` → Ant script for compilation and JAR generation  
- `examples/` → Example XML models  

###  Build and run
```bash
# Build with Ant
ant clean jar

# Run (example)
java -jar dist/PetriNet-2.0.jar
```
