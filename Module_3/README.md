# Local Community Event Portal — Core Java Module (Module 3)

41 Core Java exercises covering fundamentals through advanced Java 21 features.

---

## How to Run

### Prerequisites
- **Java 21+** (recommended for all exercises including Records and Virtual Threads)
- **MySQL Connector/J** JAR (exercises 31-33 only) — [download here](https://dev.mysql.com/downloads/connector/j/)

### Quick Start (any exercise)
```bash
# Compile
javac src/Ex01_HelloWorld.java

# Run
java -cp src Ex01_HelloWorld
```

### From inside the `src/` directory
```bash
cd Module_3/src
javac Ex01_HelloWorld.java
java  Ex01_HelloWorld
```

### JDBC Exercises (31-33) — requires MySQL Connector/J
```bash
cd Module_3/src
javac -cp ".;path/to/mysql-connector-j-9.x.x.jar" Ex31_BasicJDBC.java
java  -cp ".;path/to/mysql-connector-j-9.x.x.jar" Ex31_BasicJDBC
```
> Update `PASS = ""` with your MySQL root password in each file before running.  
> Run `Module_2/schema.sql` first to create the `community_portal` database.

### Module System (Exercise 34) — from `Module_3/` directory
```bash
# Compile com.utils
javac -d mods/com.utils src/modules/com.utils/module-info.java src/modules/com.utils/com/utils/StringUtils.java

# Compile com.greetings (depends on com.utils)
javac --module-path mods -d mods/com.greetings src/modules/com.greetings/module-info.java src/modules/com.greetings/com/greetings/Main.java

# Run
java --module-path mods -m com.greetings/com.greetings.Main
```

### TCP Chat (Exercise 35) — two terminals
```bash
# Terminal 1
java Ex35_TCPServer

# Terminal 2 (after server says "listening")
java Ex35_TCPClient
```

### Bytecode Inspection (Exercise 37)
```bash
javac Ex37_BytecodeInspection.java
javap -c  Ex37_BytecodeInspection     # show bytecode
javap -v  Ex37_BytecodeInspection     # verbose (constant pool too)
```

### Decompile (Exercise 38)
```bash
javac Ex38_DecompileClass.java
# Then use CFR:
java -jar cfr.jar Ex38_DecompileClass.class
# Or open in IntelliJ IDEA (auto-decompiles)
```

---

## File Structure

```
Module_3/
├── src/
│   ├── Ex01_HelloWorld.java
│   ├── Ex02_SimpleCalculator.java
│   ├── Ex03_EvenOddChecker.java
│   ├── Ex04_LeapYearChecker.java
│   ├── Ex05_MultiplicationTable.java
│   ├── Ex06_DataTypeDemo.java
│   ├── Ex07_TypeCasting.java
│   ├── Ex08_OperatorPrecedence.java
│   ├── Ex09_GradeCalculator.java
│   ├── Ex10_NumberGuessingGame.java
│   ├── Ex11_FactorialCalculator.java
│   ├── Ex12_MethodOverloading.java
│   ├── Ex13_RecursiveFibonacci.java
│   ├── Ex14_ArraySumAverage.java
│   ├── Ex15_StringReversal.java
│   ├── Ex16_PalindromeChecker.java
│   ├── Ex17_CarClass.java
│   ├── Ex18_InheritanceExample.java
│   ├── Ex19_InterfaceExample.java
│   ├── Ex20_TryCatchExample.java
│   ├── Ex21_CustomException.java
│   ├── Ex22_FileWriting.java
│   ├── Ex23_FileReading.java
│   ├── Ex24_ArrayListExample.java
│   ├── Ex25_HashMapExample.java
│   ├── Ex26_ThreadCreation.java
│   ├── Ex27_LambdaExpressions.java
│   ├── Ex28_StreamAPI.java
│   ├── Ex29_Records.java
│   ├── Ex30_PatternMatchingSwitch.java
│   ├── Ex31_BasicJDBC.java               ← needs MySQL Connector/J
│   ├── Ex32_InsertUpdateJDBC.java         ← needs MySQL Connector/J
│   ├── Ex33_TransactionHandling.java      ← needs MySQL Connector/J
│   ├── Ex35_TCPServer.java
│   ├── Ex35_TCPClient.java
│   ├── Ex36_HTTPClientAPI.java
│   ├── Ex37_BytecodeInspection.java
│   ├── Ex38_DecompileClass.java
│   ├── Ex39_ReflectionAPI.java
│   ├── Ex40_VirtualThreads.java
│   ├── Ex41_ExecutorService.java
│   └── modules/
│       ├── com.utils/
│       │   ├── module-info.java
│       │   └── com/utils/StringUtils.java
│       └── com.greetings/
│           ├── module-info.java
│           └── com/greetings/Main.java
└── README.md
```

---

## Exercise Reference

| # | File | Topic | Key Concept |
|---|------|-------|-------------|
| 1 | Ex01_HelloWorld | Hello World | `System.out.println`, class structure |
| 2 | Ex02_SimpleCalculator | Calculator | `Scanner`, `switch` expression, arithmetic |
| 3 | Ex03_EvenOddChecker | Even/Odd | Modulus operator `%`, `if-else` |
| 4 | Ex04_LeapYearChecker | Leap Year | Nested conditionals, boolean logic |
| 5 | Ex05_MultiplicationTable | Loops | `for` loop, `printf` formatting |
| 6 | Ex06_DataTypeDemo | Primitives | `byte short int long float double char boolean` |
| 7 | Ex07_TypeCasting | Type Casting | Widening vs narrowing cast, data loss |
| 8 | Ex08_OperatorPrecedence | Precedence | `* / % + -`, pre/post increment, ternary |
| 9 | Ex09_GradeCalculator | Grades | `if-else if-else` chain |
| 10 | Ex10_NumberGuessingGame | Game Loop | `Random`, `while`, feedback branching |
| 11 | Ex11_FactorialCalculator | Factorial | `for` loop, `long` overflow safety |
| 12 | Ex12_MethodOverloading | Overloading | Same name, different signatures |
| 13 | Ex13_RecursiveFibonacci | Recursion | Base case + recursive case, `long` |
| 14 | Ex14_ArraySumAverage | Arrays | `double[]`, sum/avg/min/max |
| 15 | Ex15_StringReversal | Strings | `StringBuilder.reverse()`, char swap |
| 16 | Ex16_PalindromeChecker | Palindrome | `replaceAll`, `toLowerCase`, `StringBuilder` |
| 17 | Ex17_CarClass | Classes | Attributes, constructor, methods, objects |
| 18 | Ex18_InheritanceExample | Inheritance | `extends`, `@Override`, `super`, polymorphism |
| 19 | Ex19_InterfaceExample | Interfaces | `interface`, `implements`, `default` method |
| 20 | Ex20_TryCatchExample | Exceptions | `try-catch-finally`, multi-catch |
| 21 | Ex21_CustomException | Custom Exc. | `extends Exception`, `throw`, custom fields |
| 22 | Ex22_FileWriting | File I/O | `BufferedWriter`, `FileWriter`, try-with-resources |
| 23 | Ex23_FileReading | File I/O | `BufferedReader`, `FileReader`, line iteration |
| 24 | Ex24_ArrayListExample | ArrayList | `ArrayList<String>`, add/remove/sort/iterate |
| 25 | Ex25_HashMapExample | HashMap | `HashMap<K,V>`, put/get/remove/entrySet |
| 26 | Ex26_ThreadCreation | Threads | `Thread`, `Runnable`, lambda, `join()` |
| 27 | Ex27_LambdaExpressions | Lambdas | `Comparator`, `Predicate`, `Function`, `Consumer`, `Supplier` |
| 28 | Ex28_StreamAPI | Streams | `filter`, `map`, `reduce`, `collect`, `groupingBy` |
| 29 | Ex29_Records | Records | `record`, auto-accessors, compact constructor (Java 16+) |
| 30 | Ex30_PatternMatchingSwitch | Pattern Match | `switch` type patterns, guarded patterns (Java 21) |
| 31 | Ex31_BasicJDBC | JDBC | `DriverManager`, `Statement`, `ResultSet` |
| 32 | Ex32_InsertUpdateJDBC | JDBC CRUD | `PreparedStatement`, DAO pattern, generated keys |
| 33 | Ex33_TransactionHandling | Transactions | `setAutoCommit(false)`, `commit()`, `rollback()` |
| 34 | modules/ | Module System | `module-info.java`, `exports`, `requires` (Java 9+) |
| 35 | Ex35_TCPServer/Client | TCP Sockets | `ServerSocket`, `Socket`, `BufferedReader`, `PrintWriter` |
| 36 | Ex36_HTTPClientAPI | HTTP Client | `HttpClient`, `HttpRequest`, `HttpResponse` (Java 11+) |
| 37 | Ex37_BytecodeInspection | Bytecode | `javap -c`, JVM opcodes, constant pool |
| 38 | Ex38_DecompileClass | Decompile | CFR / JD-GUI / IntelliJ decompiler |
| 39 | Ex39_ReflectionAPI | Reflection | `Class.forName()`, `getDeclaredMethods()`, `invoke()`, `setAccessible(true)` |
| 40 | Ex40_VirtualThreads | Virtual Threads | `Thread.ofVirtual()`, lightweight concurrency (Java 21) |
| 41 | Ex41_ExecutorService | ExecutorService | `Executors.newFixedThreadPool()`, `Callable`, `Future.get()` |

---

## Java Features by Version

| Java Version | Exercises |
|---|---|
| Java 8 | 1-28 (Streams, Lambdas) |
| Java 9+ | 34 (Module System) |
| Java 11+ | 36 (HTTP Client) |
| Java 16+ | 29 (Records) |
| Java 21 | 30 (Pattern Matching Switch), 40 (Virtual Threads) |

---

## Notes

> **JDBC (Ex 31-33):** Update `static final String PASS = ""` with your MySQL password.  
> The `community_portal` database from `Module_2/schema.sql` is used as the data source.

> **Ex 33 — Transaction Setup:** Run the `accounts` table setup SQL in the comments before executing.

> **Ex 37 — Bytecode:** The real learning happens *after* compiling — run `javap -c Ex37_BytecodeInspection`.

> **Ex 40 — Virtual Threads:** Requires Java 21. On Java 19-20, use `--enable-preview`.

> **Ex 35 — TCP Chat:** Must start `Ex35_TCPServer` before `Ex35_TCPClient` in a second terminal.
