xlsx4j
======

Fast and lightweight implementation of xlsx file reader written in java.
Supports iteration over sheet without loading entire data into memory.
```java
XLSXReader reader = new XLSXReader(new FileXLSXResource(filename));
//XLSXReader reader = new XLSXReader(new FileInputStream(filename));

for (XLSXSheetRow row : reader.getSheet(0)) {
    System.out.println(row.get(0));
}
```

Benchmark
=========

small file (100 rows, 3 columns) hdd drive
```
 testFileIteration first hasNext 32 ms
 testFileIteration foreach 27 ms
 testFileIteration All 87 ms

 testFileLazyIteration first hasNext 8 ms
 testFileLazyIteration foreach 9 ms
 testFileLazyIteration All 18 ms

 testStreamLazyIteration first hasNext 111 ms
 testStreamLazyIteration foreach 8 ms
 testStreamLazyIteration All 120 ms

 testStreamIteration first hasNext 11 ms
 testStreamIteration foreach 4 ms
 testStreamIteration All 16 ms
```
 
big file (1.000.000 rows, 2 columns) on hdd
```
 testFileIteration first hasNext 4477 ms
 testFileIteration foreach 11413 ms
 testFileIteration All 15929 ms

 testFileLazyIteration first hasNext 1505 ms
 testFileLazyIteration foreach 14473 ms
 testFileLazyIteration All 15979 ms

 testStreamLazyIteration first hasNext 26088 ms
 testStreamLazyIteration foreach 13220 ms
 testStreamLazyIteration All 39309 ms

 testStreamIteration first hasNext 19350 ms
 testStreamIteration foreach 10065 ms
 testStreamIteration All 29416 ms
```

big file (1.000.000 rows, 2 columns) on ssd
```
 testFileIteration first hasNext 4351 ms
 testFileIteration foreach 8098 ms
 testFileIteration All 12483 ms

 testFileLazyIteration first hasNext 1041 ms
 testFileLazyIteration foreach 10201 ms
 testFileLazyIteration All 11242 ms
 
 testStreamLazyIteration first hasNext 1633 ms
 testStreamLazyIteration foreach 8466 ms
 testStreamLazyIteration All 10100 ms

 testStreamIteration first hasNext 3873 ms
 testStreamIteration foreach 6465 ms
 testStreamIteration All 10339 ms
```
