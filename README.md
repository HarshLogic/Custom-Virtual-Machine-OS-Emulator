# 🚀 J-VM: Custom Virtual Machine & OS Emulator

![Java](https://img.shields.io/badge/Java-21-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)
![Architecture](https://img.shields.io/badge/Architecture-System%20Design-blue.svg)
![Data Structures](https://img.shields.io/badge/Data%20Structures-O(1)%20LRU-purple.svg?style=flat)

## 📌 Overview
J-VM is a custom Virtual Machine and Operating System Memory Simulator built entirely from scratch in Java. Instead of relying on the host OS for memory management, this project implements a complete **Fetch-Decode-Execute CPU cycle**, a **Memory Management Unit (MMU)**, and an **LRU-based Paging System** to handle virtual-to-physical address translation.

The core engine is wrapped in a **Spring Boot REST API**, allowing the internal state of the CPU registers, physical memory frames, and page tables to be visualized and controlled externally.

## ✨ Technical Highlights
* **CPU Emulation:** Implements a stack-based processor capable of executing custom bytecode assembly (`LOAD`, `STORE`, `ADD`, `PUSH`).
* **Virtual Memory & Paging:** Translates simulated virtual addresses into physical array indices using a Page Table.
* **LRU Page Replacement:** Integrates a custom Data Structure (HashMap + Doubly Linked List) to handle `PageFaultExceptions` by swapping memory frames to a local `.dat` disk file in $O(1)$ time.
* **Separation of Concerns:** Strictly decouples the OS core logic from the HTTP web layer.

---

## 🏗️ System Architecture 

The project follows a modular, enterprise-grade directory structure:

| Layer | Package | Responsibility |
| :--- | :--- | :--- |
| **API Layer** | `controller`, `dto` | Exposes REST endpoints to control the VM lifecycle and fetch state. |
| **Middleware** | `service` | Bridges the HTTP controllers to the core Virtual Machine engine. |
| **Execution** | `vm` | Contains the `CPU`, `ExecutionStack`, and `InstructionParser`. |
| **Memory** | `memory` | Houses the `MMU`, `PageTable`, and physical memory arrays. |
| **Caching** | `replacement` | Contains the Strategy Pattern implementation for `LRUSwapper`. |
| **Storage** | `storage` | Manages raw binary I/O to the mock `.dat` hard drive. |

---
## 📜 Instruction Set Architecture (ISA)

The CPU currently understands the following custom assembly instructions:

| Opcode | Argument | Description |
| :--- | :--- | :--- |
| `PUSH` | `[int]` | Pushes a number onto the execution stack. |
| `POP` | None | Removes the top number from the stack. |
| `ADD` / `SUB` | None | Pops the top two numbers, performs math, pushes result. |
| `MUL` / `DIV` | None | Pops the top two numbers, performs math, pushes result. |
| `LOAD` | `[address]`| Reads data from the Virtual Address and pushes it to stack. |
| `STORE` | `[address]`| Pops data from the stack and writes it to the Virtual Address. |
| `JMP` | `[line]` | Unconditional jump to a specific line number (Program Counter). |
| `JZ` | `[line]` | Pops the top of the stack. If it is `0`, jumps to the line number. |
| `HALT` | None | Safely stops the Virtual Machine execution loop. |

---

## 🔌 REST API Endpoints

The VM can be controlled via HTTP requests once the Spring Boot application is running.

* `POST /api/vm/load` - Uploads an assembly code `.txt` file into the VM.
* `POST /api/vm/start` - Initializes the CPU and memory structures.
* `POST /api/vm/step` - Executes a single CPU instruction (Fetch-Decode-Execute).
* `GET /api/vm/state` - Returns the current snapshot of the CPU Registers, Stack, and Page Table.

---

## 🛠️ Getting Started

### Prerequisites
* Java 17 or higher

### Installation & Run
1. Clone the repository:
   ```bash
   git clone [[https://github.com/HarshLogic/Custom-Virtual-Machine-OS-Emulator.git](https://github.com/HarshLogic/Custom-Virtual-Machine-OS-Emulator.git)]
