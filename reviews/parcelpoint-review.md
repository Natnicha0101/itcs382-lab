Files read: `Locker.java`, `Main.java`, `Parcel.java`, `ParcelService.java` (the four attached files; no `parcelpoint/src/main/java` directory was present in the workspace).

| No. | Check | Code location (file:line) | Finding | Severity | Suggested fix |
|---:|---|---|---|---|---|
| 1 | Code quality | `Main.java:8` | The `Scanner` is never closed. | Should fix | Close it with try-with-resources when input is finished. |
| 2 | Code quality | `Main.java:11–17` | `store` and `pickup` access arguments without checking their count or whether numbers parse, so malformed input can terminate the program. | Must fix | Validate the command arguments before using them. |
| 3 | Code quality | `Main.java:6–26` | `main` combines input parsing, command dispatch, parcel creation, and output in one long conditional chain. | Should fix | Move each command’s handling into a small method. |
| 4 | Code quality | `ParcelService.java:6` | `Date` is an unused import. | Note | Remove the import. |
| 5 | Code quality | `ParcelService.java:12` | `maxParcels` is never used, so its apparent limit is not enforced. | Should fix | Enforce the limit in `storeParcel`, or remove the field. |
| 6 | Code quality | `ParcelService.java:42` | `==` compares string identities, so pickup can fail for matching codes. | Must fix | Compare codes with `.equals()`. |
| 7 | Code quality | `ParcelService.java:76–81` | The `FileWriter` is never closed, and the empty catch block hides write failures. | Must fix | Use try-with-resources and report or propagate the exception. |
| 8 | Code quality | `Parcel.java:13`; `ParcelService.java:28,44,68` | Status values are repeated string literals. | Should fix | Define status constants or an enum. |
| 9 | Design quality | `Parcel.java:7` | `status` is public, allowing other classes to change parcel state directly. | Should fix | Make the field private and expose a controlled state change. |
| 10 | Design quality | `ParcelService.java:20–22` | `getLockers()` returns the internal, mutable list. | Must fix | Return an unmodifiable view or a defensive copy. |
| 11 | Design quality | `Main.java:22` | `Main` reaches through `ParcelService` into its lockers to calculate availability; this is tight coupling to the service’s internal structure. | Should fix | Add a free-locker count operation to `ParcelService`. |
| 12 | Design quality | `Main.java:19–21`; `ParcelService.java:64` | The boolean argument selects two different listing behaviors, creating **control coupling**; `printParcels` has **logical cohesion**. | Should fix | Provide separate operations for the two listings. |
| 13 | Design quality | `Main.java:13–14`; `ParcelService.java:24,33` | Passing a whole `Parcel` and returning a whole `Locker` create **stamp coupling** where the caller needs only the assigned locker number. | Note | Return the locker number or a small result containing only the needed data. |
| 14 | Design quality | `ParcelService.java:25–32` | `storeParcel` also prints an SMS message and saves a file, giving the method **procedural cohesion** and the service several responsibilities. | Should fix | Keep assignment in the method and delegate notification and persistence. |
| 15 | Design quality | `ParcelService.java:39–46` | `pickUp` combines finding a parcel, changing its state, saving, and calculating a fee; its cohesion is **procedural**. | Should fix | Extract fee calculation and persistence from the pickup sequence. |
| 16 | Design quality | `ParcelService.java:28,44,67–68,78`; `Parcel.java:7` | The service and parcel share the spelling and meaning of status strings, creating **connascence of convention**. | Should fix | Represent status with a single enum used by both classes. |
| 17 | Design quality | `Locker.java:5`; `Parcel.java:6`; `ParcelService.java:56–61` | Several classes rely on the same numeric size meanings, creating **connascence of convention**. | Should fix | Use one shared size enum or named constants. |
| 18 | Design quality | `Main.java:13`; `Parcel.java:9` | The caller and constructor must agree on the order of two `String` arguments, creating **connascence of position**. | Note | Use clearly named factory parameters or distinct value types if these arguments are frequently mixed up. |

Findings by check: **Code quality: 8. Design quality: 10.**

