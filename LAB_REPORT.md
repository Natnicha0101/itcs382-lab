# Lab Report, Code Quality and Design Quality

Repository URL:

## Tools

| Item | Version or name |
| --- | --- |
| JDK | 17.0.12 |
| Maven | 3.10.0 |
| IDE | Visual Studio Code 1.142.0 |
| SonarQube for IDE | 6.2.0 |
| AI tool and exact model | ChatGPT, GPT-6 Sol |

## Task 1. SonarQube on the starter code

Commit of the starter, before any change: da3dfb6

Table 1. SonarQube issues in the starter code

| No. | Rule | File:line | Message |
| --- | --- | --- | --- |
| 1 | java:S2143 | ParcelService.java:1 | Use the "java.time" API for date and time. |
| 2 | java:S1128 | ParcelService.java:6 | Remove this unused import 'java.util.Date'. |
| 3 | java:S1068 | ParcelService.java:12 | Remove this unused "maxParcels" private field. |
| 4 | java:S106 | ParcelService.java:30 | Replace this use of System.out by a logger. |
| 5 | java:S4973 | ParcelService.java:42 | Strings and Boxed types should be compared using "equals()". |
| 6 | java:S106 | ParcelService.java:67 | Replace this use of System.out by a logger. |
| 7 | java:S106 | ParcelService.java:69 | Replace this use of System.out by a logger. |
| 8 | java:S2095 | ParcelService.java:76 | Use try-with-resources or close this "FileWriter" in a "finally" clause. |
| 9 | java:S108 | ParcelService.java:80 | Remove this block of code, fill it in, or add a comment explaining why it is empty. |
| 10 | java:S106 | Main.java:9 | Replace this use of System.out by a logger. |
| 11 | java:S106 | Main.java:15 | Replace this use of System.out by a logger. |
| 12 | java:S106 | Main.java:17 | Replace this use of System.out by a logger. |
| 13 | java:S106 | Main.java:22 | Replace this use of System.out by a logger. |

## Task 2. AI review of the starter code

| Item | Value |
| --- | --- |
| Date | 25 October 2026 |
| AI tool and exact model | ChatGPT, GPT-6 Sol |
| Commit reviewed | da3dfb6 |
| How the AI read the files (attached the files, opened the folder, or pasted the files) | Attached Locker.java, Main.java, Parcel.java, and ParcelService.java |
| Output file in the reviews folder | reviews/parcelpoint-review.md |

Table 2. All findings on the starter code

| No. | File:line | Finding | Found by (SonarQube, AI or both) | SonarQube rule or course term | Correct? (yes or no, and why) |
| --- | --- | --- | --- | --- | --- |
| 1 | ParcelService.java:1 | Suggests using java.time | SonarQube | java:S2143 | No: the program has no date or time operations; it only has an unused Date import. |
| 2 | ParcelService.java:6 | Unused Date import | both | java:S1128 | Yes: Date is imported but never referenced. |
| 3 | ParcelService.java:12 | Unused maxParcels field | both | java:S1068 | Yes: no method reads maxParcels. |
| 4 | ParcelService.java:30 | System.out used for SMS message | SonarQube | java:S106 | Yes: the line calls System.out.println; whether to keep it is a separate decision. |
| 5 | ParcelService.java:42 | Parcel code Strings compared with == | both | java:S4973 | Yes: == compares references, and the observed pickup failed for P1. |
| 6 | ParcelService.java:67 | System.out used for staff listing | SonarQube | java:S106 | Yes: the line calls System.out.println. |
| 7 | ParcelService.java:69 | System.out used for public listing | SonarQube | java:S106 | Yes: the line calls System.out.println. |
| 8 | ParcelService.java:76 | FileWriter is not closed | both | java:S2095 | Yes: there is no close or try-with-resources; parcels.csv was empty after the run. |
| 9 | ParcelService.java:80 | Empty catch hides IOException | both | java:S108 | Yes: the catch body contains no handling. |
| 10 | Main.java:8 | Scanner is not closed | AI | Code quality | Yes: Scanner is created without close or try-with-resources. |
| 11 | Main.java:11–17 | Missing argument-count and number validation | AI | Code quality | Yes: words are indexed and parsed without checking malformed input. |
| 12 | Main.java:6–26 | main handles parsing, commands, parcel creation and output | AI | Cohesion | Yes: these operations are all present in one method, although "too long" is subjective. |
| 13 | Parcel.java:7,13; ParcelService.java:28,44,68 | Parcel and service rely on String status values | AI | Connascence of convention | Yes: both classes must agree on values such as STORED; the AI's claim that literals repeat "many times" is overstated. |
| 14 | Parcel.java:7 | status field is public | AI | Encapsulation | Yes: ParcelService changes parcel.status directly. |
| 15 | ParcelService.java:20–22 | getLockers returns the mutable internal list | AI | Coupling / encapsulation | Yes: callers can modify the actual lockers list. |
| 16 | Main.java:22 | Main reaches into lockers to count free ones | AI | Coupling | Yes: Main calls getLockers and then Locker.isFree instead of asking the service for the count. |
| 17 | Main.java:19–21; ParcelService.java:64 | Boolean flag selects two listing behaviors | AI | Control coupling; logical cohesion | Yes: false and true select different output paths in printParcels. |
| 18 | Main.java:13–15; ParcelService.java:24,33 | Passing Parcel and returning Locker were called stamp coupling | AI | Stamp coupling | No: the claim is too broad; storeParcel uses several Parcel properties, although returning a whole Locker exposes more than Main needs. |
| 19 | ParcelService.java:25–32 | storeParcel assigns a locker, prints an SMS and saves a file | AI | Cohesion / class responsibility | Yes: the method performs assignment, notification output and persistence; the exact label "procedural" is debatable. |
| 20 | ParcelService.java:39–46 | AI suggests extracting fee calculation from pickUp | AI | Cohesion | No: fee calculation is already in the separate fee method at lines 52–62, although pickUp coordinates several steps. |
| 21 | Locker.java:5; Parcel.java:6; ParcelService.java:56–61 | Classes share numeric size meanings | AI | Connascence of convention | Yes: the same values 1, 2 and 3 must have the same meanings across classes. |
| 22 | Main.java:13; Parcel.java:9 | Constructor has two adjacent String arguments | AI | Connascence of position | Yes: code and studentId must be passed in the correct order, and Java cannot distinguish them by type. |
| 23 | Main.java:9 | System.out prints the command menu | SonarQube | java:S106 | Yes: Main prints the available commands to the console. |
| 24 | Main.java:15 | System.out prints the store result | SonarQube | java:S106 | Yes: Main prints the locker number or no-free-locker message. |
| 25 | Main.java:17 | System.out prints the pickup fee | SonarQube | java:S106 | Yes: Main prints the fee to the console. |
| 26 | Main.java:22 | System.out prints the free-locker count | SonarQube | java:S106 | Yes: Main prints the count to the console. |

2.1 Name two problems that only the AI review found.
The AI found that Parcel.status is public (Parcel.java:7), so other classes can change it directly. It also found that getLockers() returns the internal mutable list (ParcelService.java:20–22), allowing callers to change the service's lockers. SonarQube did not report either problem.

2.2 Name one problem that only SonarQube found, or write none.
SonarQube reported the use of System.out in ParcelService.java:30 (java:S106), but the AI review did not report it.

2.3 Name one AI finding that was wrong or too vague, and explain why. If every finding was right, explain how you checked them.
The AI suggested extracting fee calculation from pickUp, but this advice is inaccurate because fee calculation is already in a separate fee() method at ParcelService.java:52–62. I checked both methods in the source file: pickUp calls fee(parcel, days) at line 46.

## Task 3. Fix and check

Table 3. What you did with each SonarQube issue in Table 1

| No. in Table 1 | Fixed or kept | Commit, or the reason for keeping it |
| --- | --- | --- |
| 1 | Fixed | 62376d0 |
| 2 | Fixed | 62376d0 |
| 3 | Fixed | f813891 |
| 4 | Kept | This command-line program intentionally prints the simulated SMS to the user. |
| 5 | Fixed | 4ce6f06 |
| 6 | Kept | This command-line program intentionally prints the staff parcel list. |
| 7 | Kept | This command-line program intentionally prints the public parcel list. |
| 8 | Fixed | 6cc62e1 |
| 9 | Fixed | 4d762bb |
| 10 | Kept | This command-line program intentionally prints its available commands. |
| 11 | Kept | This command-line program intentionally prints the result of storing a parcel. |
| 12 | Kept | This command-line program intentionally prints the pickup fee. |
| 13 | Kept | This command-line program intentionally prints the number of free lockers. |
Number of SonarQube issues that remain after your fixes: 7

The two design problems that you fixed

| Design problem (course term) | What you changed | Commit |
| Coupling and encapsulation: getLockers exposed the mutable internal list | Replaced getLockers with getFreeLockerCount and changed Main to request only the count | 990e28e |
| Encapsulation: Parcel.status was public | Made status private and added methods for reading and changing its state | 04bfc0d |

Output of the program after your fixes, for the commands `store P1 6501 2`, `pickup P1 5` and `staff`, and the content of parcels.csv:




## Task 4. AI-generated poor and good designs

| Item | bikeshare-poor | bikeshare-good |
| --- | --- | --- |
| Date | 25 October 2026 | 25 October 2026 |
| AI tool and exact model | ChatGPT, GPT-6 Sol | ChatGPT, GPT-6 Sol |
| Prompt (B or C as given, or your changed prompt in full) | B as given | C as given |
| Builds with mvn package (yes or no) | Yes | Yes |
| Number of classes, interfaces, enums and records | 1 | 7 |
| SonarQube issues, total | 14 | 6 |
| SonarQube issues, by rule | java:S2386: 5; java:S1172: 1; java:S106: 8 | java:S106: 6 |
| AI review findings in Check 1 | 4 | 2 |
| AI review findings in Check 2 | 6 | 5 |
| Review output file in the reviews folder | reviews/bikeshare-poor-review.md | reviews/bikeshare-good-review.md |

4.1 Did SonarQube report any cohesion or coupling problem in bikeshare-poor? Quote the rule and the message if it did.
SonarQube reported java:S2386 five times for the public shared arrays in bikeshare-poor, with the message "Make this member protected." This is a visibility issue related to how other code can depend on shared state. It did not directly label the class's cohesion or classify its coupling using the course terms.

4.2 Did the AI review tell the two versions apart? Give one finding for bikeshare-poor that does not appear for bikeshare-good.
Yes. The AI review of bikeshare-poor found that one BikeShare class contains command handling, bike state, fee rules, file saving, and display behavior (BikeShare.java:10). The good version separates these responsibilities into classes such as Main, BikeShareService, FeeCalculator, and CsvRideRepository, so that same finding does not appear in its review.

4.3 Which version would you rather extend with a new member type, such as a visitor who pays 4 baht per minute, and why?
I would extend bikeshare-good. It already has a MemberType enum and a separate FeeCalculator, so I can add a visitor type and its 4-baht-per-minute rate in clearly identified places. The poor version uses integer member codes and mixes the fee rule with other responsibilities in one class.

## Task 5. Reflection

5.1 What does SonarQube check, and what does it not check? Answer with examples from this lab.
SonarQube found code issues such as the String comparison using == and the unclosed FileWriter in ParcelPoint. It also reported visibility and System.out issues in BikeShare, but did not directly explain the differences in cohesion and coupling between the two designs.

5.2 When should you not trust the AI review? Give an example from this lab.
I should not trust an AI finding without checking the source code. For example, the ParcelPoint review suggested extracting fee calculation from pickUp, although a separate fee() method already existed.

5.3 What will you do differently in Project Phase 2 after this lab?
For Project Phase 2, I will run SonarQube and an AI design review on the same committed code. I will check each finding myself, make small fixes, and rerun the program after each change.

## AI usage declaration

| AI tool and exact model | Purpose | Files or parts that it produced or changed | How you checked its output |
| --- | --- | --- | --- |
| ChatGPT, GPT-6 Sol | Review the ParcelPoint starter code on 25 October 2026 | reviews/parcelpoint-review.md; findings summarized in Task 2 | Compared each finding with the Java files, the SonarQube issues, and the program's output; marked inaccurate advice in Table 2. |
| ChatGPT, GPT-6 Sol | Generate the poor BikeShare design on 25 October 2026 | bikeshare-poor/pom.xml and BikeShare.java | Ran mvn package and tested renting and returning a bike; checked the 10-baht receipt. |
| ChatGPT, GPT-6 Sol | Generate the good BikeShare design on 25 October 2026 | bikeshare-good/pom.xml and its seven Java files | Ran mvn package and tested renting and returning a bike; checked the 10-baht receipt. |
| ChatGPT, GPT-6 Sol | Review both BikeShare versions on 25 October 2026 | reviews/bikeshare-poor-review.md and reviews/bikeshare-good-review.md; Task 4 comparison | Compared the findings with the Java files and SonarQube results; checked the file names that the AI reported. |
| ChatGPT, GPT-6 Sol | Help explain the lab, suggest ParcelPoint fixes, and draft report answers on 25 October 2026 | ParcelPoint fixes and parts of LAB_REPORT.md | Built and ran ParcelPoint after changes, checked the final output and parcels.csv, and compared report entries with the observed issues. |