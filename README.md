xlsx4j
======

Fast and lightweight implementation of xlsx file reader written in java

Benchmark
=========

small file (100 rows, 3 columns)
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
 
big file (1.000.000 rows, 2 columns)
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
