xlsx4j
======

Fast and lightweight implementation of xlsx file reader written in java

Benchmark
=========

small file (100 rows, 3 columns)
```
 testFileIteration foreach 23 ms
 testFileIteration All 87 ms
 
 testStreamLazyIteration foreach 11 ms
 testStreamLazyIteration All 114 ms
 
 testStreamIteration hasNext 24 ms
 testStreamIteration foreach 4 ms
 testStreamIteration All 30 ms
```
 
big file (1.000.000 rows, 2 columns)
```
 testFileIteration foreach 11306 ms
 testFileIteration All 15519 ms
 
 testStreamLazyIteration foreach 13364 ms
 testStreamLazyIteration All 25655 ms
 
 testStreamIteration foreach 9930 ms
 testStreamIteration All 25533 ms
```
